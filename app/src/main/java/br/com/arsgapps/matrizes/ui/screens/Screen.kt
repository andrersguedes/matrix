package br.com.arsgapps.matrizes.ui.screens

sealed class Screen {
    object Home : Screen()
    data class Dimension(val operation: Int) : Screen()
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
