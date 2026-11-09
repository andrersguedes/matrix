package br.com.arsgapps.matrizes.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.arsgapps.matrizes.ui.theme.MatrixTheme

@Composable
fun HomeScreen(onSelectOperation: (Int) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Escolha a Operação",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        OperationButton(text = "Soma de Matrizes", onClick = { onSelectOperation(1) })
        OperationButton(text = "Subtração de Matrizes", onClick = { onSelectOperation(2) })
        OperationButton(text = "Multiplicação por Constante", onClick = { onSelectOperation(3) })
        OperationButton(text = "Multiplicação de Matrizes", onClick = { onSelectOperation(4) })
    }
}

@Composable
fun OperationButton(text: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary
        ),
        shape = MaterialTheme.shapes.medium
    ) {
        Text(text = text, style = MaterialTheme.typography.bodyLarge)
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    MatrixTheme {
        HomeScreen(onSelectOperation = {})
    }
}
