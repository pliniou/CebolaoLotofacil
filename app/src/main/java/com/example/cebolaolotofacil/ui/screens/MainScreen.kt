package com.example.cebolaolotofacil.ui.screens

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.cebolaolotofacil.navigation.Screen
import com.example.cebolaolotofacil.navigation.bottomNavItems
import com.example.cebolaolotofacil.viewmodels.GameViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MainScreen() {
    val gameViewModel: GameViewModel = viewModel()
    val pagerState = rememberPagerState(pageCount = { bottomNavItems.size })
    val scope = rememberCoroutineScope()

    fun navigateToPage(pageIndex: Int) {
        scope.launch {
            pagerState.animateScrollToPage(pageIndex)
        }
    }

    Scaffold(
        bottomBar = {
            NavigationBar(
                containerColor = MaterialTheme.colorScheme.surfaceVariant
            ) {
                bottomNavItems.forEachIndexed { index, screen ->
                    NavigationBarItem(
                        selected = pagerState.currentPage == index,
                        onClick = { navigateToPage(index) },
                        icon = { Icon(screen.icon, contentDescription = screen.title) },
                        label = { Text(screen.title, style = MaterialTheme.typography.labelSmall) },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = MaterialTheme.colorScheme.primary,
                            unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                            selectedTextColor = MaterialTheme.colorScheme.primary,
                            unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
                            indicatorColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)
                        )
                    )
                }
            }
        }
    ) { innerPadding ->
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.padding(innerPadding),
            // beyondBoundsPageCount is deprecated. Use a large enough value to prevent pre-loading adjacent pages.
        ) { pageIndex ->
            // Usa AnimatedContent para uma transição de fade suave entre as páginas.
            AnimatedContent(
                targetState = pageIndex,
                transitionSpec = {
                    fadeIn() togetherWith fadeOut()
                },
                label = "page_transition"
            ) { targetPageIndex ->
                when (bottomNavItems[targetPageIndex]) {
                    Screen.Home -> HomeScreen()
                    Screen.Filters -> FiltersScreen(
                        gameViewModel = gameViewModel,
                        onNavigateToGeneratedGames = { navigateToPage(2) }
                    )
                    Screen.GeneratedGames -> GeneratedGamesScreen(
                        gameViewModel = gameViewModel,
                        onCheckGame = { game ->
                            gameViewModel.setGameForAutoCheck(game.numbers)
                            navigateToPage(3)
                        }
                    )
                    Screen.Checker -> CheckerScreen(gameViewModel = gameViewModel)
                    Screen.About -> AboutScreen()
                }
            }
        }
    }
}