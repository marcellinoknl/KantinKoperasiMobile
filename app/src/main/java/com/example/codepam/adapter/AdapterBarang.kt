package com.example.codepam.adapter

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.codepam.R
import com.example.codepam.activity.DetailBarangActivity
import com.example.codepam.helper.Helper
import com.example.codepam.model.Barang
import com.example.codepam.util.Config
import com.google.gson.Gson
import com.squareup.picasso.Picasso

class AdapterBarang(var activity: Activity, var data:ArrayList<Barang>): RecyclerView.Adapter<AdapterBarang.Holder>() {
    class Holder(view: View):RecyclerView.ViewHolder(view){
        val tvNama =  view.findViewById<TextView>(R.id.tv_nama)
        val tvHarga =  view.findViewById<TextView>(R.id.tv_harga)
        val tvGambar =  view.findViewById<ImageView>(R.id.img_barang)
        val layout =  view.findViewById<CardView>(R.id.layout)

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
        holder.tvNama.text= data[position].nama_barang
        holder.tvHarga.text= Helper().gantiRupiah(data[position].harga)

        val image = Config.makananminumanUrl+data[position].file_foto
        Picasso.get()
            .load(image)
            .placeholder(R.drawable.product)
            .error(R.drawable.product)
            .into(holder.tvGambar)

        holder.layout.setOnClickListener {
            val activityintent = Intent(activity, DetailBarangActivity::class.java)
            val str = Gson().toJson(data[position], Barang::class.java)
            activityintent.putExtra("extra", str)
            activity.startActivity(activityintent)
        }

    }
}