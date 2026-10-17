package com.example.timerapp.ui.timer.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.timerapp.R


@Composable
fun SoundChip(
    isOn: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val intractionSource = remember { MutableInteractionSource() }
    val isPressed by intractionSource.collectIsPressedAsState()

    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.75f else 1f,
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy),
        label = "buttonScale"
    )

    val backgroundColor by animateColorAsState(
        targetValue = if (isPressed) colorResource(R.color.Primary)
        else colorResource(R.color.Primary).copy(alpha = 0.07f),
        label = "chipBackground"
    )

    val contentColor by animateColorAsState(
        targetValue = if (isPressed) Color.White else colorResource(R.color.Primary),
        label = "chipContent"
    )

    Row(
        modifier = modifier
            .graphicsLayer(
                scaleX = scale,
                scaleY = scale
            )
            .clip(CircleShape)
            .background(backgroundColor)
            .clickable(
                interactionSource = intractionSource,
                indication = ripple(),
                onClick = onClick)
            .padding(horizontal = 20.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = if (isOn) painterResource(R.drawable.outline_notifications_active) else painterResource(
                R.drawable.outline_notifications_off_24
            ),
            contentDescription = null,
            tint = contentColor,
            modifier = Modifier.size(24.dp)
        )

        Spacer(Modifier.width(10.dp))

        Text(
            text = if (isOn) "Sound on" else "Sound off",
            color = contentColor,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium
        )
    }
}
