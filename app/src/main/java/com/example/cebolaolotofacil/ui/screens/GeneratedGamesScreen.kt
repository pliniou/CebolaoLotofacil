package com.example.cebolaolotofacil.ui.screens

import androidx.compose.ui.draw.alpha
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Games
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Badge
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.cebolaolotofacil.data.LotofacilGame
import com.example.cebolaolotofacil.ui.components.GameCard
import com.example.cebolaolotofacil.ui.components.InfoDialog
import com.example.cebolaolotofacil.viewmodels.GameViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import androidx.compose.foundation.Image // Importação necessária
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.ui.res.painterResource // Importação necessária
import com.example.cebolaolotofacil.R // Importação necessária
import androidx.compose.ui.layout.ContentScale // Importação necessária

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun GeneratedGamesScreen(
    gameViewModel: GameViewModel,
    onCheckGame: (LotofacilGame) -> Unit
) {
    val games by gameViewModel.generatedGames.collectAsState()
    val coroutineScope = rememberCoroutineScope()
    var checkingGameId by remember { mutableStateOf<Set<Int>?>(null) }
    var showStatsDialogFor by remember { mutableStateOf<LotofacilGame?>(null) }
    var showClearDialog by remember { mutableStateOf(false) }

    showStatsDialogFor?.let { game ->
        InfoDialog(
            onDismissRequest = { showStatsDialogFor = null },
            dialogTitle = "Estatísticas do Jogo",
            dialogText = gameViewModel.getGameStats(game)
        )
    }

    if (showClearDialog) {
        AlertDialog(
            onDismissRequest = { showClearDialog = false },
            title = { Text("Limpar Jogos") },
            text = { Text("Tem certeza que deseja remover todos os ${games.size} jogos gerados?") },
            confirmButton = {
                TextButton(
                    onClick = {
                        gameViewModel.clearGames()
                        showClearDialog = false
                    },
                    colors = ButtonDefaults.textButtonColors(contentColor = MaterialTheme.colorScheme.error)
                ) { Text("Limpar") }
            },
            dismissButton = {
                TextButton(onClick = { showClearDialog = false }) { Text("Cancelar") }
            }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Jogos Gerados", style = MaterialTheme.typography.titleLarge) },
                actions = {
                    if (games.isNotEmpty()) {
                        Badge(
                            modifier = Modifier.padding(end = 8.dp),
                            containerColor = MaterialTheme.colorScheme.primary
                        ) {
                            Text(
                                text = games.size.toString(),
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onPrimary
                            )
                        }
                        IconButton(onClick = { showClearDialog = true }) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "Limpar todos os jogos",
                                tint = MaterialTheme.colorScheme.error
                            )
                        }
                    }
                }
            )
        }
    ) { innerPadding ->
        if (games.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(24.dp),
                contentAlignment = Alignment.Center
            ) {
                // ***** NOVO: Imagem de fundo sutil para o estado vazio *****
                Image(
                    painter = painterResource(id = R.drawable.icone_600x1000_cartela_lotofacil),
                    contentDescription = null, // Imagem decorativa, o texto explica o contexto
                    modifier = Modifier.fillMaxSize().alpha(0.1f), // Mais transparente
                    contentScale = ContentScale.Crop // Cobrir o Box
                )

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp)),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f)),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(32.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Games,
                            contentDescription = null, // Ícone decorativo, texto explica
                            modifier = Modifier.size(64.dp),
                            tint = MaterialTheme.colorScheme.primary.copy(alpha = 0.7f)
                        )
                        Text(
                            text = "Nenhum jogo gerado",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onSurface,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentPadding = PaddingValues(vertical = 8.dp)
            ) {
                items(games, key = { it.numbers }) { game ->
                    GameCard(
                        game = game,
                        isChecking = checkingGameId == game.numbers,
                        onCheckClick = {
                            checkingGameId = game.numbers
                            coroutineScope.launch {
                                delay(600) // Tempo para a animação do ícone
                                onCheckGame(game)
                                checkingGameId = null // Reseta após a navegação
                            }
                        },
                        onInfoClick = { showStatsDialogFor = game }
                    )
                }
            }
        }
    }
}