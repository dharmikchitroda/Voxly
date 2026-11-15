package com.example.timerapp.core.util

fun TimerFormatter(time: Int): String {

    val sec = time % 60
    val min = time / 60

    return "%02d:%02d".format(min, sec)

}