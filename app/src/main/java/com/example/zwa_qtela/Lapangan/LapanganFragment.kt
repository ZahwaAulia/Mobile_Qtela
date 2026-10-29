package com.example.zwa_qtela.Lapangan

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.zwa_qtela.Booking.BookingFragment
import com.example.zwa_qtela.R

class LapanganFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_lapangan, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btnBooking1 = view.findViewById<Button>(R.id.btnBookingLapangan1)
        val btnBooking2 = view.findViewById<Button>(R.id.btnBookingLapangan2)

        btnBooking1?.setOnClickListener {
            navigateToBooking("LAPANGAN 1")
        }

        btnBooking2?.setOnClickListener {
            navigateToBooking("LAPANGAN 2")
        }
    }

    private fun navigateToBooking(lapanganNama: String) {
        val bookingFragment = BookingFragment().apply {
            arguments = Bundle().apply {
                putString("lapangan_nama", lapanganNama)
            }
        }

        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, bookingFragment)
            .addToBackStack(null)
            .commit()
    }
}