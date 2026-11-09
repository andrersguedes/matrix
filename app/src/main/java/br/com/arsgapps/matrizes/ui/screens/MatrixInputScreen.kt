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
fun MatrixInputScreen(
    operation: Int,
    rows: Int,
    cols: Int,
    onBack: () -> Unit,
    onCalculate: (List<Float>, List<Float>, Double) -> Unit
) {
    val quant = rows * cols
    var inputA by remember { mutableStateOf("") }
    var inputB by remember { mutableStateOf("") }
    var constantStr by remember { mutableStateOf("") }
    var errorMsg by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Elementos esperados: $quant",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onBackground
        )
        Text(
            text = "Insira os elementos separados por vírgula (ex: 1,2,3,4)",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        OutlinedTextField(
            value = inputA,
            onValueChange = { inputA = it },
            label = { Text(if (operation == 3) "Elementos da Matriz" else "Elementos da Matriz A") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            shape = MaterialTheme.shapes.medium
        )

        if (operation == 1 || operation == 2 || operation == 4) {
            OutlinedTextField(
                value = inputB,
                onValueChange = { inputB = it },
                label = { Text("Elementos da Matriz B") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                shape = MaterialTheme.shapes.medium
            )
        }

        if (operation == 3) {
            OutlinedTextField(
                value = constantStr,
                onValueChange = { constantStr = it },
                label = { Text("Constante") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                shape = MaterialTheme.shapes.medium
            )
        }

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
                try {
                    val listA = inputA.split(",").map { it.trim().toFloat() }
                    if (listA.size != quant) {
                        errorMsg = "Quantidade de elementos da matriz A não corresponde!"
                        return@Button
                    }
                    var listB = listOf<Float>()
                    if (operation == 1 || operation == 2 || operation == 4) {
                        listB = inputB.split(",").map { it.trim().toFloat() }
                        if (listB.size != quant) {
                            errorMsg = "Quantidade de elementos da matriz B não corresponde!"
                            return@Button
                        }
                    }
                    var constVal = 0.0
                    if (operation == 3) {
                        constVal = constantStr.toDoubleOrNull() ?: 0.0
                    }
                    onCalculate(listA, listB, constVal)
                } catch (e: Exception) {
                    errorMsg = "Erro ao parsear valores. Verifique o formato."
                }
            },
            modifier = Modifier.fillMaxWidth(),
            shape = MaterialTheme.shapes.medium
        ) {
            Text("Calcular")
        }

        TextButton(onClick = onBack, modifier = Modifier.padding(top = 8.dp)) {
            Text("Voltar", color = MaterialTheme.colorScheme.secondary)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MatrixInputScreenPreview() {
    MatrixTheme {
        MatrixInputScreen(operation = 1, rows = 2, cols = 2, onBack = {}, onCalculate = { _, _, _ -> })
    }
}
