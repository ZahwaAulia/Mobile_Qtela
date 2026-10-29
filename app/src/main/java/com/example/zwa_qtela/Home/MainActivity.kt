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
import com.example.zwa_qtela.databinding.ActivityMainBinding
import com.example.zwa_qtela.utils.ThemePreferences
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Tampilkan status bar dan gunakan area konten normal
        window.clearFlags(
            android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        androidx.core.view.WindowCompat.setDecorFitsSystemWindows(
            window,
            true
        )

        window.statusBarColor = android.graphics.Color.parseColor("#FFF8EC")

        androidx.core.view.WindowInsetsControllerCompat(
            window,
            window.decorView
        ).isAppearanceLightStatusBars = true

        // Mengatur Toolbar
        setSupportActionBar(binding.toolbar)
        supportActionBar?.title = "SHUTTLY"

        // Tombol WebView
        binding.btnWebView.setOnClickListener {
            startActivity(
                Intent(this, WebViewActivity::class.java)
            )
        }
    }

    override fun onStart() {
        super.onStart()
        Log.d("MainActivity", "MainActivity terlihat di layar")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("MainActivity", "MainActivity dihancurkan")
    }

    // Menampilkan menu Toolbar
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

            R.id.action_theme,
            R.id.action_settings -> {
                showThemeChoiceDialog()
                true
            }

            R.id.action_logout -> {
                // Mengubah status login tanpa menghapus data registrasi
                getSharedPreferences(
                    "user_pref",
                    MODE_PRIVATE
                ).edit {
                    putBoolean("isLogin", false)
                    remove("usernameLogin")
                }

                // Kembali ke AuthActivity dan menghapus halaman sebelumnya
                val intent = Intent(
                    this,
                    AuthActivity::class.java
                ).apply {
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK or
                            Intent.FLAG_ACTIVITY_CLEAR_TASK
                }

                startActivity(intent)
                finish()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    // Dialog pengaturan tema
    private fun showThemeChoiceDialog() {

        val options = arrayOf(
            "Ikuti Sistem",
            "Mode Terang (Light Mode)",
            "Mode Gelap (Dark Mode)"
        )

        val checkedItem = when (
            ThemePreferences.getSavedThemeMode(this)
        ) {
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

                ThemePreferences.saveThemeMode(
                    this,
                    selectedMode
                )

                dialog.dismiss()
            }
            .setNegativeButton("Batal", null)
            .show()
    }
}