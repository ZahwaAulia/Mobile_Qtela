package com.example.zwa_qtela.Riwayat

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.example.zwa_qtela.databinding.ItemRiwayatBinding
import com.google.android.material.snackbar.Snackbar

class RiwayatCustomAdapter(
    context: Context,
    private val listRiwayat: List<RiwayatModel>
) : ArrayAdapter<RiwayatModel>(context, 0, listRiwayat) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val binding: ItemRiwayatBinding = if (convertView == null) {
            ItemRiwayatBinding.inflate(LayoutInflater.from(context), parent, false)
        } else {
            ItemRiwayatBinding.bind(convertView)
        }

        val data = listRiwayat[position]

        // Menampilkan gambar dari drawable
        binding.avatarImg.setImageResource(data.avatarUrl)

        binding.textSender.text = data.lapanganName
        binding.textMessage.text = data.bookingDetail

        binding.root.setOnClickListener {
            Snackbar.make(
                parent,
                "Riwayat dipilih: ${data.lapanganName} (${data.bookingDetail})",
                Snackbar.LENGTH_SHORT
            ).show()
        }

        return binding.root
    }
}
