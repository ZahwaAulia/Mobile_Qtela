package com.example.zwa_qtela.Home

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.zwa_qtela.databinding.ActivityBaseBinding
import androidx.fragment.app.Fragment
import com.example.zwa_qtela.Booking.BookingFragment
import com.example.zwa_qtela.Lapangan.LapanganFragment
import com.example.zwa_qtela.Profile.ProfileFragment
import com.example.zwa_qtela.R
import com.example.zwa_qtela.Riwayat.RiwayatFragment

class BaseActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBaseBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityBaseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            replaceFragment(BerandaFragment())
        }

        binding.bottomNavView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_beranda -> {
                    replaceFragment(BerandaFragment())
                    true
                }

                R.id.nav_lapangan -> {
                    replaceFragment(LapanganFragment())
                    true
                }

                R.id.nav_booking -> {
                    replaceFragment(BookingFragment())
                    true
                }

                R.id.nav_riwayat -> {
                    replaceFragment(RiwayatFragment())
                    true
                }

                R.id.nav_profile -> {
                    replaceFragment(ProfileFragment())
                    true
                }

                else -> false
            }
        }
    }
    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }
}