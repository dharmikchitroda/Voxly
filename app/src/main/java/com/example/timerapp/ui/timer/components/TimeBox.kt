package com.example.timerapp.ui.timer.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TimeBox(
    value: Int,
    label: String,
    maxValue: Int,
    onValueChange: (Int) -> Unit,
    modifier: Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Text(
            text = "▲",
            fontSize = 25.sp,
            modifier = Modifier.clickable {
                if (value < maxValue) {
                    onValueChange(value + 1)
                }
            }
        )


        BasicTextField(
            value = "%02d".format(value),
            onValueChange = { new ->
                val digits = new.filter { it.isDigit() }.takeLast(2)
                val number = digits.toIntOrNull() ?: 0
                onValueChange(number.coerceIn(0, maxValue))
            },
            singleLine = true,
            textStyle = TextStyle(
                fontSize = 40.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            ),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.padding(vertical = 5.dp),
            decorationBox = { innerTextField ->
                Box(
                    Modifier
                        .size(80.dp)
                        .background(Color.Black.copy(alpha = 0.06f), RoundedCornerShape(12.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    innerTextField()
                }
            }
        )



        Text(
            text = "▼",
            fontSize = 25.sp,
            modifier = Modifier.clickable {
                if (value > 0) {
                    onValueChange(value - 1)
                }
            }
        )

        Text(label)
    }
}
