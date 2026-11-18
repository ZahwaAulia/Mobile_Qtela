package com.example.zwa_qtela.Onboarding

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.viewpager2.widget.ViewPager2
import com.example.zwa_qtela.Home.AuthActivity
import com.example.zwa_qtela.R
import com.example.zwa_qtela.databinding.ActivityOnboardingBinding

class OnboardingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOnboardingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityOnboardingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val fragments = listOf(
            Onboarding1Fragment(),
            Onboarding2Fragment(),
            Onboarding3Fragment()
        )

        val adapter = OnboardingAdapter(this, fragments)
        binding.onboardingViewPager.adapter = adapter
        binding.dotIndicator.attachTo(binding.onboardingViewPager)

        binding.onboardingViewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                if (position == fragments.size - 1) {
                    binding.btnLanjut.text = "Mulai Sekarang"
                } else {
                    binding.btnLanjut.text = "Lanjut"
                }
            }
        })

        binding.btnLanjut.setOnClickListener {
            val currentItem = binding.onboardingViewPager.currentItem
            if (currentItem < fragments.size - 1) {
                binding.onboardingViewPager.currentItem = currentItem + 1
            } else {
                navigateToAuth()
            }
        }

        binding.btnLewati.setOnClickListener {
            navigateToAuth()
        }
    }

    private fun navigateToAuth() {
        startActivity(Intent(this, AuthActivity::class.java))
        finish()
    }
}