package br.com.arsgapps.matrizes.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import br.com.arsgapps.matrizes.R
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
                                Screen.MENU -> stringResource(R.string.matrix_app_text_menu)
                                Screen.SETUP -> stringResource(vm.selectedOperation.title)
                                Screen.RESULT -> stringResource(R.string.matrix_app_text_result)
                            },
                            fontSize = 18.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                        Text(
                            text = if (vm.currentScreen == Screen.MENU) stringResource(R.string.matrix_app_text_select_calculation)
                            else stringResource(vm.selectedOperation.formula),
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onSecondary.copy(alpha = 0.8f)
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
                                contentDescription = stringResource(R.string.matrix_app_button_back),
                                tint = MaterialTheme.colorScheme.onSecondary
                            )
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onSecondary
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
    uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES,
    backgroundColor = 0xFF000000
)
@Composable
fun MatrixAppPreview() {
    MatrixTheme {
        MatrixApp()
    }
}