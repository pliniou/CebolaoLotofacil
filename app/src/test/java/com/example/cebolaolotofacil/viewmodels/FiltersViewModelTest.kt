package com.example.cebolaolotofacil.viewmodels

import app.cash.turbine.test
import com.example.cebolaolotofacil.data.FilterType
import com.example.cebolaolotofacil.rules.MainDispatcherRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

/**
 * Testes de unidade para o FiltersViewModel.
 * Utiliza uma regra para substituir o dispatcher principal e a biblioteca Turbine para testar Flows.
 */
@ExperimentalCoroutinesApi
class FiltersViewModelTest {

    // Regra para substituir o Dispatchers.Main por um TestDispatcher
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Test
    fun `onFilterEnabledChange deve atualizar o estado do filtro corretamente`() = runTest {
        // Arrange
        val viewModel = FiltersViewModel()
        val filterToChange = FilterType.SOMA_DEZENAS

        // Assert - Estado inicial
        val initialState = viewModel.filterStates.value.first { it.type == filterToChange }
        assertFalse("O filtro deveria começar desabilitado", initialState.isEnabled)

        // Act
        viewModel.onFilterEnabledChange(filterToChange, true)

        // Assert - Novo estado
        val updatedState = viewModel.filterStates.value.first { it.type == filterToChange }
        assertTrue("O filtro deveria ter sido habilitado", updatedState.isEnabled)
    }

    @Test
    fun `onRangeChange deve atualizar o intervalo do filtro corretamente`() = runTest {
        // Arrange
        val viewModel = FiltersViewModel()
        val filterToChange = FilterType.PARES
        val newRange = 5f..7f

        // Act
        viewModel.onRangeChange(filterToChange, newRange)

        // Assert
        val updatedState = viewModel.filterStates.value.first { it.type == filterToChange }
        assertEquals("O intervalo selecionado do filtro deveria ter sido atualizado", newRange, updatedState.selectedRange)
    }

    @Test
    fun `onGenerateGamesClicked com filtros válidos deve emitir Loading e depois Success`() = runTest {
        // Arrange
        val viewModel = FiltersViewModel()

        // Act
        viewModel.onGenerateGamesClicked(5)

        // Assert
        viewModel.generationState.test {
            assertEquals("Deveria emitir o estado de Loading", GenerationUiState.Loading, awaitItem())
            val successState = awaitItem()
            assertTrue("Deveria emitir o estado de Success", successState is GenerationUiState.Success)
            assertEquals(5, (successState as GenerationUiState.Success).games.size)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `onGenerateGamesClicked com filtros impossíveis deve emitir Loading e depois Error`() = runTest {
        val viewModel = FiltersViewModel()
        viewModel.onFilterEnabledChange(FilterType.SOMA_DEZENAS, true)
        viewModel.onRangeChange(FilterType.SOMA_DEZENAS, 10f..20f)

        // Act
        viewModel.onGenerateGamesClicked(1)

        // Assert
        viewModel.generationState.test {
            assertEquals("Deveria emitir Loading", GenerationUiState.Loading, awaitItem())
            val errorState = awaitItem()
            assertTrue("Deveria emitir Error", errorState is GenerationUiState.Error)
            assertTrue(
                "A mensagem de erro deveria ser informativa",
                (errorState as GenerationUiState.Error).message.isNotEmpty()
            )
            cancelAndIgnoreRemainingEvents()
        }
    }
}