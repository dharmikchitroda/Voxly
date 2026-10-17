# 🎙️ Voxly - Voice Assisted Smart Timer

Voxly is a modern, minimalist countdown timer application for Android built with **Jetpack Compose**, featuring offline hands-free voice commands, real-time voice announcements, and customizable audio alerts.

---

## ✨ Features

- 🎙️ **Hands-Free Offline Voice Control**: Control your timer completely hands-free using voice commands ("Start", "Stop", "Pause", "Reset") powered by the offline Vosk speech recognition engine.
- 🗣️ **Smart Voice Countdown (TTS)**: Spoken voice countdown (5s, 10s) and voice alerts (e.g. "Last 10 seconds") using Android Text-to-Speech.
- 🔔 **Custom Audio Alerts**: Built-in sound effects with support to pick and import any custom audio file from your device.
- ⏱️ **Retro Digital & Circular UI**: Beautiful circular progress timer with dynamic color shift and retro digital clock display.
- ⚡ **Quick Presets & Custom Timers**: 1-tap preset chips (30s, 1m, 5m) or set any custom duration with minutes and seconds.
- ⚙️ **Persistent Preferences**: Custom sounds, voice settings, and preferences are automatically saved using Jetpack DataStore.

---

## 🛠️ Tech Stack & Architecture

- **Language**: Kotlin
- **UI Framework**: Jetpack Compose & Material 3
- **Architecture**: MVVM (ViewModel, StateFlow, LiveData, Coroutines)
- **Speech-to-Text (STT)**: Vosk Offline Speech Recognition
- **Text-to-Speech (TTS)**: Android TextToSpeech Engine
- **Audio Playback**: Android SoundPool
- **Storage & Settings**: Jetpack Preferences DataStore & Internal Storage

---

## 🚀 Getting Started

1. **Clone the repository**:
   ```bash
   git clone https://github.com/dharmikchitroda/Voxly.git
   ```
2. **Open in Android Studio**:
   - Open Android Studio and select **Open**.
   - Navigate to the cloned folder and open it.
3. **Sync & Build**:
   - Let Gradle sync all dependencies.
   - Run the app on an Android device or emulator with microphone permission enabled.

---

## 📄 License
This project is open source and available under the [MIT License](LICENSE).
