package com.example.cebolaolotofacil.viewmodels

import androidx.lifecycle.ViewModel
import com.example.cebolaolotofacil.data.LotofacilConstants
import com.example.cebolaolotofacil.data.LotofacilGame
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class GameViewModel : ViewModel() {

    private val _generatedGames = MutableStateFlow<List<LotofacilGame>>(emptyList())
    val generatedGames = _generatedGames.asStateFlow()

    private val _gameToAutoCheck = MutableStateFlow<Set<Int>?>(null)
    val gameToAutoCheck = _gameToAutoCheck.asStateFlow()

    fun updateGeneratedGames(games: List<LotofacilGame>) {
        _generatedGames.value = games
    }

    fun clearGames() {
        _generatedGames.value = emptyList()
    }

    fun setGameForAutoCheck(numbers: Set<Int>) {
        _gameToAutoCheck.value = numbers
    }

    fun consumeAutoCheckGame() {
        _gameToAutoCheck.value = null
    }

    /**
     * Analisa um jogo e retorna suas estatísticas em um formato de texto.
     * Esta função deve estar DENTRO da classe GameViewModel.
     */
    fun getGameStats(game: LotofacilGame): String {
        val numbers = game.numbers
        val sum = numbers.sum()
        val evens = numbers.count { it % 2 == 0 }
        val odds = 15 - evens
        val primes = numbers.count { it in LotofacilConstants.PRIMOS }
        val fibonacci = numbers.count { it in LotofacilConstants.FIBONACCI }
        val frame = numbers.count { it in LotofacilConstants.MOLDURA }
        val portrait = numbers.count { it in LotofacilConstants.MIOLO }
        val multiplesOf3 = numbers.count { it % 3 == 0 }

        // Monta a string de resultado com quebras de linha.
        return """
            Soma das Dezenas: $sum
            Pares: $evens | Ímpares: $odds
            Números Primos: $primes
            Sequência de Fibonacci: $fibonacci
            Na Moldura: $frame
            No Retrato (Miolo): $portrait
            Múltiplos de 3: $multiplesOf3
        """.trimIndent()
    }
}