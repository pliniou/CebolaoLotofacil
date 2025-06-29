package com.example.cebolaolotofacil.logic

import com.example.cebolaolotofacil.data.FilterState
import com.example.cebolaolotofacil.data.FilterType
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import kotlin.test.assertFailsWith

/**
 * Testes de unidade para o GameGenerator.
 * Foca em validar a lógica de geração de jogos e o tratamento de exceções.
 */
class GameGeneratorTest {

    @Test
    fun `generateGames com filtros válidos deve retornar a quantidade correta de jogos`() {
        // Arrange
        val quantity = 10
        val sumFilter = FilterState(
            type = FilterType.SOMA_DEZENAS,
            isEnabled = true,
            selectedRange = 170f..220f
        )
        val activeFilters = listOf(sumFilter)

        // Act
        val games = GameGenerator.generateGames(activeFilters, quantity)

        // Assert
        assertEquals(quantity, games.size)
        games.forEach { game ->
            val sum = game.numbers.sum()
            assertTrue(
                "A soma do jogo ${game.numbers} ($sum) deveria estar entre 170 e 220",
                sum in 170..220
            )
        }
    }

    @Test
    fun `generateGames com filtros impossíveis deve lançar GameGenerationException`() {
        // Arrange
        val quantity = 5
        val impossibleSumFilter = FilterState(
            type = FilterType.SOMA_DEZENAS,
            isEnabled = true,
            selectedRange = 10f..20f
        )
        val activeFilters = listOf(impossibleSumFilter)

        // Act & Assert
        val exception = assertFailsWith<GameGenerationException> {
            // CORREÇÃO: Esta chamada agora é válida porque adicionamos o parâmetro na função.
            GameGenerator.generateGames(activeFilters, quantity, maxAttempts = 5000)
        }

        assertTrue(
            "A mensagem de exceção deveria mencionar o filtro restritivo",
            exception.message?.contains(FilterType.SOMA_DEZENAS.title) == true
        )
    }

    @Test
    fun `generateGames com filtro de repetidas deve respeitar o filtro`() {
        // Arrange
        val quantity = 5
        val lastDraw = setOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)
        // Queremos que exatamente 9 números do sorteio anterior se repitam
        val repeatFilter = FilterState(
            type = FilterType.REPETIDAS_CONCURSO_ANTERIOR,
            isEnabled = true,
            selectedRange = 9f..9f
        )
        val activeFilters = listOf(repeatFilter)

        // Act
        val games = GameGenerator.generateGames(activeFilters, quantity, lastDraw)

        // Assert
        assertEquals(quantity, games.size)
        games.forEach { game ->
            val repeatedCount = game.numbers.intersect(lastDraw).size
            assertEquals(
                "O jogo ${game.numbers} deveria ter 9 dezenas repetidas",
                9,
                repeatedCount
            )
        }
    }
}