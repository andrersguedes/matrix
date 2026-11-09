package br.com.arsgapps.matrizes.ui.screens

import androidx.compose.runtime.Composable
import br.com.arsgapps.matrizes.ui.MatrixViewModel
import br.com.arsgapps.matrizes.ui.OperationType

@Composable
fun MenuScreen(vm: MatrixViewModel) {
    HomeScreen(
        onSelectOperation = { op ->
            val operationType = when (op.id) {
                "add" -> OperationType.ADD
                "sub" -> OperationType.SUBTRACT
                "scalar" -> OperationType.SCALAR
                "mul" -> OperationType.MULTIPLY
                else -> OperationType.ADD
            }
            vm.selectOperation(operationType)
        }
    )
}
