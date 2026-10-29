package com.example.zwa_qtela

import android.app.Application
import com.example.zwa_qtela.utils.ThemePreferences

class ZWAQtelaApp : Application() {
    override fun onCreate() {
        super.onCreate()
        ThemePreferences.applyTheme(this)
    }
}