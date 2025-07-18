package com.cebolao.lotofacil.ui.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DeleteSweep
import androidx.compose.material.icons.filled.FilterNone
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.*
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.cebolao.lotofacil.data.CheckResult
import com.cebolao.lotofacil.data.LotofacilGame
import com.cebolao.lotofacil.ui.components.GameCard
import com.cebolao.lotofacil.ui.components.InfoDialog
import com.cebolao.lotofacil.viewmodels.CheckerUiState
import com.cebolao.lotofacil.viewmodels.CheckerViewModel
import com.cebolao.lotofacil.viewmodels.GameViewModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GeneratedGamesScreen(
    gameViewModel: GameViewModel = hiltViewModel(),
    checkerViewModel: CheckerViewModel,
    onCheckGame: (LotofacilGame) -> Unit
) {
    val games by gameViewModel.generatedGames.collectAsState()
    var showClearDialog by remember { mutableStateOf(false) }
    var gameForStats by remember { mutableStateOf<LotofacilGame?>(null) }
    val gameCheckResult by checkerViewModel.uiState.collectAsState()

    LaunchedEffect(gameForStats) {
        gameForStats?.let {
            checkerViewModel.checkGameForStats(it.numbers)
        }
    }

    if (showClearDialog) {
        AlertDialog(
            onDismissRequest = { showClearDialog = false },
            title = { Text("Limpar Jogos") },
            text = { Text("Tem certeza que deseja remover todos os ${games.size} jogos gerados?") },
            confirmButton = {
                TextButton(onClick = { gameViewModel.clearGames(); showClearDialog = false }) { Text("Limpar") }
            },
            dismissButton = {
                TextButton(onClick = { showClearDialog = false }) { Text("Cancelar") }
            }
        )
    }

    gameForStats?.let {
        InfoDialog(
            onDismissRequest = {
                gameForStats = null
                checkerViewModel.clearSelection()
            },
            dialogTitle = "Análise do Jogo"
        ) {
            when (val result = gameCheckResult) {
                is CheckerUiState.Loading -> {
                    Box(modifier = Modifier.fillMaxWidth().height(200.dp), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                }
                is CheckerUiState.Success -> {
                    GamePerformanceChart(result = result.result)
                }
                else -> {
                    Box(modifier = Modifier.fillMaxWidth().height(200.dp), contentAlignment = Alignment.Center) {
                        Text("Não foi possível carregar a análise.")
                    }
                }
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Jogos Gerados") },
                actions = {
                    if (games.isNotEmpty()) {
                        IconButton(onClick = { showClearDialog = true }) {
                            Icon(Icons.Default.DeleteSweep, "Limpar todos os jogos")
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.surface)
            )
        }
    ) { innerPadding ->
        if (games.isEmpty()) {
            EmptyState(modifier = Modifier.padding(innerPadding))
        } else {
            GeneratedGamesList(
                games = games,
                onCheckGame = onCheckGame,
                onShowStats = { game -> gameForStats = game },
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Composable
private fun GeneratedGamesList(
    games: ImmutableList<LotofacilGame>,
    onCheckGame: (LotofacilGame) -> Unit,
    onShowStats: (LotofacilGame) -> Unit,
    modifier: Modifier = Modifier
) {
    val coroutineScope = rememberCoroutineScope()
    var checkingGameId by remember { mutableStateOf<Set<Int>?>(null) }

    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(top = 8.dp, bottom = 24.dp)
    ) {
        items(items = games, key = { it.numbers.sorted().joinToString() }) { game ->
            GameCard(
                game = game,
                isChecking = checkingGameId == game.numbers,
                onCheckClick = {
                    checkingGameId = game.numbers
                    coroutineScope.launch {
                        delay(600)
                        onCheckGame(game)
                        checkingGameId = null
                    }
                },
                onInfoClick = { onShowStats(game) }
            )
        }
    }
}

@Composable
private fun EmptyState(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Icon(
                imageVector = Icons.Default.FilterNone,
                contentDescription = null,
                modifier = Modifier.size(48.dp),
                tint = MaterialTheme.colorScheme.primary
            )
            Text(
                text = "Nenhum Jogo Gerado",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = "Vá para a tela de 'Gerador' para criar suas combinações otimizadas.",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center
            )
        }
    }
}

@OptIn(ExperimentalTextApi::class)
@Composable
fun GamePerformanceChart(result: CheckResult) {
    val textMeasurer = rememberTextMeasurer()
    val primaryColor = MaterialTheme.colorScheme.primary
    val onSurfaceColor = MaterialTheme.colorScheme.onSurfaceVariant

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Desempenho nos Últimos 10 Concursos", style = MaterialTheme.typography.titleMedium)
        Spacer(Modifier.height(24.dp))

        Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
                .padding(horizontal = 16.dp)
        ) {
            val barCount = result.recentHits.size
            if (barCount == 0) return@Canvas
            val barWidth = size.width / (barCount * 2)
            val maxHits = 15f

            result.recentHits.forEachIndexed { index, hits ->
                val barHeight = (hits.toFloat() / maxHits) * size.height
                val xOffset = (index * 2 + 0.5f) * barWidth

                drawRect(
                    color = primaryColor.copy(alpha = 0.8f),
                    topLeft = Offset(xOffset, size.height - barHeight),
                    size = androidx.compose.ui.geometry.Size(barWidth, barHeight)
                )

                val textLayoutResult = textMeasurer.measure(
                    text = AnnotatedString(hits.toString()),
                    style = TextStyle(color = if(barHeight > 20) Color.White else primaryColor, fontSize = 12.sp)
                )
                drawText(
                    textLayoutResult = textLayoutResult,
                    topLeft = Offset(xOffset + (barWidth - textLayoutResult.size.width) / 2, size.height - barHeight - textLayoutResult.size.height - 5)
                )
            }
            drawLine(onSurfaceColor, start = Offset(0f, size.height), end = Offset(size.width, size.height), strokeWidth = 2f)
        }
        Spacer(Modifier.height(8.dp))
        Text("Acertos por concurso (da esquerda para direita: mais recente ao mais antigo)",
            style = MaterialTheme.typography.labelSmall,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}