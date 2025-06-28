package com.example.cebolaolotofacil.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List // <-- IMPORTAÇÃO CORRIGIDA
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Tune
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.cebolaolotofacil.ui.screens.AboutScreen
import com.example.cebolaolotofacil.ui.screens.CheckerScreen
import com.example.cebolaolotofacil.ui.screens.FiltersScreen
import com.example.cebolaolotofacil.ui.screens.GeneratedGamesScreen
import com.example.cebolaolotofacil.ui.screens.HomeScreen


// Sealed class para definir cada tela de forma segura.
sealed class Screen(val route: String, val title: String, val icon: ImageVector) {
    object Home : Screen("home", "Início", Icons.Default.Home)
    object Filters : Screen("filters", "Filtros", Icons.Default.Tune)
    // ***** LINHA CORRIGIDA *****
    object GeneratedGames : Screen("generated_games", "Jogos", Icons.AutoMirrored.Filled.List)
    object Checker : Screen("checker", "Conferidor", Icons.Default.CheckCircle)
    object About : Screen("about", "Sobre", Icons.Default.Info)
}

// Lista de telas para usar na barra de navegação e no pager
val bottomNavItems = listOf(
    Screen.Home,
    Screen.Filters,
    Screen.GeneratedGames,
    Screen.Checker,
    Screen.About
)