package com.cebolao.lotofacil.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun NumberGrid(
    modifier: Modifier = Modifier,
    allNumbers: List<Int> = (1..25).toList(),
    selectedNumbers: Set<Int>,
    onNumberClick: (Int) -> Unit,
    maxSelection: Int? = null
) {
    FlowRow(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 4.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterHorizontally),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        maxItemsInEachRow = 5
    ) {
        val isSelectionFull = maxSelection != null && selectedNumbers.size >= maxSelection

        allNumbers.forEach { number ->
            val isSelected = number in selectedNumbers
            val isClickable = !isSelectionFull || isSelected

            val backgroundColor by animateColorAsState(
                targetValue = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceColorAtElevation(2.dp),
                animationSpec = tween(300),
                label = "backgroundColor"
            )
            val textColor by animateColorAsState(
                targetValue = if (isSelected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurfaceVariant,
                animationSpec = tween(300),
                label = "textColor"
            )
            val contentAlpha by animateFloatAsState(
                targetValue = if (isClickable) 1f else 0.5f,
                animationSpec = tween(300),
                label = "contentAlpha"
            )

            NumberCell(
                number = number,
                isSelected = isSelected,
                isClickable = isClickable,
                onClick = {
                    if (isClickable) {
                        onNumberClick(number)
                    }
                },
                backgroundColor = backgroundColor,
                textColor = textColor,
                alpha = contentAlpha,
                maxSelection = maxSelection // CORREÇÃO: Passando o parâmetro para a função filha
            )
        }
    }
}

@Composable
private fun NumberCell(
    number: Int,
    isSelected: Boolean,
    isClickable: Boolean,
    onClick: () -> Unit,
    backgroundColor: Color,
    textColor: Color,
    alpha: Float,
    maxSelection: Int? // CORREÇÃO: Recebendo o parâmetro
) {
    val scale by animateFloatAsState(
        targetValue = if (isSelected) 1.1f else 1.0f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "scaleAnimation"
    )

    Box(
        modifier = Modifier
            .size(40.dp)
            .graphicsLayer {
                scaleX = scale
                scaleY = scale
                this.alpha = alpha
            }
            .clip(CircleShape)
            .background(backgroundColor)
            .clickable(
                onClick = onClick,
                enabled = isClickable,
                role = Role.Checkbox
            )
            .semantics {
                val state = if (isSelected) "selecionado" else "não selecionado"
                // CORREÇÃO: Usando a variável `maxSelection` que agora está no escopo correto.
                val availability =
                    if (!isClickable && !isSelected) ". Indisponível, limite de ${maxSelection ?: 15} números atingido" else ""
                contentDescription = "Número $number, $state$availability"
            },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "%02d".format(number),
            color = textColor,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp
        )
    }
}