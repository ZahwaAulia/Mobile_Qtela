package com.example.zwa_qtela.Profile

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.example.zwa_qtela.Home.BerandaFragment
import com.example.zwa_qtela.R
import com.example.zwa_qtela.utils.ThemePreferences
import com.google.android.material.materialswitch.MaterialSwitch

class ProfileFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val toolbar = view.findViewById<Toolbar>(R.id.toolbar)

        toolbar.title = "Profil Developer"
        toolbar.setTitleTextColor(
            Color.WHITE
        )

        toolbar.setNavigationIcon(R.drawable.ic_new)

        toolbar.setNavigationOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, BerandaFragment())
                .commit()
        }

        val switchDarkMode = view.findViewById<MaterialSwitch>(R.id.switchDarkMode)
        if (switchDarkMode != null) {
            val context = requireContext()
            switchDarkMode.isChecked = ThemePreferences.isDarkMode(context)

            switchDarkMode.setOnCheckedChangeListener { _, isChecked ->
                val newMode = if (isChecked) {
                    AppCompatDelegate.MODE_NIGHT_YES
                } else {
                    AppCompatDelegate.MODE_NIGHT_NO
                }
                ThemePreferences.saveThemeMode(context, newMode)
            }
        }
    }
}