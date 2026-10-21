package com.example.zwa_qtela.Booking

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.zwa_qtela.R
import android.widget.Toast


class BookingFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_booking, container, false)
    }
    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.btnSimpanBooking)
            .setOnClickListener {
                Toast.makeText(
                    requireContext(),
                    "Form booking siap diproses",
                    Toast.LENGTH_SHORT
                ).show()
            }
    }
}
