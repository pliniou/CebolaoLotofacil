package com.cebolao.lotofacil.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.cebolao.lotofacil.data.LotofacilConstants
import com.cebolao.lotofacil.ui.components.CheckResultCard
import com.cebolao.lotofacil.ui.components.NumberGrid
import com.cebolao.lotofacil.viewmodels.CheckerUiState
import com.cebolao.lotofacil.viewmodels.CheckerViewModel

@Composable
fun CheckerScreen(
    checkerViewModel: CheckerViewModel
) {
    val checkerState by checkerViewModel.uiState.collectAsState()
    val selectedNumbers by checkerViewModel.selectedNumbers.collectAsState()

    val isButtonEnabled by remember {
        derivedStateOf { selectedNumbers.size == LotofacilConstants.GAME_SIZE && checkerState !is CheckerUiState.Loading }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .padding(top = 24.dp)
    ) {
        Column(modifier = Modifier.padding(horizontal = 24.dp)) {
            Text(text = "Conferidor", style = MaterialTheme.typography.displaySmall)
            Text(
                text = "Compare seu jogo com o histórico de resultados.",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(top = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            NumberGrid(
                selectedNumbers = selectedNumbers,
                onNumberClick = { number -> checkerViewModel.onNumberClicked(number) },
                maxSelection = LotofacilConstants.GAME_SIZE,
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            Box(modifier = Modifier.padding(top = 8.dp)) {
                when (val state = checkerState) {
                    is CheckerUiState.Error -> {
                        Text(
                            text = state.message,
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.bodyMedium,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 24.dp)
                        )
                    }
                    is CheckerUiState.Success -> {
                        CheckResultCard(result = state.result)
                    }
                    else -> { /* Idle or Loading */ }
                }
            }
        }

        Surface(
            modifier = Modifier.fillMaxWidth(),
            shadowElevation = 8.dp
        ) {
            Row(
                modifier = Modifier.padding(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedButton(
                    onClick = { checkerViewModel.clearSelection() },
                    enabled = selectedNumbers.isNotEmpty(),
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Limpar")
                }
                Button(
                    onClick = { checkerViewModel.onCheckGameClicked() },
                    enabled = isButtonEnabled,
                    modifier = Modifier.weight(2f)
                ) {
                    if (checkerState is CheckerUiState.Loading) {
                        CircularProgressIndicator(modifier = Modifier.size(24.dp), color = MaterialTheme.colorScheme.onPrimary)
                    } else {
                        Text("Conferir Jogo")
                    }
                }
            }
        }
    }
}