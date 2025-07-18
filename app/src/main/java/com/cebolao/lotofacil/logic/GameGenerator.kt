package com.cebolao.lotofacil.logic

import com.cebolao.lotofacil.data.FilterState
import com.cebolao.lotofacil.data.FilterType
import com.cebolao.lotofacil.data.LotofacilConstants
import com.cebolao.lotofacil.data.LotofacilGame
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GameGenerationException(message: String) : Exception(message)
object GameGenerator {
    private const val DEFAULT_MAX_ATTEMPTS = 100_000
    suspend fun generateGames(
        activeFilters: List<FilterState>,
        count: Int,
        lastDraw: Set<Int>? = null,
        maxAttempts: Int = DEFAULT_MAX_ATTEMPTS
    ): List<LotofacilGame> = withContext(Dispatchers.Default) {
        val validGames = mutableSetOf<LotofacilGame>()
        var attempts = 0
        val validationFailures = mutableMapOf<FilterType, Int>()
        activeFilters.forEach { validationFailures[it.type] = 0 }
        while (validGames.size < count && attempts < maxAttempts) {
            val randomGame = LotofacilGame(
                LotofacilConstants.ALL_NUMBERS.shuffled().take(15).toSet()
            )
            if (isGameValid(randomGame, activeFilters, lastDraw, validationFailures)) {
                validGames.add(randomGame)
            }
            attempts++
        }
        if (validGames.size < count) {
            // MELHORIA: A lógica de erro foi aprimorada para fornecer feedback mais útil.
            // Agora, ela identifica os filtros mais restritivos e monta uma mensagem clara.
            val topFailedFilters = validationFailures.entries
                .filter { it.value > 0 }
                .sortedByDescending { it.value }
                .take(3) // Pega os 3 principais ofensores
            val errorMessage = if (topFailedFilters.isNotEmpty()) {
                val filterNames = topFailedFilters.joinToString(separator = ", ") { "'${it.key.title}'" }
                "Não foi possível gerar os jogos. Os filtros $filterNames parecem ser muito restritivos. Tente ampliar seus intervalos ou desativar um deles."
            } else {
                "Não foi possível gerar jogos com os filtros atuais. Tente ampliar os intervalos ou reduzir o número de filtros ativos."
            }
            throw GameGenerationException(errorMessage)
        }
        validGames.toList()
    }
    fun isGameValid(
        game: LotofacilGame,
        activeFilters: List<FilterState>,
        lastDraw: Set<Int>?,
        validationFailures: MutableMap<FilterType, Int>
    ): Boolean {
        return activeFilters.all { filter ->
            if (!filter.isEnabled) return@all true
            val value = when (filter.type) {
                FilterType.SOMA_DEZENAS -> game.sum
                FilterType.PARES -> game.evens
                FilterType.IMPARES -> game.odds
                FilterType.PRIMOS -> game.primes
                FilterType.MOLDURA -> game.frame
                FilterType.RETRATO -> game.portrait
                FilterType.FIBONACCI -> game.fibonacci
                FilterType.MULTIPLOS_DE_3 -> game.multiplesOf3
                FilterType.REPETIDAS_CONCURSO_ANTERIOR -> game.repeatedFrom(lastDraw)
            }
            val isValid = value.toFloat() in filter.selectedRange
            if (!isValid) {
                validationFailures[filter.type] = (validationFailures[filter.type] ?: 0) + 1
            }
            isValid
        }
    }
}