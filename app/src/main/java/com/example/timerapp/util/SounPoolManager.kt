package com.example.timerapp.util

import android.content.Context
import android.media.SoundPool
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.net.Uri
import com.example.timerapp.data.model.CompletionSoundPreset

class SounPoolManager(
    context: Context,
    private val preset: CompletionSoundPreset
) {
    private val appContext = context.applicationContext

    private var soundPool: SoundPool? = null
    private var mediaPlayer: MediaPlayer? = null
    private var soundId = 0
    private var isLoaded = false

    private val attrs = AudioAttributes.Builder()
        .setUsage(AudioAttributes.USAGE_MEDIA)
        .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
        .build()

    init {
        val path = preset.filePath
        if (path == null) setupSoundPool() else setupMediaPlayer(path)
    }

    private fun setupSoundPool() {
        soundPool = SoundPool.Builder()
            .setMaxStreams(1)
            .setAudioAttributes(attrs)
            .build()
            .also { pool ->
                pool.setOnLoadCompleteListener { _, _, status ->
                    isLoaded = (status == 0)
                }
                soundId = pool.load(appContext, preset.music, 1)
            }
        }

    private fun setupMediaPlayer(path: String) {
        try {
            mediaPlayer = MediaPlayer().apply {
                setAudioAttributes(attrs)
                if (path.startsWith("content://")) {
                    setDataSource(appContext, Uri.parse(path))
                } else {
                    setDataSource(path)
                }
                setOnPreparedListener { isLoaded = true }

                setOnErrorListener { _, _, _ ->
                    isLoaded = false
                    true // error handled
                }

                prepareAsync()

            }
        } catch (e: Exception) {
            isLoaded = false // file missing or invalid
        }
    }

    fun play() {
        if (!isLoaded) return

        soundPool?.play(soundId, 1f, 1f, 1, 0, 1f)

        mediaPlayer?.let {
            if (it.isPlaying) it.seekTo(0) else it.start()
        }
    }

    fun release() {
        soundPool?.release()
        soundPool = null

        mediaPlayer?.release()
        mediaPlayer = null
        isLoaded = false
    }
}