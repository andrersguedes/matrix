package br.com.arsgapps.matrizes.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import br.com.arsgapps.matrizes.ui.MatrixViewModel
import br.com.arsgapps.matrizes.ui.Screen
import br.com.arsgapps.matrizes.ui.theme.MatrixTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MatrixApp(vm: MatrixViewModel = viewModel()) {
    BackHandler(enabled = vm.currentScreen != Screen.MENU) {
        vm.navigateTo(
            when (vm.currentScreen) {
                Screen.RESULT -> Screen.SETUP
                Screen.SETUP -> Screen.MENU
                Screen.MENU -> Screen.MENU
            }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            text = when (vm.currentScreen) {
                                Screen.MENU -> "Operações com Matrizes"
                                Screen.SETUP -> vm.selectedOperation.title
                                Screen.RESULT -> "Resultado da Operação"
                            },
                            fontSize = 18.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                        Text(
                            text = if (vm.currentScreen == Screen.MENU) "Selecione o cálculo desejado"
                            else vm.selectedOperation.formula,
                            fontSize = 12.sp,
                            color = Color.White.copy(alpha = 0.8f)
                        )
                    }
                },
                navigationIcon = {
                    if (vm.currentScreen != Screen.MENU) {
                        IconButton(onClick = {
                            vm.navigateTo(
                                if (vm.currentScreen == Screen.RESULT) Screen.SETUP else Screen.MENU
                            )
                        }) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Voltar",
                                tint = Color.White
                            )
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = Color.White
                )
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            Crossfade(targetState = vm.currentScreen, label = "ScreenTransition") { screen ->
                when (screen) {
                    Screen.MENU -> MenuScreen(vm, paddingValues)
                    Screen.SETUP -> SetupScreen(vm, paddingValues)
                    Screen.RESULT -> ResultScreen(vm, paddingValues)
                }
            }
        }
    }
}

@Preview(name = "Light Mode", showBackground = true)
@Preview(
    name = "Dark Mode",
    showBackground = true,
    uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun MatrixAppPreview() {
    MatrixTheme {
        MatrixApp()
    }
}