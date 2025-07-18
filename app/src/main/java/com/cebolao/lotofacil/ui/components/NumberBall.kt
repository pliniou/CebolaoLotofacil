package com.cebolao.lotofacil.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
internal fun NumberBall(
    number: Int,
    modifier: Modifier = Modifier,
    size: Dp = 34.dp // Tamanho padrão refinado
) {
    Box(
        modifier = modifier
            .size(size)
            .clip(CircleShape)
            // Efeito de gradiente sutil para dar profundidade
            .background(
                Brush.radialGradient(
                    colors = listOf(
                        MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.7f),
                        MaterialTheme.colorScheme.primaryContainer
                    ),
                    radius = size.value * 0.7f
                )
            )
            .semantics { contentDescription = "Número $number" },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "%02d".format(number),
            color = MaterialTheme.colorScheme.onPrimaryContainer,
            style = MaterialTheme.typography.labelLarge,
            fontSize = (size.value / 2.4).sp // Fonte se adapta ao tamanho da bola
        )
    }
}