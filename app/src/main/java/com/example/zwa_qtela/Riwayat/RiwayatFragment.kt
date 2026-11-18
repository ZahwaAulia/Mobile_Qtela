package com.example.zwa_qtela.Riwayat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.fragment.app.Fragment
import com.example.zwa_qtela.R

class RiwayatFragment : Fragment() {

    private val dataRiwayatList = listOf(
        RiwayatModel(
            "LAPANGAN 1",
            "2026-07-08 • 16:00 - 17:00 (Selesai)",
            R.drawable.lapangan1
        ),
        RiwayatModel(
            "LAPANGAN 2",
            "2026-07-10 • 19:00 - 20:00 (Menunggu)",
            R.drawable.lapangan2
        )
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_riwayat, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val listView = view.findViewById<ListView>(R.id.listViewRiwayat)
        val adapter = RiwayatCustomAdapter(requireContext(), dataRiwayatList)
        listView?.adapter = adapter
    }
}