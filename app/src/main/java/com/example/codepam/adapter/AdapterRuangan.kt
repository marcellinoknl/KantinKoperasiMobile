package com.example.codepam.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.codepam.R
import com.example.codepam.model.Ruangan

class AdapterRuangan (var data:ArrayList<Ruangan>): RecyclerView.Adapter<AdapterRuangan.Holder>() {
    class Holder(view: View): RecyclerView.ViewHolder(view){
        val tvNama =  view.findViewById<TextView>(R.id.tv_nama)
        val tvHarga =  view.findViewById<TextView>(R.id.tv_harga)
        val tvGambar =  view.findViewById<ImageView>(R.id.img_ruangan)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view: View = LayoutInflater.from(parent.context).
        inflate(R.layout.item_ruangan, parent, false)
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