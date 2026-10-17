package com.example.timerapp.ui.timer.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.timerapp.ui.timer.TimerViewmodel
@Composable
fun PracticeUI(vm: TimerViewmodel = viewModel()) {

    val count by vm.count.observeAsState()

    Button(
        modifier = Modifier
            .padding(5.dp)
            .fillMaxWidth(), onClick = { vm.increment() }) {
        Text("+", fontSize = 20.sp)
    }

    Text(count.toString(), fontSize = 40.sp)
}