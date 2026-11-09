package br.com.arsgapps.matrizes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import br.com.arsgapps.matrizes.ui.screens.DimensionStepper
import br.com.arsgapps.matrizes.ui.screens.HomeScreen
import br.com.arsgapps.matrizes.ui.screens.MatrixInputGrid
import br.com.arsgapps.matrizes.ui.screens.MatrixOperation
import br.com.arsgapps.matrizes.ui.theme.MatrixTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContent {
            MatrixTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MatrixAppRoot()
                }
            }
        }
    }
}

@Composable
fun MatrixAppRoot() {
    var selectedOperation by remember { mutableStateOf<MatrixOperation?>(null) }
    var rows by remember { mutableStateOf(2) }
    var cols by remember { mutableStateOf(2) }

    // Matrix A & B state values stored as 2D lists of Strings for grid inputs
    var matrixAValues by remember { mutableStateOf(List(2) { List(2) { "" } }) }
    var matrixBValues by remember { mutableStateOf(List(2) { List(2) { "" } }) }
    var scalarValue by remember { mutableStateOf("2") }
    var calculationResult by remember { mutableStateOf<List<List<Double>>?>(null) }

    if (selectedOperation == null) {
        HomeScreen(onSelectOperation = { op ->
            selectedOperation = op
            calculationResult = null
        })
    } else {
        // Operation Input & Calculation Screen
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .padding(20.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Header row with back navigation
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                TextButton(onClick = { selectedOperation = null }) {
                    Text("← Voltar")
                }
                Text(
                    text = selectedOperation!!.title,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }

            // Dimension Steppers Card
            Card(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Dimensões da Matriz",
                        style = MaterialTheme.typography.titleSmall,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    DimensionStepper(label = "Linhas (m)", value = rows, onValueChange = {
                        rows = it
                        matrixAValues = List(rows) { List(cols) { "" } }
                        matrixBValues = List(rows) { List(cols) { "" } }
                    })
                    DimensionStepper(label = "Colunas (n)", value = cols, onValueChange = {
                        cols = it
                        matrixAValues = List(rows) { List(cols) { "" } }
                        matrixBValues = List(rows) { List(cols) { "" } }
                    })
                }
            }

            // Matrix A Input Grid Card
            Card(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Box(modifier = Modifier.padding(16.dp)) {
                    MatrixInputGrid(
                        label = "Matriz A",
                        rows = rows,
                        cols = cols,
                        values = matrixAValues,
                        onValueChange = { r, c, v ->
                            matrixAValues = matrixAValues.toMutableList().apply {
                                this[r] = this[r].toMutableList().apply { this[c] = v }
                            }
                        }
                    )
                }
            }

            // Matrix B or Scalar Input depending on selected operation
            if (selectedOperation?.id == "scalar") {
                Card(
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(text = "Escalar (k)", style = MaterialTheme.typography.titleSmall, color = MaterialTheme.colorScheme.primary)
                        Spacer(modifier = Modifier.height(8.dp))
                        OutlinedTextField(
                            value = scalarValue,
                            onValueChange = { scalarValue = it },
                            label = { Text("Valor de k") },
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true
                        )
                    }
                }
            } else {
                Card(
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Box(modifier = Modifier.padding(16.dp)) {
                        MatrixInputGrid(
                            label = "Matriz B",
                            rows = rows,
                            cols = cols,
                            values = matrixBValues,
                            onValueChange = { r, c, v ->
                                matrixBValues = matrixBValues.toMutableList().apply {
                                    this[r] = this[r].toMutableList().apply { this[c] = v }
                                }
                            }
                        )
                    }
                }
            }

            // Action Button to Run Calculation
            Button(
                onClick = {
                    val res = mutableListOf<List<Double>>()
                    try {
                        for (r in 0 until rows) {
                            val rowList = mutableListOf<Double>()
                            for (c in 0 until cols) {
                                val aVal = matrixAValues[r][c].toDoubleOrNull() ?: 0.0
                                val valRes = when (selectedOperation?.id) {
                                    "add" -> {
                                        val bVal = matrixBValues[r][c].toDoubleOrNull() ?: 0.0
                                        aVal + bVal
                                    }
                                    "sub" -> {
                                        val bVal = matrixBValues[r][c].toDoubleOrNull() ?: 0.0
                                        aVal - bVal
                                    }
                                    "scalar" -> {
                                        val k = scalarValue.toDoubleOrNull() ?: 1.0
                                        aVal * k
                                    }
                                    else -> { // Matrix Multiplication (A · B)
                                        var sum = 0.0
                                        for (kVal in 0 until cols) {
                                            val aElem = matrixAValues[r][kVal].toDoubleOrNull() ?: 0.0
                                            val bElem = matrixBValues[kVal][c].toDoubleOrNull() ?: 0.0
                                            sum += aElem * bElem
                                        }
                                        sum
                                    }
                                }
                                rowList.add(valRes)
                            }
                            res.add(rowList)
                        }
                        calculationResult = res
                    } catch (e: Exception) {
                        calculationResult = null
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = MaterialTheme.shapes.medium
            ) {
                Text("Calcular Operação", style = MaterialTheme.typography.titleMedium)
            }

            // Results Card Display
            if (calculationResult != null) {
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer
                    ),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "Resultado",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        calculationResult?.forEach { row ->
                            Text(
                                text = row.joinToString(prefix = "[ ", postfix = " ]", separator = "  ") {
                                    if (it == it.toInt().toDouble()) it.toInt().toString() else "%.2f".format(it)
                                },
                                style = MaterialTheme.typography.bodyLarge,
                                color = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                        }
                    }
                }
            }
        }
    }
}