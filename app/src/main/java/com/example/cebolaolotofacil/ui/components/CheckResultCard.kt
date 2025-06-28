package com.example.cebolaolotofacil.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.* // Importa o HorizontalDivider daqui
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.cebolaolotofacil.data.CheckResult
import com.example.cebolaolotofacil.ui.theme.CebolaoLotofacilTheme

@Composable
fun CheckResultCard(result: CheckResult) {
    val totalWins = result.scoreCounts.values.sum()

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                "Desempenho Histórico",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(16.dp))

            (15 downTo 11).forEach { score ->
                ResultRow(label = "$score Acertos:", value = "${result.scoreCounts[score] ?: 0}")
            }

            // ***** LINHA CORRIGIDA *****
            HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

            ResultRow(label = "Total de Prêmios:", value = "$totalWins", isBold = true)

            Spacer(modifier = Modifier.height(8.dp))

            val lastHitText = if (result.lastHitContest != null) {
                val contestsAgo = result.lastCheckedContest - result.lastHitContest
                if (contestsAgo == 0) "Pontuou no último concurso!"
                else if (contestsAgo == 1) "Pontuou há 1 concurso."
                else "Pontuou há $contestsAgo concursos."
            } else {
                "Nunca pontuou."
            }
            Text(text = lastHitText, style = MaterialTheme.typography.bodyMedium)
        }
    }
}

@Composable
private fun ResultRow(label: String, value: String, isBold: Boolean = false) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = if (isBold) FontWeight.Bold else FontWeight.Normal
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = if (isBold) FontWeight.Bold else FontWeight.Normal
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CheckResultCardPreview() {
    val previewResult = CheckResult(
        scoreCounts = mapOf(11 to 313, 12 to 65, 13 to 6),
        lastHitContest = 3418,
        lastCheckedContest = 3421
    )
    CebolaoLotofacilTheme {
        CheckResultCard(result = previewResult)
    }
}