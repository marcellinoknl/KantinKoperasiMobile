package com.example.codepam.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.codepam.R
import com.example.codepam.model.Barang

class AdapterBarang (var data:ArrayList<Barang>): RecyclerView.Adapter<AdapterBarang.Holder>() {
    class Holder(view: View): RecyclerView.ViewHolder(view){
        val tvNama =  view.findViewById<TextView>(R.id.tv_nama)
        val tvHarga =  view.findViewById<TextView>(R.id.tv_harga)
        val tvGambar =  view.findViewById<ImageView>(R.id.img_barang)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view: View = LayoutInflater.from(parent.context).
        inflate(R.layout.item_barang, parent, false)
        return Holder(view)
    }

    //menghitung banyak data
    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.tvNama.text= data[position].nama
        holder.tvHarga.text= data[position].harga
        holder.tvGambar.setImageResource(data[position].gambar)

    }
}