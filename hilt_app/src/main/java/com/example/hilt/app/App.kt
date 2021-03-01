package com.example.hilt.app

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 *
 * @author zhangquan
 */
@HiltAndroidApp
class App : Application() {
    override fun onCreate() {
        super.onCreate()
    }
}