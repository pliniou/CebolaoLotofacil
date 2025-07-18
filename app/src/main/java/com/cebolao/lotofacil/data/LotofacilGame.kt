package com.cebolao.lotofacil.data
import java.math.BigDecimal
/**
 * Representa um jogo da Lotofácil com 15 números.
 *
 * As propriedades estatísticas (soma, pares, ímpares, etc.) são calculadas
 * de forma lazy na primeira vez que são acessadas e armazenadas em cache.
 * Isso otimiza a performance, evitando recálculos repetidos durante a
 * geração e análise de jogos.
 *
 * @property numbers O conjunto de 15 números do jogo.
 */
data class LotofacilGame(val numbers: Set<Int>) {
    init {
        require(numbers.size == LotofacilConstants.GAME_SIZE) { "Um jogo da Lotofácil deve conter exatamente ${LotofacilConstants.GAME_SIZE} números, mas foram fornecidos ${numbers.size}." }
    }
    val sum: Int by lazy { numbers.sum() }
    val evens: Int by lazy { numbers.count { it % 2 == 0 } }
    val odds: Int by lazy { LotofacilConstants.GAME_SIZE - evens }
    val primes: Int by lazy { numbers.count { it in LotofacilConstants.PRIMOS } }
    val fibonacci: Int by lazy { numbers.count { it in LotofacilConstants.FIBONACCI } }
    val frame: Int by lazy { numbers.count { it in LotofacilConstants.MOLDURA } }
    val portrait: Int by lazy { numbers.count { it in LotofacilConstants.MIOLO } }
    val multiplesOf3: Int by lazy { numbers.count { it % 3 == 0 } }
    /**
     * Calcula a quantidade de números deste jogo que estão presentes em um sorteio anterior.
     * @param lastDraw O conjunto de números do sorteio anterior.
     * @return A quantidade de números repetidos.
     */
    fun repeatedFrom(lastDraw: Set<Int>?): Int {
        return lastDraw?.let { numbers.count(it::contains) } ?: 0
    }
}
object LotofacilConstants {
    const val GAME_SIZE = 15
    val GAME_COST = BigDecimal("3.50")
    val ALL_NUMBERS = (1..25).toSet()
    val PRIMOS = setOf(2, 3, 5, 7, 11, 13, 17, 19, 23)
    val FIBONACCI = setOf(1, 2, 3, 5, 8, 13, 21)
    val MIOLO = setOf(7, 8, 9, 12, 13, 14, 17, 18, 19)
    val MOLDURA = ALL_NUMBERS - MIOLO
    val MULTIPLOS_DE_3 = setOf(3, 6, 9, 12, 15, 18, 21, 24)
}