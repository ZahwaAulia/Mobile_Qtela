package com.example.zwa_qtela.Lapangan

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.zwa_qtela.databinding.ItemLapanganBinding

class LapanganAdapter(
    private val lapanganList: List<LapanganModel>,
    private val onItemClick: (LapanganModel) -> Unit
) : RecyclerView.Adapter<LapanganAdapter.LapanganViewHolder>() {

    inner class LapanganViewHolder(val binding: ItemLapanganBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LapanganViewHolder {
        val binding = ItemLapanganBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return LapanganViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LapanganViewHolder, position: Int) {
        val item = lapanganList[position]

        with(holder.binding) {
            tvLapanganName.text = item.name
            tvLapanganFacility.text = item.facility
            tvLapanganPrice.text = item.price

            // Menampilkan gambar dari drawable
            imgLapangan.setImageResource(item.imageUrl)

            btnBookingItem.setOnClickListener {
                onItemClick(item)
            }
        }
    }

    override fun getItemCount(): Int = lapanganList.size
}
