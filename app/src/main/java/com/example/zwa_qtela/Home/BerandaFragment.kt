
package com.example.zwa_qtela.Home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.example.zwa_qtela.R

class BerandaFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(
            R.layout.fragment_beranda,
            container,
            false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Mengatur jarak konten dari status bar dan navigation bar
        ViewCompat.setOnApplyWindowInsetsListener(view) { currentView, insets ->
            val systemBars = insets.getInsets(
                WindowInsetsCompat.Type.systemBars()
            )

            currentView.setPadding(
                systemBars.left,
                systemBars.top,
                systemBars.right,
                systemBars.bottom
            )

            insets
        }

        // Mengatur Toolbar
        val toolbar = view.findViewById<Toolbar>(R.id.toolbar)

        (requireActivity() as? AppCompatActivity)?.let { activity ->
            activity.setSupportActionBar(toolbar)
            activity.supportActionBar?.title = "SHUTTLY"
        }

        // Tombol WebView
        val btnWebView = view.findViewById<View>(R.id.btnWebView)

        btnWebView.setOnClickListener {
            val intent = Intent(
                requireContext(),
                WebViewActivity::class.java
            )
            startActivity(intent)
        }
    }

    // Menampilkan menu pada Toolbar
    override fun onCreateOptionsMenu(
        menu: Menu,
        inflater: android.view.MenuInflater
    ) {
        inflater.inflate(R.menu.main_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    // Mengatur aksi menu Toolbar

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {

            R.id.action_search -> {
                Toast.makeText(
                    requireContext(),
                    "Search Clicked",
                    Toast.LENGTH_SHORT
                ).show()
                true
            }

            R.id.action_settings -> {
                Toast.makeText(
                    requireContext(),
                    "Settings Clicked",
                    Toast.LENGTH_SHORT
                ).show()
                true
            }

            R.id.action_logout -> {
                val sharedPreferences = requireActivity()
                    .getSharedPreferences("user_pref", android.content.Context.MODE_PRIVATE)

                sharedPreferences.edit()
                    .clear()
                    .apply()

                val intent = Intent(
                    requireContext(),
                    AuthActivity::class.java
                )
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or
                        Intent.FLAG_ACTIVITY_CLEAR_TASK

                startActivity(intent)
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }


    override fun onStart() {
        super.onStart()
        Log.e("BerandaFragment", "Beranda terlihat di layar")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.e("BerandaFragment", "Tampilan Beranda dihancurkan")
    }
}
