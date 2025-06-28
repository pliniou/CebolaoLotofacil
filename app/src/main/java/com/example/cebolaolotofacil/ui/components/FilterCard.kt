package com.example.cebolaolotofacil.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.material3.RangeSlider
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.cebolaolotofacil.ui.theme.CebolaoLotofacilTheme
import kotlin.math.roundToInt

@Composable
fun FilterCard(
    modifier: Modifier = Modifier,
    title: String,
    filterEnabled: Boolean,
    onFilterEnabledChange: (Boolean) -> Unit,
    range: ClosedFloatingPointRange<Float>,
    selectedRange: ClosedFloatingPointRange<Float>,
    onRangeChange: (ClosedFloatingPointRange<Float>) -> Unit,
    onInfoClick: () -> Unit
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp)
            .animateContentSize(), // Anima a mudança de tamanho do card
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp), // A translucidez já dá destaque
        border = BorderStroke(1.dp, Color.White.copy(alpha = 0.1f)) // Borda sutil
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
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

            // O conteúdo só é visível se o filtro estiver habilitado
            AnimatedVisibility(visible = filterEnabled) {
                Column(modifier = Modifier.padding(top = 16.dp)) {
                    RangeSlider(
                        value = selectedRange,
                        onValueChange = onRangeChange,
                        valueRange = range,
                        steps = (range.endInclusive - range.start).toInt() - 1
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = "De: ${selectedRange.start.roundToInt()}", style = MaterialTheme.typography.bodySmall)
                        Text(text = "Até: ${selectedRange.endInclusive.roundToInt()}", style = MaterialTheme.typography.bodySmall)
                    }
                }
            }
        }
    }
}

// O Preview nos permite ver nosso componente no modo de Design sem rodar o app.
@Preview(showBackground = true)
@Composable
fun FilterCardPreview() {
    CebolaoLotofacilTheme {
        var enabled by remember { mutableStateOf(true) }
        var selectedRange by remember { mutableStateOf(50f..150f) }

        FilterCard(
            title = "Soma das Dezenas",
            filterEnabled = enabled,
            onFilterEnabledChange = { enabled = it },
            range = 30f..230f,
            selectedRange = selectedRange,
            onRangeChange = { selectedRange = it },
            onInfoClick = {}
        )
    }
}