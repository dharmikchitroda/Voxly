package com.example.timerapp.util

import android.content.Context
import android.util.Log
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import org.json.JSONObject
import org.vosk.Model
import org.vosk.Recognizer
import org.vosk.android.RecognitionListener
import org.vosk.android.SpeechService
import org.vosk.android.StorageService
import java.lang.Exception

class VoskManager(context: Context) : RecognitionListener {

    private val appContext = context.applicationContext   // not Activity context
    private var model: Model? = null
    private var speechService: SpeechService? = null

    private val _isReady = MutableStateFlow(false)
    val isReady: StateFlow<Boolean> = _isReady.asStateFlow()

    private val _isListening = MutableStateFlow(false)
    val isListening: StateFlow<Boolean> = _isListening.asStateFlow()

    private val _commands = MutableSharedFlow<String>(extraBufferCapacity = 1)
    val commands = _commands.asSharedFlow()

    fun init() {
        if (model != null) return
        StorageService.unpack(
            appContext, "model-en-us", "model",
            { m -> model = m; _isReady.value = true ; Log.d("Vosk", "Model loaded")

            },
            { e -> Log.e("Vosk", "Model error: ${e.message}") }
        )
    }

    fun startListening() {
        val m = model ?: return

        if (speechService != null) return

        val GRAMMAR = "[\"start\", \"stop\", \"pause\", \"reset\", \"[unk]\"]"

        try {
//          val rec = Recognizer(m, 16000.0f, GRAMMAR)
            val rec = Recognizer(m, 16000.0f)
            speechService = SpeechService(rec, 16000.0f).also { it.startListening(this) }
            _isListening.value = true
        } catch (e: Exception) {
            Log.e("Vosk", "Start error: ${e.message}")
            _isListening.value = false
        }
    }

    fun stopListening() {
        speechService?.stop()
        speechService?.shutdown()
        speechService = null
        _isListening.value = false
    }

    fun release() {
        stopListening()
        model?.close()
        model = null
        _isReady.value = false
    }

    override fun onResult(hypothesis: String?) {
        val text = JSONObject(hypothesis ?: return).optString("text")
//        if (text.isNotBlank() && text != "[unk]") _commands.tryEmit(text)
        if (text.isNotBlank()) _commands.tryEmit(text)
    }

    override fun onPartialResult(hypothesis: String?) {
    }
    override fun onFinalResult(hypothesis: String?) {}
    override fun onError(exception: Exception?) { Log.e("Vosk", "Error: ${exception?.message}") }
    override fun onTimeout() {}

    companion object {
        private const val GRAMMAR = "[\"start\", \"stop\", \"pause\", \"reset\", \"[unk]\"]"
    }
}