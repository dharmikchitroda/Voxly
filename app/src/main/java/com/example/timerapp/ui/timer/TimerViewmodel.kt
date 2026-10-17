package com.example.timerapp.ui.timer

import android.app.Application
import android.util.Log
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.timerapp.R
import com.example.timerapp.data.local.SoundSettings
import com.example.timerapp.data.model.CompletionSoundPreset
import com.example.timerapp.data.model.TimerPreset
import com.example.timerapp.util.VoskManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class TimerViewmodel(app: Application) : AndroidViewModel(app) {
    // load datastore object
    private val settings = SoundSettings(app)

    // creat vosk object
    private val vosk = VoskManager(app)
    val voskReady = vosk.isReady
    val isListening = vosk.isListening
    var TimerJob: Job? = null
    private val _time = MutableStateFlow(30)
    val time: StateFlow<Int> = _time
    private val _Intialtime = MutableStateFlow(30)
    val Intialtime: StateFlow<Int> = _Intialtime
    private val _TimerRuning = MutableStateFlow(false)
    val TimerRuning: StateFlow<Boolean> = _TimerRuning
    private val _ChipsList = MutableStateFlow<List<TimerPreset>>(emptyList())
    val ChipsList: StateFlow<List<TimerPreset>> = _ChipsList
    var _soundOn = MutableStateFlow(true)
    val soundOn: StateFlow<Boolean> = _soundOn

    var _count = MutableLiveData(0)
    val count: LiveData<Int> = _count

    var _VoiceCountdownChipsList = MutableStateFlow(emptyList<String>())
    val VoiceCountdownChipsList: StateFlow<List<String>> = _VoiceCountdownChipsList

    private val _DefultSoundList = MutableStateFlow<List<CompletionSoundPreset>>(emptyList())
    val DefultSoundList: StateFlow<List<CompletionSoundPreset>> = _DefultSoundList
    private val _selectedIndex = MutableStateFlow<Int>(0)
    val selectedIndex: StateFlow<Int> = _selectedIndex

    private val _IsEnableVoiceAnnoucment = MutableStateFlow<Boolean>(false)
    val IsEnableVoiceAnnoucment: StateFlow<Boolean> = _IsEnableVoiceAnnoucment
    private val _IsEnableVoiceAssistentbtn = MutableStateFlow<Boolean>(false)
    val IsEnableVoiceAssistentbtn: StateFlow<Boolean> = _IsEnableVoiceAssistentbtn


    fun increment() {

        viewModelScope.launch(Dispatchers.IO) {

            repeat(10) {
                delay(1000)
                _count.value = (_count.value ?: 0) + 1
            }
        }
    }

    fun Start() {
        if (_time.value == 0) _time.value = Intialtime.value

        if (_TimerRuning.value) {
            TimerJob?.cancel()
            _TimerRuning.value = false
        } else {
            _TimerRuning.value = true

            TimerJob = viewModelScope.launch {
                while (_time.value > 0) {
                    delay(1000)
                    _time.value--
                }
                _TimerRuning.value = false
            }
        }
    }

    fun stop() {
        TimerJob?.cancel()
        _time.value = Intialtime.value
        _TimerRuning.value = false
    }

    fun TimerFormatter(time: Int): String {

        val sec = time % 60
        val min = time / 60

        return "%02d:%02d".format(min, sec)

    }

    // list data of chips ( presets )
    val listChipsdata: List<TimerPreset> = listOf(
        TimerPreset(30, "30 Sec"),
        TimerPreset(60, "1 Min"),
        TimerPreset(300, "5 Min"),
    )

    // list data of DropDownmenu Items
    val completionSounds_list: List<CompletionSoundPreset> = listOf(
        CompletionSoundPreset("Default-Sound 1", R.raw.sound1),
        CompletionSoundPreset("Default-Sound 2", R.raw.sound2),
        CompletionSoundPreset("Default-Sound 3", R.raw.sound3),
        CompletionSoundPreset("Default-Sound 4", R.raw.sound4)
    )

    // voice countdown chips list
    val options = listOf("Off", "5 sec", "10 sec")

    // this pass for ALWAYS first defult set
    private val _DefultSoundIntial = MutableStateFlow(completionSounds_list.first())
    val DefultSoundIntial: StateFlow<CompletionSoundPreset> = _DefultSoundIntial

    //read file from the datastore
    init {

        _ChipsList.value = listChipsdata
        _DefultSoundList.value = completionSounds_list

        viewModelScope.launch {
            val (savedName, savedPath) = settings.getSavedSound()


            if (savedPath != null) {

                val customSound = CompletionSoundPreset(
                    name = savedName ?: "My Music",
                    music = 0,
                    filePath = savedPath
                )

                _DefultSoundList.value =
                    completionSounds_list + customSound

                _DefultSoundIntial.value = customSound
            } else {

                val selectedSound = completionSounds_list.find {
                    it.name == savedName
                } ?: completionSounds_list.first()

                _DefultSoundIntial.value = selectedSound
            }

            _selectedIndex.value = settings.getVoiceIndex()
            _IsEnableVoiceAnnoucment.value = settings.getvoiceAnnoucment()
            _IsEnableVoiceAssistentbtn.value = settings.getVoiceAssistentbtn()
        }
        // voice countdown chips list
        _VoiceCountdownChipsList.value = options
    }

    fun setime(time: Int) {

        TimerJob?.cancel()
        _TimerRuning.value = false

        _time.value = time
        _Intialtime.value = time

    }

    fun progressinf(): Float {
        return (_Intialtime.value - _time.value).toFloat() / _Intialtime.value
    }

    fun SetCustomTime(mins: Int, sec: Int) {

        val total = mins * 60 + sec
        if (total == 0) return
        setime(total)

    }

    fun SetsoundOnOff() {
        _soundOn.value = !_soundOn.value
    }

    fun ChangeDefaultDropDownMenu(DefaultMusic: CompletionSoundPreset) {
        _DefultSoundIntial.value = DefaultMusic

        viewModelScope.launch {
            settings.saveSound(DefaultMusic.name, DefaultMusic.filePath)
        }
    }

    fun AddCustomSound(filePath: String) {

        val customSound = CompletionSoundPreset(
            name = "My Music",
            music = 0,
            filePath = filePath
        )

        _DefultSoundList.value = _DefultSoundList.value.filter { it.filePath == null } + customSound
        _DefultSoundIntial.value = customSound

        viewModelScope.launch {
            settings.saveSound(customSound.name, customSound.filePath)
        }
    }

    fun AddCustomSoundAccordingIndex(cuurentInd: Int) {

        _selectedIndex.value = cuurentInd

        viewModelScope.launch {
            settings.saveVoiceIndex(cuurentInd)
        }
    }

    fun SetEnableVoiceAnnoucment(currentMode: Boolean) {

        _IsEnableVoiceAnnoucment.value = currentMode
        viewModelScope.launch {
            settings.savevoiceAnnoucment(currentMode)
        }
    }

    fun SetEnableVoiceAssistent(currentMode: Boolean) {

        _IsEnableVoiceAssistentbtn.value = currentMode
        viewModelScope.launch {
            settings.saveVoiceAssistentbtn(currentMode)
        }
    }

    // intealize for vosk
    init {
        vosk.init()
        viewModelScope.launch {
            viewModelScope.launch {
                vosk.commands.collect { handleVoiceCommand(it) }
            }
        }
    }

    fun startVoice() = vosk.startListening()
    fun stopVoice() = vosk.stopListening()
    private fun handleVoiceCommand(text: String) {
        Log.d("Vosk", text)

        val words = text.split(" ")          // word match, not "in text" ("restart" contains "start")
        when {
            "start" in words && !_TimerRuning.value -> Start()
            ("stop" in words || "pause" in words) && _TimerRuning.value -> Start() // Start() toggles = pause
            "reset" in words -> stop()       // your stop() = reset
        }
    }

    override fun onCleared() {
        vosk.stopListening()
        super.onCleared()
    }

}