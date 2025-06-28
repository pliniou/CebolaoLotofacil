package com.example.cebolaolotofacil.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kotlin.math.roundToInt

@Composable
fun RepeatedNumbersFilterCard(
    modifier: Modifier = Modifier,
    title: String,
    filterEnabled: Boolean,
    onFilterEnabledChange: (Boolean) -> Unit,
    lastDrawNumbers: Set<Int>,
    onLastDrawNumberClick: (Int) -> Unit,
    repetitionCount: Int,
    onRepetitionCountChange: (Int) -> Unit,
    onInfoClick: () -> Unit
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp)
            .animateContentSize(), // Anima a mudança de tamanho
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        border = BorderStroke(1.dp, Color.White.copy(alpha = 0.1f))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Cabeçalho do Card
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onInfoClick, modifier = Modifier.size(32.dp)) {
                    Icon(Icons.Default.Info, contentDescription = "Mais informações")
                }
                Spacer(modifier = Modifier.width(4.dp))
                Text(text = title, style = MaterialTheme.typography.titleMedium, modifier = Modifier.weight(1f))
                Switch(
                    checked = filterEnabled,
                    onCheckedChange = onFilterEnabledChange
                )
            }

            // Conteúdo expansível
            AnimatedVisibility(visible = filterEnabled) {
                Column(modifier = Modifier.padding(top = 16.dp)) {
                    Text(
                        "Selecione as 15 dezenas do último sorteio:",
                        style = MaterialTheme.typography.bodySmall
                    )
                    Spacer(Modifier.height(8.dp))

                    NumberGrid(
                        selectedNumbers = lastDrawNumbers,
                        onNumberClick = onLastDrawNumberClick,
                        maxSelection = 15
                    )

                    // Slider de quantidade (só aparece quando 15 dezenas são selecionadas)
                    AnimatedVisibility(visible = lastDrawNumbers.size == 15) {
                        Column(modifier = Modifier.padding(top = 16.dp)) {
                            Text(
                                "Quantas delas devem se repetir?",
                                style = MaterialTheme.typography.bodySmall,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.fillMaxWidth()
                            )
                            Slider(
                                value = repetitionCount.toFloat(),
                                onValueChange = { onRepetitionCountChange(it.roundToInt()) },
                                valueRange = 0f..15f,
                                steps = 14
                            )
                            Text(
                                text = "Repetir $repetitionCount dezenas",
                                style = MaterialTheme.typography.bodyMedium,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    }
                }
            }
        }
    }
}