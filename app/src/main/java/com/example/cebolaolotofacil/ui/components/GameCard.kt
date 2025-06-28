package com.example.cebolaolotofacil.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cebolaolotofacil.data.LotofacilGame
import com.example.cebolaolotofacil.ui.theme.CebolaoLotofacilTheme

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun GameCard(
    game: LotofacilGame,
    modifier: Modifier = Modifier,
    isChecking: Boolean,
    onCheckClick: () -> Unit,
    onInfoClick: () -> Unit
) {
    val cardElevation by animateFloatAsState(
        targetValue = if (isChecking) 8f else 2f,
        animationSpec = tween(300),
        label = "cardElevation"
    )

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp)
            .graphicsLayer { shadowElevation = cardElevation },
        elevation = CardDefaults.cardElevation(defaultElevation = cardElevation.dp),
        border = BorderStroke(1.dp, Color.White.copy(alpha = 0.1f))
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(6.dp, Alignment.CenterHorizontally),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                game.numbers.sorted().forEach { number ->
                    NumberBall(number = number)
                }
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                IconButton(onClick = onInfoClick, modifier = Modifier.size(36.dp)) {
                    Icon(Icons.Default.Info, contentDescription = "Ver estatísticas do jogo")
                }

                val rotation by animateFloatAsState(
                    targetValue = if (isChecking) 360f else 0f,
                    animationSpec = tween(durationMillis = 500), label = "checkIconRotation"
                )
                IconButton(
                    onClick = onCheckClick,
                    modifier = Modifier.size(36.dp).graphicsLayer { rotationZ = rotation }
                ) {
                    Icon(Icons.Default.Search, contentDescription = "Conferir jogo no histórico")
                }
            }
        }
    }
}

@Composable
private fun NumberBall(number: Int) {
    Box(
        modifier = Modifier
            .size(32.dp)
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.8f)),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "%02d".format(number),
            color = MaterialTheme.colorScheme.onPrimary,
            fontWeight = FontWeight.Bold,
            fontSize = 12.sp
        )
    }
}

@Preview
@Composable
fun GameCardPreview() {
    val previewGame = LotofacilGame(setOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15))
    CebolaoLotofacilTheme {
        // *** CHAMADA CORRIGIDA ***
        // Fornecemos valores padrão para os novos parâmetros, apenas para o preview funcionar.
        GameCard(
            game = previewGame,
            isChecking = false,
            onCheckClick = {},
            onInfoClick = {}
        )
    }
}