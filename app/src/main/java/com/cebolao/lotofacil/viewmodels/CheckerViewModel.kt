package com.cebolao.lotofacil.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cebolao.lotofacil.data.CheckResult
import com.cebolao.lotofacil.data.HistoricalDraw
import com.cebolao.lotofacil.data.LotofacilConstants
import com.cebolao.lotofacil.logic.HistoryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException
import javax.inject.Inject

sealed class CheckerUiState {
    data object Idle : CheckerUiState()
    data object Loading : CheckerUiState()
    data class Success(val result: CheckResult) : CheckerUiState()
    data class Error(val message: String) : CheckerUiState()
}

@HiltViewModel
class CheckerViewModel @Inject constructor(
    private val historyRepository: HistoryRepository
    // CORREÇÃO: Remove o SavedStateHandle que não estava sendo utilizado.
) : ViewModel() {
    private val _uiState = MutableStateFlow<CheckerUiState>(CheckerUiState.Idle)
    val uiState = _uiState.asStateFlow()

    private val _selectedNumbers = MutableStateFlow<Set<Int>>(emptySet())
    val selectedNumbers = _selectedNumbers.asStateFlow()

    fun onNumberClicked(number: Int) {
        if (_uiState.value is CheckerUiState.Loading) return
        _uiState.value = CheckerUiState.Idle
        _selectedNumbers.update { currentSelection ->
            val newSelection = currentSelection.toMutableSet()
            if (number in newSelection) {
                newSelection.remove(number)
            } else if (newSelection.size < LotofacilConstants.GAME_SIZE) {
                newSelection.add(number)
            }
            newSelection
        }
    }

    fun onCheckGameClicked() {
        if (_selectedNumbers.value.size != LotofacilConstants.GAME_SIZE) return
        performCheck(_selectedNumbers.value)
    }

    fun checkGameForStats(gameNumbers: Set<Int>) {
        performCheck(gameNumbers)
    }

    private fun performCheck(gameNumbers: Set<Int>) {
        if (_uiState.value is CheckerUiState.Loading) return
        _uiState.value = CheckerUiState.Loading
        viewModelScope.launch {
            try {
                val history = historyRepository.getHistory()
                if (history.isEmpty()) {
                    _uiState.value = CheckerUiState.Error("Histórico de sorteios não encontrado.")
                    return@launch
                }
                val result = withContext(Dispatchers.Default) {
                    calculateResult(gameNumbers, history)
                }
                _uiState.value = CheckerUiState.Success(result)
            } catch (_: IOException) {
                _uiState.value = CheckerUiState.Error("Erro ao ler o histórico.")
            }
        }
    }

    private fun calculateResult(gameNumbers: Set<Int>, history: List<HistoricalDraw>): CheckResult {
        val scoreCounts = mutableMapOf<Int, Int>()
        var lastHitContest: Int? = null
        val recentHits = history.take(10).map { draw ->
            gameNumbers.intersect(draw.numbers).size
        }

        history.forEach { draw ->
            val hits = gameNumbers.intersect(draw.numbers).size
            if (hits >= 11) {
                scoreCounts[hits] = (scoreCounts[hits] ?: 0) + 1
                if (lastHitContest == null) {
                    lastHitContest = draw.contestNumber
                }
            }
        }

        return CheckResult(
            scoreCounts = scoreCounts,
            lastHitContest = lastHitContest,
            lastCheckedContest = history.first().contestNumber,
            recentHits = recentHits
        )
    }

    fun clearSelection() {
        _selectedNumbers.value = emptySet()
        _uiState.value = CheckerUiState.Idle
    }
}