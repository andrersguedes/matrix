package br.com.arsgapps.matrizes.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.arsgapps.matrizes.R
import br.com.arsgapps.matrizes.ui.MatrixViewModel
import br.com.arsgapps.matrizes.ui.Screen
import br.com.arsgapps.matrizes.ui.components.DimensionStepper
import br.com.arsgapps.matrizes.ui.components.MatrixBracketInput
import br.com.arsgapps.matrizes.ui.theme.MatrixTheme

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
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.onSecondary),
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
                            text = stringResource(R.string.setup_screen_text_matrix_orders),
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Surface(
                            color = MaterialTheme.colorScheme.primaryContainer,
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Text(
                                text = if (vm.selectedOperation.isScalar) stringResource(
                                    R.string.setup_screen_text_a,
                                    vm.rowsA,
                                    vm.colsA
                                )
                                else stringResource(
                                    R.string.setup_screen_text_a_b,
                                    vm.rowsA,
                                    vm.colsA,
                                    stringResource(vm.selectedOperation.symbol),
                                    vm.rowsB,
                                    vm.colsB
                                ),
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
                                .background(MaterialTheme.colorScheme.secondaryContainer, RoundedCornerShape(12.dp))
                                .padding(10.dp)
                        ) {
                            Text(stringResource(R.string.setup_screen_text_matrix_a), fontWeight = FontWeight.Bold, fontSize = 12.sp, color = MaterialTheme.colorScheme.onPrimaryContainer)
                            Spacer(modifier = Modifier.height(6.dp))
                            DimensionStepper(
                                label = stringResource(R.string.setup_screen_text_matrix_a_lines),
                                value = vm.rowsA,
                                onDecrement = { vm.updateRowsA(vm.rowsA - 1) },
                                onIncrement = { vm.updateRowsA(vm.rowsA + 1) }
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            DimensionStepper(
                                label = stringResource(R.string.setup_screen_text_matrix_a_columns),
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
                                    .background(MaterialTheme.colorScheme.secondaryContainer, RoundedCornerShape(12.dp))
                                    .padding(10.dp)
                            ) {
                                Text(stringResource(R.string.setup_screen_text_matrix_b), fontWeight = FontWeight.Bold, fontSize = 12.sp, color = MaterialTheme.colorScheme.onPrimaryContainer)
                                Spacer(modifier = Modifier.height(6.dp))

                                if (!vm.selectedOperation.requiresMatchingDimensions) {
                                    Box(
                                        modifier = Modifier.fillMaxHeight(),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            text = stringResource(
                                                R.string.setup_screen_text_matrix_b_dimensions,
                                                vm.rowsA,
                                                vm.colsA
                                            ),
                                            fontSize = 11.sp,
                                            color = MaterialTheme.colorScheme.onPrimaryContainer,
                                            textAlign = TextAlign.Center
                                        )
                                    }
                                } else {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(stringResource(R.string.setup_screen_text_matrix_b_lines), fontSize = 11.sp, color = MaterialTheme.colorScheme.primary)
                                        Surface(
                                            color = MaterialTheme.colorScheme.onSecondary,
                                            shape = RoundedCornerShape(4.dp),
                                            border = androidx.compose.foundation.BorderStroke(1.dp,
                                                MaterialTheme.colorScheme.primaryContainer)
                                        ) {
                                            Text(
                                                text = stringResource(
                                                    R.string.setup_screen_text_matrix_lines_fixo,
                                                    vm.rowsB
                                                ),
                                                fontSize = 11.sp,
                                                fontWeight = FontWeight.Bold,
                                                color = MaterialTheme.colorScheme.primary,
                                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                            )
                                        }
                                    }
                                    Spacer(modifier = Modifier.height(6.dp))
                                    DimensionStepper(
                                        label = stringResource(R.string.setup_screen_text_matrix_b_columns),
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
                                    .background(MaterialTheme.colorScheme.secondaryContainer, RoundedCornerShape(12.dp))
                                    .padding(10.dp)
                            ) {
                                Text(stringResource(R.string.setup_screen_text_matrix_b_scale), fontWeight = FontWeight.Bold, fontSize = 12.sp, color = MaterialTheme.colorScheme.onPrimaryContainer)
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
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.onSecondary),
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
                            text = stringResource(R.string.setup_screen_text_matrix_elements),
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.CheckCircle,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onSecondaryContainer,
                                modifier = Modifier.size(14.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(stringResource(R.string.setup_screen_text_matrix_grade_edition), fontSize = 11.sp, color = MaterialTheme.colorScheme.onSecondaryContainer)
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Matrix A Input Grid
                    Text(
                        text = stringResource(
                            R.string.setup_screen_text_matrix_a_input,
                            vm.rowsA,
                            vm.colsA
                        ),
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.primary
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
                        HorizontalDivider(color = MaterialTheme.colorScheme.outline)
                        Spacer(modifier = Modifier.height(12.dp))

                        Text(
                            text = stringResource(
                                R.string.setup_screen_text_matrix_b_input,
                                vm.rowsB,
                                vm.colsB
                            ),
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.primary
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
                Text(stringResource(R.string.setup_screen_text_matrix_calculate), fontWeight = FontWeight.Bold, fontSize = 15.sp)
            }
        }
    }
}

@SuppressLint("ViewModelConstructorInComposable")
@Preview(name = "Light Mode", showBackground = true)
@Preview(
    name = "Dark Mode",
    showBackground = true,
    uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES,
    backgroundColor = 0xFF000000
)
@Composable
fun SetupScreenPreview() {
    MatrixTheme {
        SetupScreen(
            vm = MatrixViewModel()
        )
    }
}