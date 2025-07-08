package com.example.cebolaolotofacil.logic

import com.example.cebolaolotofacil.data.FilterState
import com.example.cebolaolotofacil.data.FilterType
import com.example.cebolaolotofacil.data.LotofacilConstants
import com.example.cebolaolotofacil.data.LotofacilGame

class GameGenerationException(message: String) : Exception(message)

object GameGenerator {
    private const val DEFAULT_MAX_ATTEMPTS = 100_000

    fun generateGames(
        activeFilters: List<FilterState>,
        count: Int,
        lastDraw: Set<Int>? = null,
        maxAttempts: Int = DEFAULT_MAX_ATTEMPTS
    ): List<LotofacilGame> {
        val validGames = mutableSetOf<LotofacilGame>() // Usa Set para evitar duplicatas
        var attempts = 0
        val validationAttempts = mutableMapOf<FilterType, Int>()
        activeFilters.forEach { validationAttempts[it.type] = 0 }

        while (validGames.size < count && attempts < maxAttempts) {
            val randomGame = LotofacilGame(
                LotofacilConstants.ALL_NUMBERS.shuffled().take(15).toSet()
            )

            if (isGameValid(randomGame, activeFilters, lastDraw, validationAttempts)) {
                validGames.add(randomGame)
            }
            attempts++
        }

        if (validGames.size < count) {
            val mostFailedFilter = validationAttempts.maxByOrNull { it.value }
            val errorMessage = mostFailedFilter?.let {
                "Não foi possível gerar os jogos. O filtro '${it.key.title}' parece ser o mais restritivo. Tente ampliar seu intervalo."
            } ?: "Não foi possível gerar jogos com os filtros atuais. Tente ampliar os intervalos."
            throw GameGenerationException(errorMessage)
        }

        return validGames.toList()
    }

    private fun isGameValid(
        game: LotofacilGame,
        activeFilters: List<FilterState>,
        lastDraw: Set<Int>?,
        validationAttempts: MutableMap<FilterType, Int>
    ): Boolean {
        for (filterState in activeFilters) {
            if (!filterState.isEnabled) continue

            val value = when (filterState.type) {
                FilterType.SOMA_DEZENAS -> game.numbers.sum()
                FilterType.PARES -> game.numbers.count { it % 2 == 0 }
                FilterType.IMPARES -> game.numbers.count { it % 2 != 0 }
                FilterType.PRIMOS -> game.numbers.count { it in LotofacilConstants.PRIMOS }
                FilterType.MOLDURA -> game.numbers.count { it in LotofacilConstants.MOLDURA }
                FilterType.RETRATO -> game.numbers.count { it in LotofacilConstants.MIOLO }
                FilterType.FIBONACCI -> game.numbers.count { it in LotofacilConstants.FIBONACCI }
                FilterType.MULTIPLOS_DE_3 -> game.numbers.count { it % 3 == 0 }
                FilterType.REPETIDAS_CONCURSO_ANTERIOR -> {
                    // Se o último concurso não foi fornecido, o filtro não pode ser aplicado.
                    if (lastDraw == null || lastDraw.size < 15) return true
                    game.numbers.count { it in lastDraw }
                }
            }

            if (value.toFloat() !in filterState.selectedRange) {
                validationAttempts[filterState.type] = (validationAttempts[filterState.type] ?: 0) + 1
                return false
            }
        }
        return true
    }
}