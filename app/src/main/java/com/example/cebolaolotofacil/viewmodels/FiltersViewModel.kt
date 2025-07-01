package com.example.cebolaolotofacil.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cebolaolotofacil.data.FilterState
import com.example.cebolaolotofacil.data.FilterType
import com.example.cebolaolotofacil.data.LotofacilGame
import com.example.cebolaolotofacil.logic.GameGenerationException
import com.example.cebolaolotofacil.logic.GameGenerator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

sealed class GenerationUiState {
    data object Idle : GenerationUiState()
    data object Loading : GenerationUiState()
    data class Success(val games: List<LotofacilGame>) : GenerationUiState()
    data class Error(val message: String) : GenerationUiState()
}

class FiltersViewModel : ViewModel() {

    private val _filterStates = MutableStateFlow<List<FilterState>>(listOf())
    val filterStates: StateFlow<List<FilterState>> = _filterStates.asStateFlow()

    private val _generationState = MutableStateFlow<GenerationUiState>(GenerationUiState.Idle)
    val generationState: StateFlow<GenerationUiState> = _generationState.asStateFlow()

    private val _lastDrawSelection = MutableStateFlow<Set<Int>>(emptySet())
    val lastDrawSelection = _lastDrawSelection.asStateFlow()

    init {
        initializeFilters()
    }

    private fun initializeFilters() {
        _filterStates.value = FilterType.entries.map { filterType ->
            FilterState(type = filterType)
        }
    }

    fun onFilterEnabledChange(type: FilterType, isEnabled: Boolean) {
        _filterStates.update { currentState ->
            currentState.map { filterState ->
                if (filterState.type == type) {
                    filterState.copy(isEnabled = isEnabled)
                } else {
                    filterState
                }
            }
        }
    }

    fun onRangeChange(type: FilterType, newRange: ClosedFloatingPointRange<Float>) {
        _filterStates.update { currentState ->
            currentState.map { filterState ->
                if (filterState.type == type) {
                    filterState.copy(selectedRange = newRange)
                } else {
                    filterState
                }
            }
        }
    }

    fun onLastDrawNumberClicked(number: Int) {
        _lastDrawSelection.update { currentSelection ->
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

    fun onGenerateGamesClicked(quantity: Int) {
        // A geração de jogos é uma tarefa de CPU, então Dispatchers.Default está correto.
        viewModelScope.launch(Dispatchers.Default) {
            _generationState.value = GenerationUiState.Loading
            // CORREÇÃO: Removido o delay artificial que não pertence ao código de produção.
            // kotlinx.coroutines.delay(500)

            try {
                val activeFilters = _filterStates.value.filter { it.isEnabled }
                val generatedGames = GameGenerator.generateGames(
                    activeFilters = activeFilters,
                    count = quantity,
                    lastDraw = _lastDrawSelection.value
                )
                _generationState.value = GenerationUiState.Success(generatedGames)

            } catch (e: GameGenerationException) {
                _generationState.value = GenerationUiState.Error(e.message ?: "Erro desconhecido na geração.")
            } catch (e: Exception) {
                _generationState.value = GenerationUiState.Error("Ocorreu um erro inesperado.")
            }
        }
    }

    fun dismissGenerationState() {
        _generationState.value = GenerationUiState.Idle
    }
}