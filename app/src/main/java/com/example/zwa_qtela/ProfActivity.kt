package com.example.zwa_qtela

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.zwa_qtela.databinding.ActivityProfBinding
import com.example.zwa_qtela.databinding.ActivityProfileBinding
import com.example.zwa_qtela.pertemuan3.ThirdResultActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class ProfActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding =  ActivityProfBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val imageProf = findViewById<ImageView>(R.id.imageprof)
        imageProf.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }
            val imageBeranda = findViewById<ImageView>(R.id.imageBerandaa)
            imageBeranda.setOnClickListener {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }

        val sharedPref = getSharedPreferences("user_pref", MODE_PRIVATE)
        // Tombol Logout
        binding.btnLogout.setOnClickListener {
            MaterialAlertDialogBuilder(this)
                .setTitle("Konfirmasi")
                .setMessage("Apakah Anda yakin ingin logout?")
                .setPositiveButton("Ya") { dialog, _ ->

                    // Hapus SharedPreferences
                    val editor = sharedPref.edit()
                    editor.clear()
                    editor.apply()

                    dialog.dismiss()

                    // Kembali ke ThirdResultActivity
                    val intent = Intent(this, ThirdResultActivity::class.java)
                    startActivity(intent)
                    finish()
                }
                .setNegativeButton("Batal") { dialog, _ ->
                    dialog.dismiss()
                }
                .show()
        }
    }
}