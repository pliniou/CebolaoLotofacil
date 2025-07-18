package com.cebolao.lotofacil.logic

import com.cebolao.lotofacil.data.LotofacilGame

/**
 * Objeto utilitário para centralizar a lógica de análise estatística de um jogo.
 * Utiliza as propriedades pré-calculadas do [LotofacilGame] para maior eficiência.
 */
object GameStatsAnalyzer {
    /**
     * Analisa um jogo e retorna suas estatísticas em um formato de texto.
     */
    fun analyze(game: LotofacilGame): String {
        return """
            Soma das Dezenas: ${game.sum}
            Pares: ${game.evens} | Ímpares: ${game.odds}
            Números Primos: ${game.primes}
            Sequência de Fibonacci: ${game.fibonacci}
            Na Moldura: ${game.frame}
            No Retrato (Miolo): ${game.portrait}
            Múltiplos de 3: ${game.multiplesOf3}
        """.trimIndent()
    }
}