package com.example.timerapp.ui.timer.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.timerapp.R
import com.example.timerapp.ui.timer.TimerViewmodel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimeChooserDialog(
    modifier: Modifier = Modifier,
    viewmodel: TimerViewmodel,
    onDismiss: () -> Unit
) {

    var minutes by remember { mutableIntStateOf(0) }
    var seconds by remember { mutableIntStateOf(30) }

    Dialog(onDismissRequest = { }) {

        Card(Modifier.fillMaxWidth()) {

            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(25.dp),
                horizontalAlignment = Alignment.CenterHorizontally,

                ) {

                Text("Set Duration", fontWeight = FontWeight.Bold)

                Spacer(Modifier.height(16.dp))

                Row(
                    Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {

                    TimeBox(
                        value = minutes,
                        label = "Minutes",
                        maxValue = 59,
                        onValueChange = { minutes = it },
                        Modifier.weight(1f)
                    )

                    Column() {
                        Text(
                            ":",
                            fontSize = 40.sp,
                            modifier = Modifier.padding(horizontal = 10.dp)
                        )
                        Spacer(Modifier.height(25.dp))
                    }

                    TimeBox(
                        value = seconds,
                        label = "Seconds",
                        maxValue = 59,
                        onValueChange = { seconds = it },
                        modifier.weight(1f)
                    )
                }

                Spacer(Modifier.height(20.dp))

                Button(
                    {
                        viewmodel.SetCustomTime(minutes, seconds)
                        onDismiss()
                    },
                    modifier = Modifier
                        .height(50.dp)
                        .fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(R.color.Primary)
                    )
                ) {
                    Text("Set Timer", fontSize = 16.sp)
                }
            }

        }

    }

}
