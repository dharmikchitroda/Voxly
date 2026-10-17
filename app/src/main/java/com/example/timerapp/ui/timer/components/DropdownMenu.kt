package com.example.timerapp.ui.timer.components

import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.timerapp.data.model.CompletionSoundPreset
import com.example.timerapp.ui.timer.TimerViewmodel

@Composable
fun SoundDropdownMenu(
    isExpanded: Boolean,
    Sound_list: List<CompletionSoundPreset>,
    viewmodel: TimerViewmodel,
    onDismissRequest: () -> Unit

) {

    DropdownMenu(
        expanded = isExpanded,
        onDismissRequest = { onDismissRequest() },
        modifier = Modifier.width(210.dp),
        shape = RoundedCornerShape(20.dp),
    ) {
        Sound_list.forEach {

            DropdownMenuItem(
                text = { Text(it.name) },
                onClick = {
                    viewmodel.ChangeDefaultDropDownMenu(it)
                    onDismissRequest()
                },
                )
        }


    }
}