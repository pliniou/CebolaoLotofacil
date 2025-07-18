package com.cebolao.lotofacil.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RangeSlider
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cebolao.lotofacil.data.FilterState
import com.cebolao.lotofacil.data.FilterType
import kotlin.math.roundToInt

@Composable
fun FilterCard(
    modifier: Modifier = Modifier,
    filterState: FilterState,
    onEnabledChange: (Boolean) -> Unit,
    onRangeChange: (ClosedFloatingPointRange<Float>) -> Unit,
    onInfoClick: () -> Unit,
    lastDrawNumbers: Set<Int>? = null
) {
    val isRepetitionFilter = filterState.type == FilterType.REPETIDAS_CONCURSO_ANTERIOR
    val isDataAvailable = !isRepetitionFilter || (lastDrawNumbers != null && lastDrawNumbers.isNotEmpty())

    val borderColor by animateColorAsState(
        targetValue = if (filterState.isEnabled && isDataAvailable) MaterialTheme.colorScheme.primary
        else MaterialTheme.colorScheme.outline.copy(alpha = 0.2f), // Borda mais sutil
        animationSpec = tween(300),
        label = "borderColorAnimation"
    )

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp), // Espaçamento vertical reduzido
        shape = MaterialTheme.shapes.large, // Shape um pouco menor que o extraLarge
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(1.dp)),
        border = BorderStroke(1.dp, borderColor) // Borda mais fina
    ) {
        Column(modifier = Modifier.padding(vertical = 12.dp, horizontal = 16.dp)) { // Padding interno reduzido
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                IconButton(onClick = onInfoClick) {
                    Icon(
                        imageVector = Icons.Outlined.Info,
                        contentDescription = "Mais informações sobre ${filterState.type.title}",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(20.dp) // Ícone menor
                    )
                }
                Text(
                    text = filterState.type.title,
                    style = MaterialTheme.typography.titleLarge, // Usando a nova tipografia
                    fontSize = 16.sp, // Ajuste fino
                    modifier = Modifier.weight(1f)
                )
                Switch(
                    checked = filterState.isEnabled,
                    onCheckedChange = onEnabledChange,
                    enabled = isDataAvailable
                )
            }

            AnimatedVisibility(visible = filterState.isEnabled && isDataAvailable) {
                Column(modifier = Modifier.padding(top = 8.dp, start = 8.dp, end = 8.dp)) {
                    // Conteúdo do filtro (slider, etc.)
                    DefaultFilterContent(
                        selectedRange = filterState.selectedRange,
                        fullRange = filterState.type.fullRange,
                        onRangeChange = onRangeChange
                    )
                }
            }
        }
    }
}

@Composable
private fun DefaultFilterContent(
    selectedRange: ClosedFloatingPointRange<Float>,
    fullRange: ClosedFloatingPointRange<Float>,
    onRangeChange: (ClosedFloatingPointRange<Float>) -> Unit
) {
    RangeSlider(
        value = selectedRange,
        onValueChange = onRangeChange,
        valueRange = fullRange,
        steps = (fullRange.endInclusive - fullRange.start).toInt() - 1,
        modifier = Modifier.padding(horizontal = 8.dp)
    )
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 2.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = "Mín: ${selectedRange.start.roundToInt()}", style = MaterialTheme.typography.labelMedium)
        Text(text = "Máx: ${selectedRange.endInclusive.roundToInt()}", style = MaterialTheme.typography.labelMedium)
    }
}