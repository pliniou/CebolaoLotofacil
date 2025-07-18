package com.cebolao.lotofacil.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.cebolao.lotofacil.data.CheckResult

@Composable
fun CheckResultCard(result: CheckResult) {
    val totalWins = result.scoreCounts.values.sum()

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp),
        // REFINAMENTO: Forma e cores padronizadas pelo tema.
        shape = MaterialTheme.shapes.extraLarge,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(2.dp)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp) // Sombra controlada por tonalElevation
    ) {
        Column(modifier = Modifier.padding(24.dp)) { // REFINAMENTO: Padding padronizado
            Text(
                "Desempenho Histórico",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(10.dp))

            Text(
                "Este jogo foi comparado com todos os ${result.lastCheckedContest} concursos anteriores.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(24.dp)) // REFINAMENTO: Maior espaçamento para respiro

            // Itera de 15 a 11 para mostrar os resultados mais importantes primeiro.
            (15 downTo 11).forEach { score ->
                ResultRow(label = "$score Acertos:", value = "${result.scoreCounts[score] ?: 0} vezes")
            }

            HorizontalDivider(
                modifier = Modifier.padding(vertical = 16.dp),
                thickness = DividerDefaults.Thickness,
                color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f)
            )

            ResultRow(label = "Total de Prêmios:", value = "$totalWins", isBold = true)
            Spacer(modifier = Modifier.height(16.dp))

            val lastHitText = if (result.lastHitContest != null) {
                val contestsAgo = result.lastCheckedContest - result.lastHitContest
                when (contestsAgo) {
                    0 -> "Pontuou no último concurso (Nº ${result.lastHitContest})!"
                    1 -> "Pontuou há 1 concurso (Nº ${result.lastHitContest})."
                    else -> "Última pontuação há $contestsAgo concursos (Nº ${result.lastHitContest})."
                }
            } else {
                "Este jogo nunca pontuou na faixa de prêmios (11 a 15 acertos)."
            }
            Text(
                text = lastHitText,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

@Composable
private fun ResultRow(label: String, value: String, isBold: Boolean = false) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp), // REFINAMENTO: Espaçamento vertical sutil
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        val fontWeight = if (isBold) FontWeight.Bold else FontWeight.Normal
        val color = if (isBold) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface

        Text(
            text = label,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = fontWeight,
            color = color
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = fontWeight,
            color = color
        )
    }
}