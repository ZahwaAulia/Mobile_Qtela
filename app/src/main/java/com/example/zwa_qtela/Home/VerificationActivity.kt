package com.example.zwa_qtela.Home

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.zwa_qtela.R
import com.example.zwa_qtela.databinding.ActivityVerificationBinding

class VerificationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityVerificationBinding

    private val sharedPref by lazy {
        getSharedPreferences("user_pref", MODE_PRIVATE)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityVerificationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        tampilkanData()

        binding.btnSubmit.setOnClickListener {
            val nama = sharedPref.getString("reg_nama", "") ?: ""
            val email = sharedPref.getString("reg_email", "") ?: ""
            val tanggalLahir = sharedPref.getString("reg_tanggalLahir", "") ?: ""
            val jenisKelamin = sharedPref.getString("reg_jenisKelamin", "") ?: ""
            val username = sharedPref.getString("reg_username", "") ?: ""
            val password = sharedPref.getString("reg_password", "") ?: ""

            // Simpan akun baru ke SharedPreferences (komit secara sinkron)
            sharedPref.edit()
                .putString("nama", nama)
                .putString("email", email)
                .putString("tanggalLahir", tanggalLahir)
                .putString("jenisKelamin", jenisKelamin)
                .putString("username", username.ifBlank { "user" })
                .putString("password", password.ifBlank { "123" })
                .putBoolean("isLogin", false)
                .commit()

            // Buka SuccessActivity
            val intent = Intent(this, SuccessActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.btnKembali.setOnClickListener {
            finish()
        }
    }

    private fun tampilkanData() {
        val nama = sharedPref.getString("reg_nama", "-")
        val email = sharedPref.getString("reg_email", "-")
        val tanggalLahir = sharedPref.getString("reg_tanggalLahir", "-")
        val jenisKelamin = sharedPref.getString("reg_jenisKelamin", "-")
        val username = sharedPref.getString("reg_username", "-")
        val password = sharedPref.getString("reg_password", "") ?: ""

        val maskPassword = if (password.isNotEmpty()) "••••••••" else "-"

        binding.tvDataRegistrasi.text = """
            Nama: $nama
            Email: $email
            Tanggal Lahir: $tanggalLahir
            Jenis Kelamin: $jenisKelamin
            Username: $username
            Password: $maskPassword
        """.trimIndent()
    }
}