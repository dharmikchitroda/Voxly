package com.example.timerapp.ui.timer.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.timerapp.R

// Custome Duration
@Composable
fun CustomDurationbtn(modifier: Modifier = Modifier, onclick: () -> Unit) {

    Box(
        modifier
            .fillMaxWidth()
            .clip(CircleShape)
            .background(
                Color.Black.copy(alpha = 0.06f)
            )
            .clickable(onClick = onclick)
            .padding(horizontal = 10.dp, vertical = 10.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(
            Modifier
                .padding(2.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(R.drawable.outline_menu_24),
                    contentDescription = "Custom Duration",
                    tint = colorResource(R.color.Primary),
                    modifier = Modifier.size(26.dp)
                )
                Text(
                    "Custom Duration",
                    modifier = Modifier.padding(start = 8.dp),
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp
                )
            }

            Icon(
                painter = painterResource(R.drawable.outline_arrow_forward_24),
                contentDescription = "outline_menu",
                modifier = Modifier.size(26.dp)
            )

        }

    }

}
