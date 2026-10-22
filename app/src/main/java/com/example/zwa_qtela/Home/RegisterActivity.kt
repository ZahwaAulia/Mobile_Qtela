package com.example.zwa_qtela.Home

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.zwa_qtela.R
import  java.util.Calendar
import com.example.zwa_qtela.databinding.ActivityRegisterBinding
import kotlin.jvm.java

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private val sharedPref by lazy { getSharedPreferences("user_pref", MODE_PRIVATE)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding.edEmail.setText(sharedPref.getString("reg_email", ""))
        binding.edTanggalLahir.setText(
            sharedPref.getString("reg_tanggalLahir", "")
        )
        binding.edUsername.setText(
            sharedPref.getString("reg_username", "")
        )
        binding.edPassword.setText(
            sharedPref.getString("reg_password", "")
        )
        binding.edConfirmPassword.setText(
            sharedPref.getString("reg_confirmPassword", "")
        )

        when (sharedPref.getString("reg_jenisKelamin", "")) {
            "Laki-laki" -> binding.rbLakiLaki.isChecked = true
            "Perempuan" -> binding.rbPerempuan.isChecked = true
        }

        binding.edTanggalLahir.setOnClickListener {
            val calendar = Calendar.getInstance()

            val tanggalTersimpan = sharedPref.getString(
                "reg_tanggalLahir", ""
            ) ?: ""

            if (tanggalTersimpan.isNotEmpty()) {
                val bagian = tanggalTersimpan.split("/")
                if (bagian.size == 3) {
                    calendar.set(
                        bagian[2].toInt(),
                        bagian[1].toInt() - 1,
                        bagian[0].toInt()
                    )
                }
            }

            DatePickerDialog(
                this,
                { _, year, month, day ->
                    binding.edTanggalLahir.setText(
                        "%02d/%02d/%04d".format(
                            day, month + 1, year
                        )
                    )
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }

        binding.btnSelanjutnya.setOnClickListener {
            val nama = binding.edNama.text.toString().trim()
            val email = binding.edEmail.text.toString().trim()
            val tanggalLahir =
                binding.edTanggalLahir.text.toString().trim()
            val username = binding.edUsername.text.toString().trim()
            val password = binding.edPassword.text.toString()
            val confirmPassword =
                binding.edConfirmPassword.text.toString()

            val jenisKelamin = when (binding.rgJenisKelamin.checkedRadioButtonId) {
                binding.rbLakiLaki.id -> "Laki-laki"
                binding.rbPerempuan.id -> "Perempuan"
                else -> ""
            }

            // Semua data disimpan untuk diperiksa di halaman verifikasi
            sharedPref.edit()
                .putString("reg_nama", nama)
                .putString("reg_email", email)
                .putString("reg_tanggalLahir", tanggalLahir)
                .putString("reg_jenisKelamin", jenisKelamin)
                .putString("reg_username", username)
                .putString("reg_password", password)
                .putString("reg_confirmPassword", confirmPassword)
                .apply()

            startActivity(
                Intent(this, VerificationActivity::class.java)
            )
        }
    }
}
