    package com.example.timerapp.util

    import android.content.Context
    import android.net.Uri
    import java.io.File

    class AudioFileStorage() {


        fun saver(context: Context, uri: Uri): File {

            val path = context.filesDir // /data/user/0/com.example.timerapp/files
            val destinationFile = File(path, "selected_music.mp3") // /data/user/0/com.example.timerapp/files/selected_music.mp3

            try {
                val resolver = context.contentResolver

                val inputStream = resolver.openInputStream(uri)

                if (inputStream == null) {
                    throw Exception("Unable to open selected file")
                }

    //        inputStream?.use { stream ->
    //            var byte = stream.read()
    //
    //            while (byte != -1) {
    //                byte = stream.read()
    //            }
    //
    //        }

                inputStream .use { input ->
                    destinationFile.outputStream().use { output ->
                        input.copyTo(output)
                    }
                }
            } catch (e: Exception) {
                throw e
            }

            return destinationFile
        }
    }