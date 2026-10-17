package com.example.timerapp.data.model

import android.net.Uri
import androidx.annotation.RawRes
import androidx.compose.ui.graphics.Path

data class CompletionSoundPreset(
    val name: String,
    @RawRes val music: Int,
    val filePath: String? = null
)
