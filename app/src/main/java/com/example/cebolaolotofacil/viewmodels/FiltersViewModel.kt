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

// Um sealed class para representar os diferentes estados da nossa UI de forma mais clara.
sealed class GenerationUiState {
    object Idle : GenerationUiState() // Estado inicial
    object Loading : GenerationUiState() // Gerando jogos
    data class Success(val games: List<LotofacilGame>) : GenerationUiState() // Sucesso
    data class Error(val message: String) : GenerationUiState() // Falha
}

class FiltersViewModel : ViewModel() {

    // Renomeei de _filterStates para _uiState para ser mais descritivo do que ele contém
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
        // Usamos .entries que é a forma recomendada para iterar sobre enums em Kotlin moderno
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
        viewModelScope.launch(Dispatchers.Default) {
            _generationState.value = GenerationUiState.Loading
            kotlinx.coroutines.delay(500)

            try { // *** INÍCIO DO BLOCO TRY ***
                val activeFilters = _filterStates.value.filter { it.isEnabled }
                val generatedGames = GameGenerator.generateGames(
                    activeFilters = activeFilters,
                    count = quantity,
                    lastDraw = _lastDrawSelection.value
                )
                // Se chegar aqui, a geração foi um sucesso
                _generationState.value = GenerationUiState.Success(generatedGames)

            } catch (e: GameGenerationException) { // *** CAPTURA A EXCEÇÃO ESPECÍFICA ***
                // Passa a mensagem de erro detalhada para a UI
                _generationState.value = GenerationUiState.Error(e.message ?: "Erro desconhecido na geração.")
            } catch (e: Exception) { // Captura genérica para outros erros
                _generationState.value = GenerationUiState.Error("Ocorreu um erro inesperado.")
            }
        }
    }

    // Função para resetar o estado após o usuário ver a mensagem de sucesso/erro.
    fun dismissGenerationState() {
        _generationState.value = GenerationUiState.Idle
    }
}