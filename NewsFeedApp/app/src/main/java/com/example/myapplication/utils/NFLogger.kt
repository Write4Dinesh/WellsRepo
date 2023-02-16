package com.example.myapplication.utils

import android.util.Log

object NFLogger {
    const val LOG_TAG = "NFLogger"
    fun log(message: String) {
        Log.d(LOG_TAG, message)
    }

    fun log(tag: String, message: String) {
        Log.d("${LOG_TAG}-${tag}", message)
    }

}