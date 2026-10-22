package com.example.zwa_qtela.Home

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.zwa_qtela.R
import com.example.zwa_qtela.databinding.ActivityVerificationBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder

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
                val tanggalLahir =
                    sharedPref.getString("reg_tanggalLahir", "") ?: ""
                val jenisKelamin =
                    sharedPref.getString("reg_jenisKelamin", "") ?: ""
                val username =
                    sharedPref.getString("reg_username", "") ?: ""
                val password =
                    sharedPref.getString("reg_password", "") ?: ""
                val confirmPassword =
                    sharedPref.getString("reg_confirmPassword", "") ?: ""

                val semuaTerisi = listOf(
                    nama,
                    email,
                    tanggalLahir,
                    jenisKelamin,
                    username,
                    password,
                    confirmPassword
                ).all { it.isNotBlank() }

                if (!semuaTerisi) {
                    tampilkanError(
                        "Semua data registrasi wajib diisi. Tekan Kembali untuk memperbaiki data."
                    )
                    return@setOnClickListener
                }

                if (!email.contains("@") || !email.substringAfter("@").contains(".")) {
                    tampilkanError(
                        "Format email tidak valid. Tekan Kembali untuk memperbaiki email."
                    )
                    return@setOnClickListener
                }

                if (password != confirmPassword) {
                    tampilkanError(
                        "Password dan Confirm Password tidak sama. Tekan Kembali untuk memperbaikinya."
                    )
                    return@setOnClickListener
                }

                // Pindahkan data registrasi ke data akun yang digunakan saat login
                sharedPref.edit()
                    .putString("nama", nama)
                    .putString("email", email)
                    .putString("tanggalLahir", tanggalLahir)
                    .putString("jenisKelamin", jenisKelamin)
                    .putString("username", username)
                    .putString("password", password)
                    .putBoolean("isLogin", false)
                    .apply()

                startActivity(
                    Intent(this, SuccessActivity::class.java)
                )
                finish()
            }

            binding.btnKembali.setOnClickListener {
                finish()
            }
        }

        private fun tampilkanData() {
            binding.tvDataRegistrasi.text = """
            Nama: ${sharedPref.getString("reg_nama", "")}
             
            Email: ${sharedPref.getString("reg_email", "")}
            
            Tanggal Lahir: ${sharedPref.getString("reg_tanggalLahir", "")}
            
            Jenis Kelamin: ${sharedPref.getString("reg_jenisKelamin", "")}
            
            Username: ${sharedPref.getString("reg_username", "")}
            
            Password: ${if ((sharedPref.getString("reg_password", "") ?: "").isNotEmpty()) "••••••••" else ""}
            
            Confirm Password: ${if ((sharedPref.getString("reg_confirmPassword", "") ?: "").isNotEmpty()) "••••••••" else ""}
        """.trimIndent()
        }

        private fun tampilkanError(pesan: String) {
            MaterialAlertDialogBuilder(this)
                .setTitle("Validasi Gagal")
                .setMessage(pesan)
                .setPositiveButton("OK", null)
                .show()
        }
    }
