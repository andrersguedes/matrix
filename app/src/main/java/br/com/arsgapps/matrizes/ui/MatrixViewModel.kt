package br.com.arsgapps.matrizes.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import br.com.arsgapps.matrizes.R

enum class Screen { MENU, SETUP, RESULT }

enum class OperationType(
    val title: Int,
    val formula: Int,
    val description: Int,
    val symbol: Int,
    val requiresTwo: Boolean,
    val requiresMatchingDimensions: Boolean,
    val isScalar: Boolean = false
) {
    ADD(R.string.matrix_viewmodel_title_sum,
        R.string.matrix_viewmodel_formula_sum,
        R.string.matrix_viewmodel_description_sum,
        R.string.matrix_viewmodel_symbol_sum, true, true),
    SUBTRACT(R.string.matrix_viewmodel_title_subtract,
        R.string.matrix_viewmodel_formula_subtract,
        R.string.matrix_viewmodel_description_subtract,
        R.string.matrix_viewmodel_symbol_subtract, true, true),
    SCALAR(R.string.matrix_viewmodel_title_scalar,
        R.string.matrix_viewmodel_formula_scalar,
        R.string.matrix_viewmodel_description_scalar,
        R.string.matrix_viewmodel_symbol_scalar, false, false, isScalar = true),
    MULTIPLY(R.string.matrix_viewmodel_title_multiply,
        R.string.matrix_viewmodel_formula_multiply,
        R.string.matrix_viewmodel_description_multiply,
        R.string.matrix_viewmodel_symbol_multiply, true, false)
}

data class CalculationResult(
    val matrix: List<List<Float>>,
    val steps: List<String>,
    val rows: Int,
    val cols: Int
)

class MatrixViewModel : ViewModel() {

    var currentScreen by mutableStateOf(Screen.MENU)
        private set

    var selectedOperation by mutableStateOf(OperationType.ADD)
        private set

    var rowsA by mutableIntStateOf(2)
        private set

    var colsA by mutableIntStateOf(2)
        private set

    var rowsB by mutableIntStateOf(2)
        private set

    var colsB by mutableIntStateOf(2)
        private set

    var scalarValue by mutableStateOf("2")
        private set

    var calculationResult by mutableStateOf<CalculationResult?>(null)
        private set

    // Cell data stored as "row-col" -> string value
    val matrixA = mutableStateMapOf<String, String>(
        "0-0" to "6", "0-1" to "5",
        "1-0" to "8", "1-1" to "2"
    )

    val matrixB = mutableStateMapOf<String, String>(
        "0-0" to "6", "0-1" to "5",
        "1-0" to "2", "1-1" to "2"
    )

    fun selectOperation(op: OperationType) {
        selectedOperation = op
        if (op.requiresMatchingDimensions) {
            rowsB = rowsA
            colsB = colsA
        } else if (op == OperationType.MULTIPLY) {
            rowsB = colsA
        }
        currentScreen = Screen.SETUP
    }

    fun updateRowsA(newRows: Int) {
        val clamped = newRows.coerceIn(1, 4)
        rowsA = clamped
        if (selectedOperation.requiresMatchingDimensions) {
            rowsB = clamped
        }
    }

    fun updateColsA(newCols: Int) {
        val clamped = newCols.coerceIn(1, 4)
        colsA = clamped
        if (selectedOperation.requiresMatchingDimensions) {
            colsB = clamped
        } else if (selectedOperation == OperationType.MULTIPLY) {
            rowsB = clamped
        }
    }

    fun updateRowsB(newRows: Int) {
        rowsB = newRows.coerceIn(1, 4)
    }

    fun updateColsB(newCols: Int) {
        colsB = newCols.coerceIn(1, 4)
    }

    fun updateScalar(value: String) {
        scalarValue = value
    }

    fun updateCellA(row: Int, col: Int, value: String) {
        matrixA["$row-$col"] = value
    }

    fun updateCellB(row: Int, col: Int, value: String) {
        matrixB["$row-$col"] = value
    }

    fun navigateTo(screen: Screen) {
        currentScreen = screen
    }

    fun calculate() {
        calculationResult = computeResult()
        currentScreen = Screen.RESULT
    }

    private fun getVal(map: Map<String, String>, r: Int, c: Int): Float {
        return map["$r-$c"]?.toFloatOrNull() ?: 0f
    }

    fun computeResult(): CalculationResult {
        return when (selectedOperation) {
            OperationType.ADD -> {
                val res = mutableListOf<List<Float>>()
                val steps = mutableListOf<String>()
                for (r in 0 until rowsA) {
                    val rowList = mutableListOf<Float>()
                    for (c in 0 until colsA) {
                        val vA = getVal(matrixA, r, c)
                        val vB = getVal(matrixB, r, c)
                        val sum = vA + vB
                        rowList.add(sum)
                        steps.add("C[${r + 1}][${c + 1}] = A[${r + 1}][${c + 1}] + B[${r + 1}][${c + 1}] = $vA + $vB = $sum")
                    }
                    res.add(rowList)
                }
                CalculationResult(res, steps, rowsA, colsA)
            }

            OperationType.SUBTRACT -> {
                val res = mutableListOf<List<Float>>()
                val steps = mutableListOf<String>()
                for (r in 0 until rowsA) {
                    val rowList = mutableListOf<Float>()
                    for (c in 0 until colsA) {
                        val vA = getVal(matrixA, r, c)
                        val vB = getVal(matrixB, r, c)
                        val diff = vA - vB
                        rowList.add(diff)
                        steps.add("C[${r + 1}][${c + 1}] = A[${r + 1}][${c + 1}] - B[${r + 1}][${c + 1}] = $vA - $vB = $diff")
                    }
                    res.add(rowList)
                }
                CalculationResult(res, steps, rowsA, colsA)
            }

            OperationType.SCALAR -> {
                val res = mutableListOf<List<Float>>()
                val steps = mutableListOf<String>()
                val k = scalarValue.toFloatOrNull() ?: 1f
                for (r in 0 until rowsA) {
                    val rowList = mutableListOf<Float>()
                    for (c in 0 until colsA) {
                        val vA = getVal(matrixA, r, c)
                        val prod = k * vA
                        rowList.add(prod)
                        steps.add("C[${r + 1}][${c + 1}] = $k · A[${r + 1}][${c + 1}] = $k · $vA = $prod")
                    }
                    res.add(rowList)
                }
                CalculationResult(res, steps, rowsA, colsA)
            }

            OperationType.MULTIPLY -> {
                val res = mutableListOf<List<Float>>()
                val steps = mutableListOf<String>()
                val rB = colsA
                for (i in 0 until rowsA) {
                    val rowList = mutableListOf<Float>()
                    for (j in 0 until colsB) {
                        var sum = 0f
                        val terms = mutableListOf<String>()
                        for (k in 0 until rB) {
                            val vA = getVal(matrixA, i, k)
                            val vB = getVal(matrixB, k, j)
                            sum += vA * vB
                            terms.add("($vA × $vB)")
                        }
                        rowList.add(sum)
                        steps.add("C[${i + 1}][${j + 1}] = ${terms.joinToString(" + ")} = $sum")
                    }
                    res.add(rowList)
                }
                CalculationResult(res, steps, rowsA, colsB)
            }
        }
    }
}
