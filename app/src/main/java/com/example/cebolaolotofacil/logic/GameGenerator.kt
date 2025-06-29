package com.example.cebolaolotofacil.logic

import com.example.cebolaolotofacil.data.FilterState
import com.example.cebolaolotofacil.data.FilterType
import com.example.cebolaolotofacil.data.LotofacilConstants
import com.example.cebolaolotofacil.data.LotofacilGame

// Exceção customizada para carregar a mensagem de erro
class GameGenerationException(message: String) : Exception(message)

object GameGenerator {

    // VALOR PADRÃO PARA O MÁXIMO DE TENTATIVAS
    private const val DEFAULT_MAX_ATTEMPTS = 1_000_000

    fun generateGames(
        activeFilters: List<FilterState>,
        count: Int,
        lastDraw: Set<Int>? = null,
        // CORREÇÃO: Adicionado o parâmetro opcional 'maxAttempts' com um valor padrão.
        // Agora, os testes podem passar um valor menor, mas o app usará o padrão.
        maxAttempts: Int = DEFAULT_MAX_ATTEMPTS
    ): List<LotofacilGame> {
        val validGames = mutableListOf<LotofacilGame>()
        var attempts = 0
        // Usa o parâmetro recebido
        // val maxAttempts = 1_000_000 (linha removida)

        val validationAttempts = mutableMapOf<FilterType, Int>()
        activeFilters.forEach { validationAttempts[it.type] = 0 }

        while (validGames.size < count && attempts < maxAttempts) {
            // Gera um jogo aleatório com 15 dezenas únicas.
            val randomGame = LotofacilGame(
                LotofacilConstants.ALL_NUMBERS.shuffled().take(15).toSet()
            )

            if (isGameValid(randomGame, activeFilters, lastDraw, validationAttempts)) {
                validGames.add(randomGame)
            }
            attempts++
        }

        if (validGames.size < count) {
            // Se não encontrou jogos suficientes, analisa qual filtro mais falhou
            val mostFailedFilter = validationAttempts.maxByOrNull { it.value }
            val errorMessage = mostFailedFilter?.let {
                "Não foi possível gerar os jogos. O filtro '${it.key.title}' parece ser o mais restritivo. Tente ampliar seu intervalo."
            } ?: "Não foi possível gerar jogos com os filtros atuais. Tente ampliar os intervalos."

            throw GameGenerationException(errorMessage)
        }

        return validGames
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
                    // Se não houver um último sorteio carregado, este filtro não pode ser aplicado.
                    if (lastDraw == null) return true
                    game.numbers.count { it in lastDraw }
                }
            }

            if (value.toFloat() !in filterState.selectedRange) {
                // Se o jogo falhar neste filtro, incrementa o contador de falhas
                validationAttempts[filterState.type] = (validationAttempts[filterState.type] ?: 0) + 1
                return false // Falhou na validação
            }
        }
        return true // Passou em todos os filtros
    }
}