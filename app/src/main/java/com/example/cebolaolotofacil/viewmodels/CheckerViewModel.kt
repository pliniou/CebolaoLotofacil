package com.example.cebolaolotofacil.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.cebolaolotofacil.data.CheckResult
import com.example.cebolaolotofacil.data.LotofacilGame
import com.example.cebolaolotofacil.logic.HistoryRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

sealed class CheckerUiState {
    object Idle : CheckerUiState()
    object Loading : CheckerUiState()
    data class Success(val result: CheckResult) : CheckerUiState()
    data class Error(val message: String) : CheckerUiState()
}

class CheckerViewModel(application: Application) : AndroidViewModel(application) {

    private val historyRepository = HistoryRepository(application)

    private val _uiState = MutableStateFlow<CheckerUiState>(CheckerUiState.Idle)
    val uiState = _uiState.asStateFlow()

    private val _selectedNumbers = MutableStateFlow<Set<Int>>(emptySet())
    val selectedNumbers = _selectedNumbers.asStateFlow()

    // Nova variável para expor o número do último concurso
    private val _lastContestNumber = MutableStateFlow<Int?>(null)
    val lastContestNumber = _lastContestNumber.asStateFlow()

    init {
        // Carrega o número do último concurso assim que o ViewModel é criado.
        loadLastContestInfo()
    }

    private fun loadLastContestInfo() {
        viewModelScope.launch {
            // Apenas para garantir que o histórico seja carregado
            historyRepository.getHistory()
            _lastContestNumber.value = historyRepository.getLastContestNumber()
        }
    }
    // *** NOVA FUNÇÃO ***
    // Define um jogo para ser conferido, vindo de uma fonte externa (atalho).
    fun setGameToCheck(numbers: Set<Int>) {
        if (numbers.size == 15) {
            _selectedNumbers.value = numbers
            // Dispara a conferência imediatamente
            onCheckGameClicked()
        }
    }

    fun onNumberClicked(number: Int) {
        // A lógica de conferência não é mais chamada aqui.
        _uiState.value = CheckerUiState.Idle
        _selectedNumbers.update { currentSelection ->
            val newSelection = currentSelection.toMutableSet()
            if (number in newSelection) {
                newSelection.remove(number)
            } else {
                if (newSelection.size < 15) {
                    newSelection.add(number)
                }
            }
            newSelection
        }
    }

    // Nova função pública para ser chamada pelo botão "Conferir".
    fun onCheckGameClicked() {
        val currentGame = _selectedNumbers.value
        if (currentGame.size != 15) return // Segurança extra

        _uiState.value = CheckerUiState.Loading

        viewModelScope.launch(Dispatchers.Default) {
            try {
                val history = historyRepository.getHistory()
                if (history.isEmpty()) {
                    _uiState.value = CheckerUiState.Error("Arquivo de histórico não encontrado ou vazio.")
                    return@launch
                }

                val gameToCheck = LotofacilGame(currentGame)
                val scoreCounts = mutableMapOf<Int, Int>()
                var lastHit: Int? = null

                history.forEach { draw ->
                    val hits = gameToCheck.numbers.intersect(draw.numbers).size
                    if (hits >= 11) {
                        scoreCounts[hits] = (scoreCounts[hits] ?: 0) + 1
                        if (lastHit == null) {
                            lastHit = draw.contestNumber
                        }
                    }
                }

                val finalResult = CheckResult(
                    scoreCounts = scoreCounts,
                    lastHitContest = lastHit,
                    lastCheckedContest = historyRepository.getLastContestNumber()
                )

                _uiState.value = CheckerUiState.Success(finalResult)

            } catch (e: Exception) {
                _uiState.value = CheckerUiState.Error("Falha ao conferir os jogos: ${e.message}")
            }
        }
    }

    fun clearSelection() {
        _selectedNumbers.value = emptySet()
        _uiState.value = CheckerUiState.Idle
    }
}