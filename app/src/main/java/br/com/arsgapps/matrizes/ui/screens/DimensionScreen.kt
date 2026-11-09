package br.com.arsgapps.matrizes.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.arsgapps.matrizes.ui.theme.MatrixTheme

@Composable
fun DimensionScreen(operation: Int, onBack: () -> Unit, onNext: (Int, Int) -> Unit) {
    var rowsText by remember { mutableStateOf("") }
    var colsText by remember { mutableStateOf("") }
    var errorMsg by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Informe a ordem da matriz",
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        OutlinedTextField(
            value = rowsText,
            onValueChange = { rowsText = it },
            label = { Text("Número de Linhas") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            shape = MaterialTheme.shapes.medium
        )

        OutlinedTextField(
            value = colsText,
            onValueChange = { colsText = it },
            label = { Text("Número de Colunas") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            shape = MaterialTheme.shapes.medium
        )

        val currentError = errorMsg
        if (currentError != null) {
            Text(
                text = currentError,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                val l = rowsText.toIntOrNull() ?: 0
                val c = colsText.toIntOrNull() ?: 0
                if (l > 0 && c > 0) {
                    onNext(l, c)
                } else {
                    errorMsg = "Insira valores válidos (> 0)"
                }
            },
            modifier = Modifier.fillMaxWidth(),
            shape = MaterialTheme.shapes.medium
        ) {
            Text("Avançar")
        }

        TextButton(onClick = onBack, modifier = Modifier.padding(top = 8.dp)) {
            Text("Voltar", color = MaterialTheme.colorScheme.secondary)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DimensionScreenPreview() {
    MatrixTheme {
        DimensionScreen(operation = 1, onBack = {}, onNext = { _, _ -> })
    }
}
