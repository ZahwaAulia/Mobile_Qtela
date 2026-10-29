package com.example.zwa_qtela.Home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.edit
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.zwa_qtela.R
import com.example.zwa_qtela.utils.ThemePreferences
import com.example.zwa_qtela.databinding.ActivityMainBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Mengatur jarak konten dari status bar dan navigation bar
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { view, insets ->
            val systemBars = insets.getInsets(
                WindowInsetsCompat.Type.systemBars()
            )

            view.setPadding(
                systemBars.left,
                systemBars.top,
                systemBars.right,
                systemBars.bottom
            )

            insets
        }

        // Mengatur Toolbar
        setSupportActionBar(binding.toolbar)
        supportActionBar?.title = "SHUTTLY"

        // Tombol WebView
        binding.btnWebView.setOnClickListener {
            val intent = Intent(this, WebViewActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onStart() {
        super.onStart()
        Log.e("MainActivity", "Main Activity terlihat di layar")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.e("MainActivity", "Main Activity dihapus dari stack")
    }

    // Menampilkan menu pada Toolbar
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    // Mengatur aksi menu Toolbar
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_search -> {
                Toast.makeText(
                    this,
                    "Search Clicked",
                    Toast.LENGTH_SHORT
                ).show()
                true
            }

            R.id.action_theme, R.id.action_settings -> {
                showThemeChoiceDialog()
                true
            }

            R.id.action_logout -> {
                val sharedPref = getSharedPreferences("user_pref", MODE_PRIVATE)
                sharedPref.edit { putBoolean("isLogin", false) }
                val intent = Intent(this, AuthActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
                finish()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun showThemeChoiceDialog() {
        val options = arrayOf("Ikuti Sistem", "Mode Terang (Light Mode)", "Mode Gelap (Dark Mode)")
        val checkedItem = when (ThemePreferences.getSavedThemeMode(this)) {
            AppCompatDelegate.MODE_NIGHT_NO -> 1
            AppCompatDelegate.MODE_NIGHT_YES -> 2
            else -> 0
        }

        MaterialAlertDialogBuilder(this)
            .setTitle(R.string.select_theme)
            .setSingleChoiceItems(options, checkedItem) { dialog, which ->
                val selectedMode = when (which) {
                    1 -> AppCompatDelegate.MODE_NIGHT_NO
                    2 -> AppCompatDelegate.MODE_NIGHT_YES
                    else -> AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
                }
                ThemePreferences.saveThemeMode(this, selectedMode)
                dialog.dismiss()
            }
            .setNegativeButton("Batal", null)
            .show()
    }
}