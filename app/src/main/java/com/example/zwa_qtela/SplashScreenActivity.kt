package com.example.zwa_qtela

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.zwa_qtela.pertemuan3.ThirdResultActivity
import kotlinx.coroutines.launch
import kotlinx.coroutines.delay
class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val sharedPref = getSharedPreferences("user_pref", MODE_PRIVATE)
        val isLogin=sharedPref.getBoolean("isLogin", false)
        if (isLogin){
            Intent(this@SplashScreenActivity, MainActivity::class.java)
        }

        setContentView(R.layout.activity_splash_screen)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        lifecycleScope.launch {
            delay(2000) //simulasi pengambilan data selama 2 detik

            val sharedPref = getSharedPreferences("user_pref", MODE_PRIVATE)
            val isLogin=sharedPref.getBoolean("isLogin", false)
            val intent = if(isLogin){
                Intent(this@SplashScreenActivity, ThirdResultActivity::class.java)
            }else{
                Intent(this@SplashScreenActivity, AuthActivity::class.java)
            }
            startActivity(intent)
            finish()
        }
    }
}