package com.cebolao.lotofacil.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cebolao.lotofacil.data.FilterState
import com.cebolao.lotofacil.data.FilterType
import com.cebolao.lotofacil.data.LotofacilGame
import com.cebolao.lotofacil.logic.GameGenerationException
import com.cebolao.lotofacil.logic.GameGenerator
import com.cebolao.lotofacil.logic.HistoryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

sealed class GenerationUiState {
    data object Idle : GenerationUiState()
    data object Loading : GenerationUiState()
    data class Success(val games: ImmutableList<LotofacilGame>) : GenerationUiState()
    data class Error(val message: String) : GenerationUiState()
}
@HiltViewModel
class FiltersViewModel @Inject constructor(
    private val historyRepository: HistoryRepository
) : ViewModel() {
    private val _filterStates = MutableStateFlow<List<FilterState>>(emptyList())
    val filterStates: StateFlow<List<FilterState>> = _filterStates.asStateFlow()
    private val _generationState = MutableStateFlow<GenerationUiState>(GenerationUiState.Idle)
    val generationState: StateFlow<GenerationUiState> = _generationState.asStateFlow()
    private val _lastDraw = MutableStateFlow<Set<Int>?>(null)
    val lastDraw: StateFlow<Set<Int>?> = _lastDraw.asStateFlow()
    init {
        initializeFilters()
        loadLastDraw()
    }
    private fun initializeFilters() {
        _filterStates.value = FilterType.entries.map { filterType ->
            FilterState(type = filterType)
        }
    }
    private fun loadLastDraw() {
        viewModelScope.launch {
            try {
                _lastDraw.value = historyRepository.getLastDraw()?.numbers
            } catch (_: IOException) {
                // Erro é tratado silenciosamente para não impedir o uso de outros filtros.
                // A UI já reage à ausência de `lastDraw`.
            }
        }
    }
    fun onFilterEnabledChange(type: FilterType, isEnabled: Boolean) {
        _filterStates.update { current ->
            current.map { if (it.type == type) it.copy(isEnabled = isEnabled) else it }
        }
    }
    fun onRangeChange(type: FilterType, newRange: ClosedFloatingPointRange<Float>) {
        _filterStates.update { current ->
            current.map { if (it.type == type) it.copy(selectedRange = newRange) else it }
        }
    }
    fun onGenerateGamesClicked(quantity: Int) {
        if (_generationState.value is GenerationUiState.Loading) return
        viewModelScope.launch {
            _generationState.value = GenerationUiState.Loading
            try {
                val activeFilters = _filterStates.value.filter { it.isEnabled }
                val lastDrawNumbers = _lastDraw.value
                if (lastDrawNumbers == null && activeFilters.any { it.type == FilterType.REPETIDAS_CONCURSO_ANTERIOR && it.isEnabled }) {
                    throw GameGenerationException("O último concurso não pôde ser carregado. Desative o filtro de dezenas repetidas e tente novamente.")
                }
                val generatedGames = GameGenerator.generateGames(
                    activeFilters = activeFilters,
                    count = quantity,
                    lastDraw = lastDrawNumbers
                )
                _generationState.value = GenerationUiState.Success(generatedGames.toImmutableList())
            } catch (e: GameGenerationException) {
                // MELHORIA: Captura a exceção específica da lógica de geração.
                _generationState.value = GenerationUiState.Error(e.message ?: "Filtros muito restritivos.")
            } catch (e: Exception) {
                // MELHORIA: Captura exceções genéricas, evitando que o app quebre inesperadamente.
                _generationState.value = GenerationUiState.Error("Ocorreu um erro inesperado durante a geração dos jogos.")
            }
        }
    }
    fun dismissGenerationState() {
        _generationState.value = GenerationUiState.Idle
    }
}