package com.cebolao.lotofacil.ui.screens

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.cebolao.lotofacil.navigation.Screen
import com.cebolao.lotofacil.navigation.bottomNavItems
import com.cebolao.lotofacil.viewmodels.CheckerViewModel
import com.cebolao.lotofacil.viewmodels.GameViewModel

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val gameViewModel: GameViewModel = viewModel()
    val checkerViewModel: CheckerViewModel = hiltViewModel()

    val navigateToScreen = { screen: Screen ->
        val route = if (screen is Screen.Checker) screen.routeWithArgs else screen.route
        navController.navigate(route) {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }

    Scaffold(
        contentWindowInsets = WindowInsets(0.dp),
        bottomBar = {
            NavigationBar(
                tonalElevation = 3.dp,
                containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(3.dp),
                modifier = Modifier.windowInsetsPadding(WindowInsets.navigationBars)
            ) {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination
                bottomNavItems.forEach { screen ->
                    val routeToCheck = if (screen is Screen.Checker) screen.routeWithArgs else screen.route
                    val isSelected = currentDestination?.hierarchy?.any { it.route == routeToCheck } == true
                    NavigationBarItem(
                        selected = isSelected,
                        onClick = { navigateToScreen(screen) },
                        icon = { Icon(screen.icon, contentDescription = screen.title) },
                        label = { Text(screen.title, style = MaterialTheme.typography.labelSmall) },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = MaterialTheme.colorScheme.onPrimaryContainer,
                            unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                            selectedTextColor = MaterialTheme.colorScheme.primary,
                            indicatorColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.6f)
                        )
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(bottom = innerPadding.calculateBottomPadding())
        ) {
            composable(Screen.Home.route) {
                HomeScreen(
                    onNavigate = { screen -> navigateToScreen(screen) }
                )
            }
            composable(Screen.Filters.route) {
                FiltersScreen(
                    gameViewModel = gameViewModel,
                    onNavigateToGeneratedGames = {
                        navController.navigate(Screen.GeneratedGames.route) {
                            popUpTo(Screen.Home.route)
                        }
                    }
                )
            }
            composable(Screen.GeneratedGames.route) {
                GeneratedGamesScreen(
                    gameViewModel = gameViewModel,
                    checkerViewModel = checkerViewModel,
                    onCheckGame = { game ->
                        checkerViewModel.clearSelection()
                        game.numbers.forEach { checkerViewModel.onNumberClicked(it) }
                        navigateToScreen(Screen.Checker)
                    }
                )
            }
            composable(
                route = Screen.Checker.route,
                arguments = Screen.Checker.arguments
            ) {
                CheckerScreen(checkerViewModel = checkerViewModel)
            }
            composable(Screen.About.route) {
                AboutScreen()
            }
        }
    }
}