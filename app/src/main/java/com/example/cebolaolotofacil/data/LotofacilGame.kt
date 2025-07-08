package com.example.cebolaolotofacil.data

data class LotofacilGame(val numbers: Set<Int>)

object LotofacilConstants {
    const val GAME_COST = 3.00
    val ALL_NUMBERS = (1..25).toSet()
    // Baseado na sua lista de filtros:
    val PRIMOS = setOf(2, 3, 5, 7, 11, 13, 17, 19, 23)
    val FIBONACCI = setOf(1, 2, 3, 5, 8, 13, 21)
    val MIOLO = setOf(7, 8, 9, 12, 13, 14, 17, 18, 19)
    val MOLDURA = ALL_NUMBERS - MIOLO
    val MULTIPLOS_DE_3 = setOf(3, 6, 9, 12, 15, 18, 21, 24)
}