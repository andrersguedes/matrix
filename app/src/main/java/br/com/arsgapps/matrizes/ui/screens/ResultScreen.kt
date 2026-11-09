package br.com.arsgapps.matrizes.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.arsgapps.matrizes.ui.theme.MatrixTheme
import java.util.Locale

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
