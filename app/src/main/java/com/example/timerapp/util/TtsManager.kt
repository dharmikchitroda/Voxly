package com.example.timerapp.util

import android.content.Context
import android.speech.tts.TextToSpeech
import java.util.Locale

class TtsManager(context: Context) {

    private var tts: TextToSpeech? = null

    init {
        tts = TextToSpeech(context.applicationContext) { status ->

            if (status == TextToSpeech.SUCCESS) {
                val result = tts?.setLanguage(Locale.US)
            }

        }
    }

    fun Speak(text: String) {

        tts?.speak(text, TextToSpeech.QUEUE_FLUSH,null,"timer_tts" )

    }
    fun Shutdown() {
        tts?.stop()
        tts?.shutdown()
        tts = null
    }

}