package com.example.timerapp.ui.timer.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.timerapp.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Topbar(modifier: Modifier = Modifier, bottombar: () -> Unit) {
    TopAppBar(
        title = {

            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(R.drawable.logo_png),
                    "appLogo",
                    modifier = Modifier.size(50.dp)
                )
                Spacer(Modifier.width(1.dp))

                Text(stringResource(R.string.app_name))
            }

        },
        actions = {
            Box(
                Modifier
                    .clip(CircleShape)
                    .background(Color.LightGray)
                    .clickable { bottombar() }
            ) {
                Icon(
                    painter = painterResource(R.drawable.outline_menu_24),
                    "outline_menu",
                    modifier
                        .padding(5.dp)
                        .size(26.dp)
                )
            }
        },
        modifier = Modifier.padding(end = 10.dp)
    )

}
