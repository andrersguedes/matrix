package br.com.arsgapps.matrizes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import br.com.arsgapps.matrizes.ui.screens.*
import br.com.arsgapps.matrizes.ui.theme.MatrixTheme

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
