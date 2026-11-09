package br.com.arsgapps.matrizes.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import br.com.arsgapps.matrizes.ui.MatrixViewModel
import br.com.arsgapps.matrizes.ui.OperationType

@Composable
fun SetupScreen(vm: MatrixViewModel) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Dimensions for Matrix A
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Dimensões da Matriz A",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.height(8.dp))
                DimensionStepper(
                    label = "Linhas (m)",
                    value = vm.rowsA,
                    onValueChange = { vm.updateRowsA(it) }
                )
                DimensionStepper(
                    label = "Colunas (n)",
                    value = vm.colsA,
                    onValueChange = { vm.updateColsA(it) }
                )
            }
        }

        // Dimensions for Matrix B (if needed)
        if (vm.selectedOperation.requiresTwo) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Dimensões da Matriz B",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    if (!vm.selectedOperation.requiresMatchingDimensions && vm.selectedOperation != OperationType.MULTIPLY) {
                        DimensionStepper(
                            label = "Linhas",
                            value = vm.rowsB,
                            onValueChange = { vm.updateRowsB(it) }
                        )
                    }
                    DimensionStepper(
                        label = "Colunas",
                        value = vm.colsB,
                        onValueChange = { vm.updateColsB(it) }
                    )
                }
            }
        }

        // Scalar value input if scalar operation
        if (vm.selectedOperation.isScalar) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Valor da Constante (k)",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = vm.scalarValue,
                        onValueChange = { vm.updateScalar(it) },
                        label = { Text("Constante k") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )
                }
            }
        }

        // Matrix A Input Grid
        val valuesA = (0 until vm.rowsA).map { r ->
            (0 until vm.colsA).map { c ->
                vm.matrixA["$r-$c"] ?: ""
            }
        }
        MatrixInputGrid(
            label = "Matriz A",
            rows = vm.rowsA,
            cols = vm.colsA,
            values = valuesA,
            onValueChange = { r, c, v -> vm.updateCellA(r, c, v) }
        )

        // Matrix B Input Grid (if needed)
        if (vm.selectedOperation.requiresTwo) {
            val rB = if (vm.selectedOperation == OperationType.MULTIPLY) vm.colsA else vm.rowsB
            val valuesB = (0 until rB).map { r ->
                (0 until vm.colsB).map { c ->
                    vm.matrixB["$r-$c"] ?: ""
                }
            }
            MatrixInputGrid(
                label = "Matriz B",
                rows = rB,
                cols = vm.colsB,
                values = valuesB,
                onValueChange = { r, c, v -> vm.updateCellB(r, c, v) }
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = { vm.calculate() },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = MaterialTheme.shapes.medium
        ) {
            Text("Calcular", style = MaterialTheme.typography.titleMedium)
        }
    }
}
