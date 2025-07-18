package com.cebolao.lotofacil.ui.components

import android.text.Spanned
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.core.text.HtmlCompat

@Composable
fun HtmlText(html: String, modifier: Modifier = Modifier) {
    val annotatedString = remember(html) {
        val cleanedHtml = html.replace(Regex("(?i)<table.*?>.*?</table>"), "")
        val spanned: Spanned = HtmlCompat.fromHtml(cleanedHtml, HtmlCompat.FROM_HTML_MODE_LEGACY)
        buildAnnotatedString {
            append(spanned.toString())
            spanned.getSpans(0, spanned.length, Any::class.java).forEach { span ->
                val start = spanned.getSpanStart(span)
                val end = spanned.getSpanEnd(span)
                when (span) {
                    is android.text.style.StyleSpan -> when (span.style) {
                        android.graphics.Typeface.BOLD -> addStyle(SpanStyle(fontWeight = FontWeight.Bold), start, end)
                    }
                    is android.text.style.UnderlineSpan -> addStyle(SpanStyle(textDecoration = TextDecoration.Underline), start, end)
                }
            }
        }
    }
    Text(
        text = annotatedString,
        style = MaterialTheme.typography.bodyLarge,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        modifier = modifier
    )
}

@Composable
fun ProbabilitiesTable(modifier: Modifier = Modifier) {
    val tableData = remember {
        listOf(
            listOf("Números", "Preço", "Chance (1 em)"),
            listOf("15", "R$ 3,50", "3.268.760"),
            listOf("16", "R$ 56,00", "204.298"),
            listOf("17", "R$ 476,00", "24.035"),
            listOf("18", "R$ 2.856,00", "4.006"),
            listOf("19", "R$ 13.566,00", "843"),
            listOf("20", "R$ 54.264,00", "211")
        )
    }
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(MaterialTheme.shapes.large)
            .border(
                1.dp,
                MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f),
                MaterialTheme.shapes.large
            )
    ) {
        tableData.forEachIndexed { rowIndex, rowData ->
            Row(
                Modifier
                    .fillMaxWidth()
                    .background(
                        if (rowIndex == 0) MaterialTheme.colorScheme.surfaceColorAtElevation(2.dp)
                        else Color.Transparent
                    )
            ) {
                rowData.forEachIndexed { colIndex, cellData ->
                    val weight = when (colIndex) {
                        0 -> 0.25f
                        1 -> 0.35f
                        else -> 0.4f
                    }
                    Text(
                        text = cellData,
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = if (rowIndex == 0) FontWeight.Bold else FontWeight.Normal,
                        color = if (rowIndex == 0) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier
                            .weight(weight)
                            .padding(horizontal = 16.dp, vertical = 12.dp)
                    )
                }
            }
            if (rowIndex < tableData.size - 1) {
                HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f))
            }
        }
    }
}