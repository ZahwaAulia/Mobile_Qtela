package com.example.zwa_qtela.utils

import android.content.Context
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.edit

object ThemePreferences {
    private const val PREF_NAME = "theme_pref"
    private const val KEY_NIGHT_MODE = "night_mode"

    fun applyTheme(context: Context) {
        val mode = getSavedThemeMode(context)
        AppCompatDelegate.setDefaultNightMode(mode)
    }

    fun getSavedThemeMode(context: Context): Int {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return prefs.getInt(KEY_NIGHT_MODE, AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
    }

    fun saveThemeMode(context: Context, mode: Int) {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        prefs.edit { putInt(KEY_NIGHT_MODE, mode) }
        AppCompatDelegate.setDefaultNightMode(mode)
    }

    fun isDarkMode(context: Context): Boolean {
        val mode = getSavedThemeMode(context)
        return if (mode == AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM) {
            val nightModeFlags = context.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
            nightModeFlags == Configuration.UI_MODE_NIGHT_YES
        } else {
            mode == AppCompatDelegate.MODE_NIGHT_YES
        }
    }
}