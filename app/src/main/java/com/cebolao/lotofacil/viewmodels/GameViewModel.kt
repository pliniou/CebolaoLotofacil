package com.cebolao.lotofacil.viewmodels

import androidx.lifecycle.ViewModel
import com.cebolao.lotofacil.data.LotofacilGame
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class GameViewModel : ViewModel() {
    private val _generatedGames = MutableStateFlow<ImmutableList<LotofacilGame>>(persistentListOf())
    val generatedGames = _generatedGames.asStateFlow()

    fun updateGeneratedGames(games: List<LotofacilGame>) {
        _generatedGames.value = games.toImmutableList()
    }

    fun clearGames() {
        _generatedGames.value = persistentListOf()
    }
}