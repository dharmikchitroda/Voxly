package com.example.timerapp.presentation.ui.HomeScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.timerapp.R
import com.example.timerapp.core.util.TimerFormatter
import com.example.timerapp.presentation.ui.theme.Primary_Red



@Composable
fun RoundProgressIndicator(modifier: Modifier = Modifier,viewModel: TimerViewmodel ,time : Int) {
    Box(
        Modifier
            .size(300.dp),
        Alignment.Center,
    ) {
        CircularProgressIndicator(
            progress = { viewModel.progressinf() },
            modifier = Modifier.size(300.dp),
            color = if (viewModel.progressinf() > 0.75f) colorResource(R.color.Secondry)
            else Primary_Red,
            strokeWidth = 15.dp,
            strokeCap = StrokeCap.Round,
            gapSize = 0.dp,
            trackColor = Color.Black.copy(alpha = 0.08f),
        )

        Text(
            TimerFormatter(time),
            fontSize = 105.sp,
            fontFamily = FontFamily(Font(R.font.ds_digit)),
            modifier = Modifier.padding(30.dp)
        )
    }
}