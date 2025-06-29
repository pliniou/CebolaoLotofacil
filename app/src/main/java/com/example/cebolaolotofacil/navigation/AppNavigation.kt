package com.example.cebolaolotofacil.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List // <-- IMPORTAÇÃO CORRIGIDA
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Tune
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(val title: String, val icon: ImageVector) {
    data object Home : Screen("Início", Icons.Default.Home)
    data object Filters : Screen("Filtros", Icons.Default.Tune)
    data object GeneratedGames : Screen("Jogos", Icons.AutoMirrored.Filled.List)
    data object Checker : Screen("Conferidor", Icons.Default.CheckCircle)
    data object About : Screen("Sobre", Icons.Default.Info)
}

val bottomNavItems = listOf(
    Screen.Home,
    Screen.Filters,
    Screen.GeneratedGames,
    Screen.Checker,
    Screen.About
)