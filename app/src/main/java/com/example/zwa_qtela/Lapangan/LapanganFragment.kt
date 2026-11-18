package com.example.zwa_qtela.Lapangan

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.zwa_qtela.Booking.BookingFragment
import com.example.zwa_qtela.R

class LapanganFragment : Fragment() {


    private val listLapangan = listOf(
        LapanganModel(
            "LAPANGAN 1",
            "Rp35.000 / jam",
            R.drawable.lapangan1,
            "Lantai badminton • Lampu LED"
        ),
        LapanganModel(
            "LAPANGAN 2",
            "Rp35.000 / jam",
            R.drawable.lapangan2,
            "Lantai badminton • Area parkir"
        )
    )


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_lapangan, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val rvLapangan = view.findViewById<RecyclerView>(R.id.rvLapangan)

        val adapter = LapanganAdapter(listLapangan) { selectedItem ->
            navigateToBooking(selectedItem.name)
        }

        rvLapangan?.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            this.adapter = adapter
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