package com.example.zwa_qtela.pertemuan3

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.zwa_qtela.FifthActivity
import com.example.zwa_qtela.R
import com.example.zwa_qtela.databinding.ActivityMainBinding
import com.example.zwa_qtela.databinding.ActivityThirdResultBinding
import com.example.zwa_qtela.sign_in

class ThirdResultActivity : AppCompatActivity() {
    private lateinit var binding: ActivityThirdResultBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityThirdResultBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding.btntofifth.setOnClickListener {
            val intent = Intent(this@ThirdResultActivity, sign_in::class.java)
            startActivity(intent)
        }
    }
}