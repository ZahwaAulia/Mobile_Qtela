
package com.example.zwa_qtela.Home

import android.graphics.Color
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.fragment.app.Fragment
import com.example.zwa_qtela.Booking.BookingFragment
import com.example.zwa_qtela.Lapangan.LapanganFragment
import com.example.zwa_qtela.Profile.ProfileFragment
import com.example.zwa_qtela.R
import com.example.zwa_qtela.Riwayat.RiwayatFragment
import com.example.zwa_qtela.databinding.ActivityBaseBinding

class BaseActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBaseBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityBaseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Pastikan status bar Android tidak disembunyikan
        window.clearFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        // Gunakan tampilan standar, bukan edge-to-edge
        WindowCompat.setDecorFitsSystemWindows(window, true)

        // Status bar putih dengan ikon gelap
        window.statusBarColor = Color.WHITE
        WindowCompat.getInsetsController(
            window,
            window.decorView
        ).isAppearanceLightStatusBars = true

        // Tampilkan fragment awal
        if (savedInstanceState == null) {
            replaceFragment(BerandaFragment())
        }

        // Navigasi bawah
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
