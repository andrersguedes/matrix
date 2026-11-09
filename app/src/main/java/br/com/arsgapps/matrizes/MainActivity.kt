package br.com.arsgapps.matrizes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
import java.util.Locale

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MatrixTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MatrixApp()
                }
            }
        }
    }
}

sealed class Screen {
    object Home : Screen()
    data class Dimension(val operation: Int) : Screen() // 1: Soma, 2: Subtraia, 3: Multi Constante, 4: Multi Matrizes
    data class Input(val operation: Int, val rows: Int, val cols: Int) : Screen()
    data class Result(
        val operation: Int,
        val rows: Int,
        val cols: Int,
        val elementsA: List<Float>,
        val elementsB: List<Float> = emptyList(),
        val constant: Double = 0.0
    ) : Screen()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MatrixApp() {
    var currentScreen by remember { mutableStateOf<Screen>(Screen.Home) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        when (currentScreen) {
                            is Screen.Home -> "Matrizes"
                            is Screen.Dimension -> "Ordem da Matriz"
                            is Screen.Input -> when ((currentScreen as Screen.Input).operation) {
                                1 -> "Soma de Matrizes"
                                2 -> "Subtração de Matrizes"
                                3 -> "Multiplicação por Constante"
                                4 -> "Multiplicação de Matrizes"
                                else -> "Matrizes"
                            }
                            is Screen.Result -> "Resultado"
                        }
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }
    ) { padding ->
        Box(modifier = Modifier.padding(padding)) {
            when (val screen = currentScreen) {
                is Screen.Home -> HomeScreen(
                    onSelectOperation = { op -> currentScreen = Screen.Dimension(op) }
                )
                is Screen.Dimension -> DimensionScreen(
                    operation = screen.operation,
                    onBack = { currentScreen = Screen.Home },
                    onNext = { l, c -> currentScreen = Screen.Input(screen.operation, l, c) }
                )
                is Screen.Input -> MatrixInputScreen(
                    operation = screen.operation,
                    rows = screen.rows,
                    cols = screen.cols,
                    onBack = { currentScreen = Screen.Dimension(screen.operation) },
                    onCalculate = { elemA, elemB, const ->
                        currentScreen = Screen.Result(screen.operation, screen.rows, screen.cols, elemA, elemB, const)
                    }
                )
                is Screen.Result -> ResultScreen(
                    screen = screen,
                    onBack = { currentScreen = Screen.Home }
                )
            }
        }
    }
}

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

@Composable
fun ResultScreen(screen: Screen.Result, onBack: () -> Unit) {
    val l = screen.rows
    val c = screen.cols
    val op = screen.operation
    val elemA = screen.elementsA
    val elemB = screen.elementsB
    val constant = screen.constant

    // Calculate result matrix C
    val finalMatrixC = remember {
        val cRows = if (op == 4) l else l
        val cCols = if (op == 4) l else c
        val res = Array(cRows) { FloatArray(cCols) }

        when (op) {
            1 -> {
                var idx = 0
                for (i in 0 until l) {
                    for (j in 0 until c) {
                        res[i][j] = elemA[idx] + elemB[idx]
                        idx++
                    }
                }
            }
            2 -> {
                var idx = 0
                for (i in 0 until l) {
                    for (j in 0 until c) {
                        res[i][j] = elemA[idx] - elemB[idx]
                        idx++
                    }
                }
            }
            3 -> {
                var idx = 0
                for (i in 0 until l) {
                    for (j in 0 until c) {
                        res[i][j] = (constant * elemA[idx]).toFloat()
                        idx++
                    }
                }
            }
            4 -> {
                val matA = Array(l) { FloatArray(c) }
                var idx = 0
                for (i in 0 until l) {
                    for (j in 0 until c) {
                        matA[i][j] = elemA[idx++]
                    }
                }
                val matB = Array(c) { FloatArray(l) }
                idx = 0
                for (i in 0 until c) {
                    for (j in 0 until l) {
                        matB[i][j] = elemB[idx++]
                    }
                }
                for (i in 0 until l) {
                    for (j in 0 until l) {
                        var sum = 0f
                        for (k in 0 until c) {
                            sum += matA[i][k] * matB[k][j]
                        }
                        res[i][j] = sum
                    }
                }
            }
        }
        res
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Matriz Resultado",
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        Card(
            modifier = Modifier.padding(8.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant
            ),
            shape = MaterialTheme.shapes.large
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                for (row in finalMatrixC) {
                    Row(
                        modifier = Modifier.padding(vertical = 4.dp),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        for (value in row) {
                            Text(
                                text = String.format(Locale.getDefault(), "%.2f  ", value),
                                style = MaterialTheme.typography.bodyLarge,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier.padding(horizontal = 8.dp)
                            )
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = onBack,
            modifier = Modifier.fillMaxWidth(),
            shape = MaterialTheme.shapes.medium
        ) {
            Text("Início")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    MatrixTheme {
        HomeScreen(onSelectOperation = {})
    }
}

@Preview(showBackground = true)
@Composable
fun DimensionScreenPreview() {
    MatrixTheme {
        DimensionScreen(operation = 1, onBack = {}, onNext = { _, _ -> })
    }
}

@Preview(showBackground = true)
@Composable
fun MatrixInputScreenPreview() {
    MatrixTheme {
        MatrixInputScreen(operation = 1, rows = 2, cols = 2, onBack = {}, onCalculate = { _, _, _ -> })
    }
}

@Preview(showBackground = true)
@Composable
fun ResultScreenPreview() {
    MatrixTheme {
        ResultScreen(
            screen = Screen.Result(
                operation = 1,
                rows = 2,
                cols = 2,
                elementsA = listOf(1f, 2f, 3f, 4f),
                elementsB = listOf(5f, 6f, 7f, 8f)
            ),
            onBack = {}
        )
    }
}
