package com.example.timerapp.data.local

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first
import okio.Path

private val Context.dataStore by preferencesDataStore(name = "setting")

class SoundSettings(
    private val context: Context
) {
    private val SELECTED_SOUND = stringPreferencesKey("selected_sound")
    private val SELECTED_PATH = stringPreferencesKey("custom_path")

    private val VOICE_INDEX = intPreferencesKey("voice_countdown_index")

    private val IS_ENABLE_VOICE_ANNOUNCE = booleanPreferencesKey("IsEnableVoiceAnnoucment")
    private val IS_VOICE_ASSISTENT = booleanPreferencesKey("ISVoiceAssigntent")


    suspend fun saveSound(name: String, customPath: String?) {
        context.dataStore.edit { preferences ->

            preferences[SELECTED_SOUND] = name

            if (customPath != null) preferences[SELECTED_PATH] = customPath
            else preferences.remove(SELECTED_PATH)
        }
    }

    suspend fun getSavedSound(): Pair<String?, String?> {

        val preferences = context.dataStore.data.first()

        val name = preferences[SELECTED_SOUND]
        val path = preferences[SELECTED_PATH]

        return Pair(name, path)

    }

    suspend fun saveVoiceIndex(index: Int) {
        context.dataStore.edit { prefs ->
            prefs[VOICE_INDEX] = index
        }
    }

    suspend fun getVoiceIndex(): Int {
        return context.dataStore.data.first()[VOICE_INDEX] ?: 0   // 0 = Off
    }

    suspend fun savevoiceAnnoucment(isenable: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[IS_ENABLE_VOICE_ANNOUNCE] = isenable
        }
    }

    suspend fun getvoiceAnnoucment(): Boolean {

        return context.dataStore.data.first()[IS_ENABLE_VOICE_ANNOUNCE] ?: false
    }

    suspend fun saveVoiceAssistentbtn(isenable: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[IS_VOICE_ASSISTENT] = isenable
        }
    }

    suspend fun getVoiceAssistentbtn(): Boolean {

        return context.dataStore.data.first()[IS_VOICE_ASSISTENT] ?: false
    }

}