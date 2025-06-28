package com.example.cebolaolotofacil.ui.screens

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.cebolaolotofacil.data.FilterType
import com.example.cebolaolotofacil.ui.components.FilterCard
import com.example.cebolaolotofacil.ui.components.InfoDialog
import com.example.cebolaolotofacil.ui.components.RepeatedNumbersFilterCard
import com.example.cebolaolotofacil.viewmodels.FiltersViewModel
import com.example.cebolaolotofacil.viewmodels.GameViewModel
import com.example.cebolaolotofacil.viewmodels.GenerationUiState
import java.text.NumberFormat
import java.util.Locale
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FiltersScreen(
    filtersViewModel: FiltersViewModel = viewModel(),
    gameViewModel: GameViewModel,
    onNavigateToGeneratedGames: () -> Unit
) {
    val filters by filtersViewModel.filterStates.collectAsState()
    val generationState by filtersViewModel.generationState.collectAsState()
    val lastDrawSelection by filtersViewModel.lastDrawSelection.collectAsState()
    val context = LocalContext.current

    val activeFiltersCount = filters.count { it.isEnabled }
    var gameQuantity by remember { mutableFloatStateOf(1f) }
    var showDialogFor by remember { mutableStateOf<FilterType?>(null) }

    LaunchedEffect(generationState) {
        when (val state = generationState) {
            is GenerationUiState.Success -> {
                gameViewModel.updateGeneratedGames(state.games)
                filtersViewModel.dismissGenerationState()
                onNavigateToGeneratedGames()
            }
            is GenerationUiState.Error -> {
                Toast.makeText(context, state.message, Toast.LENGTH_LONG).show()
                filtersViewModel.dismissGenerationState()
            }
            else -> {}
        }
    }

    showDialogFor?.let { filterType ->
        InfoDialog(
            onDismissRequest = { showDialogFor = null },
            dialogTitle = filterType.title,
            dialogText = filterType.description
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Filtros Inteligentes") },
                actions = {
                    if (activeFiltersCount > 0) {
                        Surface(
                            shape = CircleShape,
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.padding(end = 16.dp)
                        ) {
                            Text(
                                text = "$activeFiltersCount",
                                color = MaterialTheme.colorScheme.onPrimary,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            LazyColumn(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally,
                contentPadding = PaddingValues(vertical = 8.dp)
            ) {
                items(filters) { filterState ->
                    if (filterState.type == FilterType.REPETIDAS_CONCURSO_ANTERIOR) {
                        RepeatedNumbersFilterCard(
                            title = filterState.type.title,
                            filterEnabled = filterState.isEnabled,
                            onFilterEnabledChange = { isEnabled ->
                                filtersViewModel.onFilterEnabledChange(filterState.type, isEnabled)
                            },
                            lastDrawNumbers = lastDrawSelection,
                            onLastDrawNumberClick = { number ->
                                filtersViewModel.onLastDrawNumberClicked(number)
                            },
                            repetitionCount = filterState.selectedRange.start.roundToInt(),
                            onRepetitionCountChange = { count ->
                                val newRange = count.toFloat()..count.toFloat()
                                filtersViewModel.onRangeChange(filterState.type, newRange)
                            },
                            onInfoClick = { showDialogFor = filterState.type }
                        )
                    } else {
                        FilterCard(
                            title = filterState.type.title,
                            filterEnabled = filterState.isEnabled,
                            onFilterEnabledChange = { isEnabled ->
                                filtersViewModel.onFilterEnabledChange(filterState.type, isEnabled)
                            },
                            range = filterState.type.fullRange,
                            selectedRange = filterState.selectedRange,
                            onRangeChange = { newRange ->
                                filtersViewModel.onRangeChange(filterState.type, newRange)
                            },
                            onInfoClick = { showDialogFor = filterState.type }
                        )
                    }
                }
            }

            // Seção inferior com o slider de quantidade e o botão
            Column(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 16.dp),
                horizontalAlignment = Alignment.Start // Alinhado à esquerda
            ) {
                Text(
                    text = "Quantidade de Jogos: ${gameQuantity.roundToInt()}",
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Custo Total: ${NumberFormat.getCurrencyInstance(Locale("pt", "BR")).format(gameQuantity.roundToInt() * 3.0)}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.primary
                )
                Slider(
                    value = gameQuantity,
                    onValueChange = { gameQuantity = it },
                    valueRange = 1f..30f,
                    steps = 28
                )
                Spacer(modifier = Modifier.height(16.dp))

                if (generationState is GenerationUiState.Loading) {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
                } else {
                    Button(
                        onClick = {
                            filtersViewModel.onGenerateGamesClicked(quantity = gameQuantity.roundToInt())
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "Gerar Jogos",
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                }
            }
        }
    }
}