package com.cebolao.lotofacil.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties

@Composable
fun InfoDialog(
    onDismissRequest: () -> Unit,
    dialogTitle: String,
    content: @Composable () -> Unit
) {
    AlertDialog(
        // REFINAMENTO: Aplicação consistente de formas, cores e propriedades do tema.
        shape = MaterialTheme.shapes.extraLarge,
        properties = DialogProperties(usePlatformDefaultWidth = false),
        modifier = Modifier.padding(horizontal = 24.dp),
        containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(6.dp),
        title = { Text(text = dialogTitle, style = MaterialTheme.typography.headlineSmall) },
        text = {
            Column(modifier = Modifier.padding(vertical = 8.dp)) {
                content()
            }
        },
        onDismissRequest = { onDismissRequest() },
        confirmButton = {
            TextButton(
                onClick = { onDismissRequest() },
                modifier = Modifier.padding(end = 8.dp, bottom = 8.dp)
            ) {
                Text("ENTENDI", style = MaterialTheme.typography.labelLarge)
            }
        }
    )
}