package com.cebolao.lotofacil.viewmodels

import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cebolao.lotofacil.R
import com.cebolao.lotofacil.logic.HistoryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.ImmutableSet
import kotlinx.collections.immutable.toImmutableSet
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class HomeUiState {
    data object Loading : HomeUiState()
    data class Success(val lastDrawStats: LastDrawStats?) : HomeUiState()
}

data class LastDrawStats(
    val contest: Int = 0,
    val numbers: ImmutableSet<Int> = kotlinx.collections.immutable.persistentSetOf(),
    val sum: Int = 0,
    val evens: Int = 0,
    val odds: Int = 0
)

enum class InfoDialogContent(@StringRes val titleRes: Int, @StringRes val textRes: Int) {
    RULES(R.string.about_rules_title, R.string.about_rules_desc),
    PROBS(R.string.about_probs_title, R.string.about_probs_desc),
    BOLOES(R.string.about_bolao_title, R.string.about_bolao_desc)
}

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val historyRepository: HistoryRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState.Loading)
    val uiState = _uiState.asStateFlow()

    private val _dialogContent = MutableStateFlow<InfoDialogContent?>(null)
    val dialogContent = _dialogContent.asStateFlow()

    init {
        loadLastDrawData()
    }

    fun showDialogFor(content: InfoDialogContent) {
        _dialogContent.value = content
    }

    fun dismissDialog() {
        _dialogContent.value = null
    }

    private fun loadLastDrawData() {
        viewModelScope.launch {
            delay(1200)
            try {
                val lastDraw = historyRepository.getLastDraw()
                val stats = lastDraw?.let {
                    LastDrawStats(
                        contest = it.contestNumber,
                        numbers = it.numbers.toImmutableSet(),
                        sum = it.numbers.sum(),
                        evens = it.numbers.count { n -> n % 2 == 0 },
                        odds = 15 - it.numbers.count { n -> n % 2 == 0 }
                    )
                }
                _uiState.value = HomeUiState.Success(stats)
            } catch (_: Exception) {
                _uiState.value = HomeUiState.Success(null)
            }
        }
    }
}