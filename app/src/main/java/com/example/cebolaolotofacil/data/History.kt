package com.example.cebolaolotofacil.data

// Representa um único sorteio do arquivo de histórico.
data class HistoricalDraw(
    val contestNumber: Int,
    val numbers: Set<Int>
)

// Representa o resultado completo da verificação de um jogo contra todo o histórico.
data class CheckResult(
    // Mapeia a quantidade de acertos (11, 12, etc.) para o número de vezes que ocorreu.
    val scoreCounts: Map<Int, Int> = emptyMap(),
    // Guarda o número do concurso em que o jogo pontuou pela última vez.
    val lastHitContest: Int? = null,
    // Guarda o número do último concurso analisado para calcular "X concursos atrás".
    val lastCheckedContest: Int = 0
)