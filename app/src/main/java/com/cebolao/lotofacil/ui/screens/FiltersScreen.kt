package com.cebolao.lotofacil.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.cebolao.lotofacil.data.FilterType
import com.cebolao.lotofacil.ui.components.FilterCard
import com.cebolao.lotofacil.ui.components.GenerationActionsPanel
import com.cebolao.lotofacil.ui.components.InfoDialog
import com.cebolao.lotofacil.viewmodels.FiltersViewModel
import com.cebolao.lotofacil.viewmodels.GameViewModel
import com.cebolao.lotofacil.viewmodels.GenerationUiState
import kotlinx.coroutines.launch

@Composable
fun FiltersScreen(
    filtersViewModel: FiltersViewModel = hiltViewModel(),
    gameViewModel: GameViewModel,
    onNavigateToGeneratedGames: () -> Unit
) {
    val filters by filtersViewModel.filterStates.collectAsState()
    val generationState by filtersViewModel.generationState.collectAsState()
    val lastDrawNumbers by filtersViewModel.lastDraw.collectAsState()
    var showDialogFor by remember { mutableStateOf<FilterType?>(null) }
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    LaunchedEffect(generationState) {
        when (val state = generationState) {
            is GenerationUiState.Success -> {
                gameViewModel.updateGeneratedGames(state.games)
                filtersViewModel.dismissGenerationState()
                onNavigateToGeneratedGames()
            }
            is GenerationUiState.Error -> {
                scope.launch {
                    snackbarHostState.showSnackbar(
                        message = state.message,
                        withDismissAction = true,
                        duration = SnackbarDuration.Long
                    )
                }
                filtersViewModel.dismissGenerationState()
            }
            else -> { /* No action needed */ }
        }
    }

    showDialogFor?.let { filterType ->
        InfoDialog(
            onDismissRequest = { showDialogFor = null },
            dialogTitle = filterType.title,
        ) {
            Text(
                text = filterType.description,
                modifier = Modifier.verticalScroll(rememberScrollState()),
                textAlign = TextAlign.Justify,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        containerColor = MaterialTheme.colorScheme.surface,
        bottomBar = {
            GenerationActionsPanel(
                generationState = generationState,
                onGenerate = { quantity ->
                    filtersViewModel.onGenerateGamesClicked(quantity = quantity)
                }
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentPadding = PaddingValues(bottom = 16.dp)
        ) {
            item {
                Column(modifier = Modifier.padding(start = 24.dp, end = 24.dp, top = 24.dp, bottom = 16.dp)) {
                    Text(text = "Gerador Inteligente", style = MaterialTheme.typography.displaySmall)
                    Text(
                        text = "Use filtros estatísticos para otimizar seus jogos.",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
            items(filters, key = { it.type }) { filterState ->
                FilterCard(
                    filterState = filterState,
                    onEnabledChange = { isEnabled ->
                        filtersViewModel.onFilterEnabledChange(filterState.type, isEnabled)
                    },
                    onRangeChange = { newRange ->
                        filtersViewModel.onRangeChange(filterState.type, newRange)
                    },
                    onInfoClick = { showDialogFor = filterState.type },
                    lastDrawNumbers = lastDrawNumbers
                )
            }
        }
    }
}