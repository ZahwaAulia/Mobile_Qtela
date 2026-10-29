package com.example.zwa_qtela.Home

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.zwa_qtela.R
import com.example.zwa_qtela.databinding.ActivityAuthBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class AuthActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAuthBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Akses "user_pref"
        val sharedPref = getSharedPreferences("user_pref", MODE_PRIVATE)

        // Jika sudah login, langsung ke BaseActivity (yang berisi BerandaFragment)
        val isLogin = sharedPref.getBoolean("isLogin", false)
        if (isLogin) {
            val i = Intent(this, BaseActivity::class.java)
            startActivity(i)
            finish()
        }

        binding.btnLogin.setOnClickListener {
            val username = binding.edUsername.text.toString().trim()
            val password = binding.edPassword.text.toString()

            val usernameTersimpan = sharedPref.getString("username", "") ?: ""
            val passwordTersimpan = sharedPref.getString("password", "") ?: ""

            if (username.isBlank() || password.isBlank()) {
                MaterialAlertDialogBuilder(this)
                    .setTitle("Login Gagal")
                    .setMessage("Username dan password wajib diisi!")
                    .setPositiveButton("OK", null)
                    .show()
                return@setOnClickListener
            }

            val loginPraktikum = username == password
            val loginSharedPreferences =
                username == usernameTersimpan &&
                        password == passwordTersimpan &&
                        usernameTersimpan.isNotBlank()

            if (loginPraktikum || loginSharedPreferences) {
                sharedPref.edit()
                    .putBoolean("isLogin", true)
                    .putString("usernameLogin", username)
                    .apply()

                startActivity(Intent(this, BaseActivity::class.java))
                finish()
            } else {
                MaterialAlertDialogBuilder(this)
                    .setTitle("Login Gagal")
                    .setMessage("Username atau password salah!")
                    .setPositiveButton("OK", null)
                    .show()
            }
        }

        binding.btnRegistrasi.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }
}