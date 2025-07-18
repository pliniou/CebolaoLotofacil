package com.cebolao.lotofacil.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Tune
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavType
import androidx.navigation.navArgument

const val CHECKER_NUMBERS_ARG = "numbers"

sealed class Screen(
    val route: String,
    val title: String,
    val icon: ImageVector
) {
    data object Home : Screen("home", "Início", Icons.Default.Home)
    data object Filters : Screen("filters", "Filtros", Icons.Default.Tune)
    data object GeneratedGames : Screen("generated_games", "Jogos", Icons.AutoMirrored.Filled.List)
    data object About : Screen("about", "Sobre", Icons.Default.Info)
    data object Checker : Screen(
        route = "checker?$CHECKER_NUMBERS_ARG={$CHECKER_NUMBERS_ARG}",
        title = "Conferidor",
        icon = Icons.Default.CheckCircle
    ) {
        fun createRoute(numbers: Set<Int>?): String {
            return if (numbers != null && numbers.isNotEmpty()) {
                "checker?$CHECKER_NUMBERS_ARG=${numbers.joinToString(",")}"
            } else {
                "checker" // Rota base sem argumentos
            }
        }

        // Rota base para verificação no NavGraph
        val routeWithArgs = "checker"

        val arguments = listOf(
            navArgument(CHECKER_NUMBERS_ARG) {
                type = NavType.StringType
                nullable = true
                defaultValue = null
            }
        )
    }
}

val bottomNavItems = listOf(
    Screen.Home,
    Screen.Filters,
    Screen.GeneratedGames,
    Screen.Checker,
    Screen.About
)