package br.com.arsgapps.matrizes.ui.screens

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.RotateLeft
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import android.annotation.SuppressLint
import androidx.compose.ui.unit.sp
import br.com.arsgapps.matrizes.ui.MatrixViewModel
import br.com.arsgapps.matrizes.ui.Screen
import br.com.arsgapps.matrizes.ui.theme.MatrixTheme

import androidx.compose.ui.platform.LocalLayoutDirection

@Composable
fun ResultScreen(vm: MatrixViewModel, scaffoldPadding: PaddingValues = PaddingValues(0.dp)) {
    val layoutDirection = LocalLayoutDirection.current
    var activeTab by remember { mutableIntStateOf(0) } // 0: Result, 1: Steps
    val result = remember { vm.computeResult() }

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
        // Tab Selector
        item {
            TabRow(
                selectedTabIndex = activeTab,
                containerColor = Color(0xFFE2E8F0),
                modifier = Modifier.clip(RoundedCornerShape(12.dp))
            ) {
                Tab(
                    selected = activeTab == 0,
                    onClick = { activeTab = 0 },
                    text = {
                        Text(
                            "Resultado Matriz",
                            fontWeight = if (activeTab == 0) FontWeight.Bold else FontWeight.Normal,
                            color = if (activeTab == 0) MaterialTheme.colorScheme.primary else Color.Gray
                        )
                    }
                )
                Tab(
                    selected = activeTab == 1,
                    onClick = { activeTab = 1 },
                    text = {
                        Text(
                            "Passo a Passo (${result.steps.size})",
                            fontWeight = if (activeTab == 1) FontWeight.Bold else FontWeight.Normal,
                            color = if (activeTab == 1) MaterialTheme.colorScheme.primary else Color.Gray
                        )
                    }
                )
            }
        }

        // Tab Content 1: Matrix Result Visual
        if (activeTab == 0) {
            item {
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier
                            .padding(20.dp)
                            .fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "MATRIZ RESULTANTE C",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Gray
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        // Visual Result Matrix Bracket Display
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Box(
                                modifier = Modifier
                                    .width(10.dp)
                                    .height(54.dp * result.rows)
                                    .border(
                                        width = 3.dp,
                                        color = MaterialTheme.colorScheme.primary,
                                        shape = RoundedCornerShape(topStart = 6.dp, bottomStart = 6.dp)
                                    )
                            )

                            Spacer(modifier = Modifier.width(10.dp))

                            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                                for (row in result.matrix) {
                                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                        for (valItem in row) {
                                            Box(
                                                modifier = Modifier
                                                    .size(46.dp)
                                                    .clip(RoundedCornerShape(10.dp))
                                                    .background(MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.6f))
                                                    .border(
                                                        1.dp,
                                                        MaterialTheme.colorScheme.primary.copy(alpha = 0.2f),
                                                        RoundedCornerShape(10.dp)
                                                    ),
                                                contentAlignment = Alignment.Center
                                            ) {
                                                Text(
                                                    text = if (valItem % 1f == 0f) valItem.toInt().toString() else "%.1f".format(valItem),
                                                    fontFamily = FontFamily.Monospace,
                                                    fontWeight = FontWeight.Bold,
                                                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                                                    fontSize = 14.sp
                                                )
                                            }
                                        }
                                    }
                                }
                            }

                            Spacer(modifier = Modifier.width(10.dp))

                            Box(
                                modifier = Modifier
                                    .width(10.dp)
                                    .height(54.dp * result.rows)
                                    .border(
                                        width = 3.dp,
                                        color = MaterialTheme.colorScheme.primary,
                                        shape = RoundedCornerShape(topEnd = 6.dp, bottomEnd = 6.dp)
                                    )
                            )
                        }

                        Spacer(modifier = Modifier.height(20.dp))
                        HorizontalDivider(color = Color(0xFFEEEEEE))
                        Spacer(modifier = Modifier.height(12.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("Operação: ${vm.selectedOperation.title}", fontSize = 12.sp, color = Color.Gray)
                            Text("Dimensão: ${result.rows}×${result.cols}", fontSize = 12.sp, color = Color.Gray)
                        }
                    }
                }
            }

            // Input Recap Card
            item {
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFF8F9FA)),
                    border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFE2E8F0)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Text("Entradas Processadas:", fontWeight = FontWeight.Bold, fontSize = 12.sp, color = Color.DarkGray)
                        Spacer(modifier = Modifier.height(6.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceAround
                        ) {
                            Text("A = ${vm.rowsA}x${vm.colsA}", fontFamily = FontFamily.Monospace, fontSize = 12.sp)
                            if (vm.selectedOperation.requiresTwo) {
                                Text("B = ${vm.rowsB}x${vm.colsB}", fontFamily = FontFamily.Monospace, fontSize = 12.sp)
                            }
                            if (vm.selectedOperation.isScalar) {
                                Text("k = ${vm.scalarValue}", fontFamily = FontFamily.Monospace, fontSize = 12.sp)
                            }
                        }
                    }
                }
            }
        }

        // Tab Content 2: Step-by-Step Resolution
        if (activeTab == 1) {
            item {
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Info,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.secondaryFixed,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "DETALHAMENTO DOS CÁLCULOS",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Gray
                            )
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        result.steps.forEachIndexed { idx, step ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp)
                                    .background(Color(0xFFF8F9FA), RoundedCornerShape(10.dp))
                                    .padding(10.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(22.dp)
                                        .clip(RoundedCornerShape(11.dp))
                                        .background(MaterialTheme.colorScheme.primaryContainer),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = "${idx + 1}",
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.primary
                                    )
                                }
                                Spacer(modifier = Modifier.width(10.dp))
                                Text(
                                    text = step,
                                    fontFamily = FontFamily.Monospace,
                                    fontSize = 12.sp,
                                    color = Color.DarkGray
                                )
                            }
                        }
                    }
                }
            }
        }

        // Recalculate Button
        item {
            OutlinedButton(
                onClick = { vm.navigateTo(Screen.SETUP) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Icon(imageVector = Icons.Default.RotateLeft, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Alterar Valores / Dimensões", fontWeight = FontWeight.SemiBold)
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
fun ResultScreenPreview() {
    MatrixTheme {
        ResultScreen(
            vm = MatrixViewModel()
        )
    }
}