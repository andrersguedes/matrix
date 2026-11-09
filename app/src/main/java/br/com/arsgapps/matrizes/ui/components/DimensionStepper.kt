package br.com.arsgapps.matrizes.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.arsgapps.matrizes.ui.theme.MatrixTheme

@Composable
fun DimensionStepper(
    label: String,
    value: Int,
    onDecrement: () -> Unit,
    onIncrement: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(label, modifier = Modifier.weight(1F), fontSize = 10.sp, color = Color.Gray)
        Row(verticalAlignment = Alignment.CenterVertically) {
            OutlinedButton(
                onClick = onDecrement,
                contentPadding = PaddingValues(0.dp),
                modifier = Modifier.size(24.dp),
                shape = RoundedCornerShape(6.dp)
            ) {
                Text("-", fontWeight = FontWeight.Bold)
            }
            Text(
                text = "$value",
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
                fontSize = 13.sp,
                modifier = Modifier.width(18.dp),
                textAlign = TextAlign.Center
            )
            OutlinedButton(
                onClick = onIncrement,
                contentPadding = PaddingValues(0.dp),
                modifier = Modifier.size(24.dp),
                shape = RoundedCornerShape(6.dp)
            ) {
                Text("+", fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Preview(name = "Light Mode", showBackground = true)
@Preview(
    name = "Dark Mode",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun DimensionStepperPreview() {
    MatrixTheme {
        DimensionStepper(
            label = "Dimension",
            value = 1,
            onDecrement = {},
            onIncrement = {}
        )
    }
}