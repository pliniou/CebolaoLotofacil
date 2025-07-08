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
import kotlinx.coroutines.withContext

sealed class CheckerUiState {
    data object Idle : CheckerUiState()
    data object Loading : CheckerUiState()
    data class Success(val result: CheckResult) : CheckerUiState()
    data class Error(val message: String) : CheckerUiState()
}

class CheckerViewModel(application: Application) : AndroidViewModel(application) {
    private val historyRepository = HistoryRepository(application)

    private val _uiState = MutableStateFlow<CheckerUiState>(CheckerUiState.Idle)
    val uiState = _uiState.asStateFlow()

    private val _selectedNumbers = MutableStateFlow<Set<Int>>(emptySet())
    val selectedNumbers = _selectedNumbers.asStateFlow()

    private val _lastContestNumber = MutableStateFlow<Int?>(null)
    val lastContestNumber = _lastContestNumber.asStateFlow()

    init {
        loadLastContestInfo()
    }

    private fun loadLastContestInfo() {
        viewModelScope.launch {
            historyRepository.getHistory()
            _lastContestNumber.value = historyRepository.getLastContestNumber()
        }
    }

    fun setGameToCheck(numbers: Set<Int>) {
        if (numbers.size == 15) {
            _selectedNumbers.value = numbers
            onCheckGameClicked()
        }
    }

    fun onNumberClicked(number: Int) {
        _uiState.value = CheckerUiState.Idle
        _selectedNumbers.update { currentSelection ->
            val newSelection = currentSelection.toMutableSet()
            if (number in newSelection) {
                newSelection.remove(number)
            } else if (newSelection.size < 15) {
                newSelection.add(number)
            }
            newSelection
        }
    }

    fun onCheckGameClicked() {
        val currentGame = _selectedNumbers.value
        if (currentGame.size != 15) return

        _uiState.value = CheckerUiState.Loading
        viewModelScope.launch {
            try {
                // A leitura do histórico é uma operação de I/O.
                val history = historyRepository.getHistory()
                if (history.isEmpty()) {
                    _uiState.value = CheckerUiState.Error("Arquivo de histórico não encontrado ou vazio.")
                    return@launch
                }

                // O processamento dos dados é uma tarefa de CPU.
                val result = withContext(Dispatchers.Default) {
                    val gameToCheck = LotofacilGame(currentGame)
                    val scoreCounts = mutableMapOf<Int, Int>()
                    var lastHit: Int? = null

                    history.forEach { draw ->
                        val hits = gameToCheck.numbers.intersect(draw.numbers).size
                        if (hits >= 11) {
                            scoreCounts[hits] = (scoreCounts[hits] ?: 0) + 1
                            if (lastHit == null) { // Pega a primeira ocorrência, que é a mais recente
                                lastHit = draw.contestNumber
                            }
                        }
                    }
                    CheckResult(
                        scoreCounts = scoreCounts,
                        lastHitContest = lastHit,
                        lastCheckedContest = history.first().contestNumber
                    )
                }
                _uiState.value = CheckerUiState.Success(result)

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