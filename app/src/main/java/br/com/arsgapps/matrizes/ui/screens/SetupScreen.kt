package br.com.arsgapps.matrizes.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.arsgapps.matrizes.ui.MatrixViewModel
import br.com.arsgapps.matrizes.ui.Screen
import br.com.arsgapps.matrizes.ui.components.DimensionStepper
import br.com.arsgapps.matrizes.ui.components.MatrixBracketInput
import br.com.arsgapps.matrizes.ui.theme.MatrixTheme

import androidx.compose.ui.platform.LocalLayoutDirection

@Composable
fun SetupScreen(vm: MatrixViewModel, scaffoldPadding: PaddingValues = PaddingValues(0.dp)) {
    val layoutDirection = LocalLayoutDirection.current
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .consumeWindowInsets(scaffoldPadding),
        contentPadding = PaddingValues(
            top = scaffoldPadding.calculateTopPadding() + 16.dp,
            bottom = scaffoldPadding.calculateBottomPadding() + 16.dp,
            start = scaffoldPadding.calculateLeftPadding(layoutDirection) + 16.dp,
            end = scaffoldPadding.calculateRightPadding(layoutDirection) + 16.dp
        ),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Dimensions Setup Card
        item {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "ORDEM DAS MATRIZES",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Gray
                        )
                        Surface(
                            color = MaterialTheme.colorScheme.primaryContainer,
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Text(
                                text = if (vm.selectedOperation.isScalar) "A (${vm.rowsA}x${vm.colsA})"
                                else "A (${vm.rowsA}x${vm.colsA}) ${vm.selectedOperation.symbol} B (${vm.rowsB}x${vm.colsB})",
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                                fontSize = 11.sp,
                                fontFamily = FontFamily.Monospace,
                                color = MaterialTheme.colorScheme.onPrimaryContainer,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        // Matrix A Controls
                        Column(
                            modifier = Modifier
                                .weight(1f)
                                .background(Color(0xFFF1F5F9), RoundedCornerShape(12.dp))
                                .padding(10.dp)
                        ) {
                            Text("Matriz A", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                            Spacer(modifier = Modifier.height(6.dp))
                            DimensionStepper(
                                label = "Linhas (m):",
                                value = vm.rowsA,
                                onDecrement = { vm.updateRowsA(vm.rowsA - 1) },
                                onIncrement = { vm.updateRowsA(vm.rowsA + 1) }
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            DimensionStepper(
                                label = "Colunas (n):",
                                value = vm.colsA,
                                onDecrement = { vm.updateColsA(vm.colsA - 1) },
                                onIncrement = { vm.updateColsA(vm.colsA + 1) }
                            )
                        }

                        // Matrix B Controls or Scalar Input
                        if (vm.selectedOperation.requiresTwo) {
                            Column(
                                modifier = Modifier
                                    .weight(1f)
                                    .background(Color(0xFFF1F5F9), RoundedCornerShape(12.dp))
                                    .padding(10.dp)
                            ) {
                                Text("Matriz B", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                                Spacer(modifier = Modifier.height(6.dp))

                                if (vm.selectedOperation.requiresMatchingDimensions) {
                                    Box(
                                        modifier = Modifier.fillMaxHeight(),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            text = "Dimensões sincronizadas (${vm.rowsA}x${vm.colsA})",
                                            fontSize = 11.sp,
                                            color = Color.Gray,
                                            textAlign = TextAlign.Center
                                        )
                                    }
                                } else {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text("Linhas:", fontSize = 11.sp, color = Color.Gray)
                                        Surface(
                                            color = Color.White,
                                            shape = RoundedCornerShape(4.dp),
                                            border = androidx.compose.foundation.BorderStroke(1.dp, Color.LightGray)
                                        ) {
                                            Text(
                                                text = "${vm.rowsB} (Fixo)",
                                                fontSize = 11.sp,
                                                fontWeight = FontWeight.Bold,
                                                color = MaterialTheme.colorScheme.primary,
                                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                            )
                                        }
                                    }
                                    Spacer(modifier = Modifier.height(6.dp))
                                    DimensionStepper(
                                        label = "Colunas (p):",
                                        value = vm.colsB,
                                        onDecrement = { vm.updateColsB(vm.colsB - 1) },
                                        onIncrement = { vm.updateColsB(vm.colsB + 1) }
                                    )
                                }
                            }
                        } else if (vm.selectedOperation.isScalar) {
                            Column(
                                modifier = Modifier
                                    .weight(1f)
                                    .background(Color(0xFFF1F5F9), RoundedCornerShape(12.dp))
                                    .padding(10.dp)
                            ) {
                                Text("Escalar (k)", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                                Spacer(modifier = Modifier.height(8.dp))
                                OutlinedTextField(
                                    value = vm.scalarValue,
                                    onValueChange = { vm.updateScalar(it) },
                                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                    textStyle = LocalTextStyle.current.copy(
                                        textAlign = TextAlign.Center,
                                        fontFamily = FontFamily.Monospace,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.primary
                                    ),
                                    singleLine = true,
                                    modifier = Modifier.fillMaxWidth()
                                )
                            }
                        }
                    }
                }
            }
        }

        // Bracket Cell Inputs Card
        item {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "ELEMENTOS DAS MATRIZES",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Gray
                        )
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.CheckCircle,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.secondaryFixed,
                                modifier = Modifier.size(14.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Edição Direta na Grade", fontSize = 11.sp, color = MaterialTheme.colorScheme.secondaryFixed)
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Matrix A Input Grid
                    Text(
                        text = "Matriz A (${vm.rowsA}×${vm.colsA})",
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    MatrixBracketInput(
                        rows = vm.rowsA,
                        cols = vm.colsA,
                        getValue = { r, c -> vm.matrixA["$r-$c"] ?: "" },
                        onValueChange = { r, c, valStr -> vm.updateCellA(r, c, valStr) }
                    )

                    // Matrix B Input Grid (If required)
                    if (vm.selectedOperation.requiresTwo) {
                        Spacer(modifier = Modifier.height(16.dp))
                        HorizontalDivider(color = Color(0xFFEEEEEE))
                        Spacer(modifier = Modifier.height(12.dp))

                        Text(
                            text = "Matriz B (${vm.rowsB}×${vm.colsB})",
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        MatrixBracketInput(
                            rows = vm.rowsB,
                            cols = vm.colsB,
                            getValue = { r, c -> vm.matrixB["$r-$c"] ?: "" },
                            onValueChange = { r, c, valStr -> vm.updateCellB(r, c, valStr) }
                        )
                    }
                }
            }
        }

        // Action Button
        item {
            Button(
                onClick = { vm.navigateTo(Screen.RESULT) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
            ) {
                Icon(imageVector = Icons.Default.Calculate, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Calcular Operação", fontWeight = FontWeight.Bold, fontSize = 15.sp)
            }
        }
    }
}

@SuppressLint("ViewModelConstructorInComposable")
@Preview(name = "Light Mode", showBackground = true)
@Preview(
    name = "Dark Mode",
    showBackground = true,
    uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun SetupScreenPreview() {
    MatrixTheme {
        SetupScreen(
            vm = MatrixViewModel()
        )
    }
}