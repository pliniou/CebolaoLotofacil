package com.cebolao.lotofacil.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun StandardScreenHeader(
    modifier: Modifier = Modifier,
    title: String,
    subtitle: String? = null
) {
    Column(
        // REFINAMENTO: Espaçamento padronizado para criar "espaço de respiro" adequado no topo das telas.
        modifier = modifier.padding(start = 24.dp, end = 24.dp, top = 24.dp, bottom = 24.dp)
    ) {
        Text(
            text = title,
            // REFINAMENTO: Utiliza o estilo refinado do tema para garantir consistência.
            style = MaterialTheme.typography.displayMedium,
            color = MaterialTheme.colorScheme.onBackground
        )
        subtitle?.let {
            Text(
                text = it,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}