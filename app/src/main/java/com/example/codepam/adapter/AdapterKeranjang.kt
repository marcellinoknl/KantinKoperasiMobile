package com.example.codepam.adapter

import android.app.Activity
import android.content.Intent
import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.codepam.R
import com.example.codepam.activity.DetailMakananMinumanActivity
import com.example.codepam.helper.Helper
import com.example.codepam.model.Makanan_Minuman
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import java.text.NumberFormat
import java.util.*
import kotlin.collections.ArrayList

class AdapterKeranjang(var activity: Activity, var data:ArrayList<Makanan_Minuman>): RecyclerView.Adapter<AdapterKeranjang.Holder>() {
    class Holder(view: View):RecyclerView.ViewHolder(view){
        val tvNama =  view.findViewById<TextView>(R.id.tv_nama)
        val tvHarga =  view.findViewById<TextView>(R.id.tv_harga)
        val tvGambar =  view.findViewById<ImageView>(R.id.img_makanminuman)
        val layout =  view.findViewById<CardView>(R.id.layout)
        val tambah =  view.findViewById<ImageView>(R.id.btn_tambah)
        val kurang =  view.findViewById<ImageView>(R.id.btn_kurang)
        val delete =  view.findViewById<ImageView>(R.id.btn_delete)
        val checkBox =  view.findViewById<CheckBox>(R.id.checkBox)
        val tvjumlah =  view.findViewById<TextView>(R.id.tv_jumlah)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view: View = LayoutInflater.from(parent.context).
        inflate(R.layout.item_keranjang, parent, false)
        return Holder(view)
    }

    //menghitung banyak data
    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.tvNama.text= data[position].nama_produk
        holder.tvHarga.text= Helper().gantiRupiah(data[position].harga)

        var jumlah = data[position].jumlah
        holder.tvjumlah.text = jumlah.toString()
        val image ="http://192.168.100.11/PAM-Kantin_Koperasi_Web/public/images/MakananMinuman/"+data[position].file_foto
        Picasso.get()
            .load(image)
            .placeholder(R.drawable.product)
            .error(R.drawable.product)
            .into(holder.tvGambar)

        holder.layout.setOnClickListener {
            val activityintent = Intent(activity, DetailMakananMinumanActivity::class.java)
            val str = Gson().toJson(data[position], Makanan_Minuman::class.java)
            activityintent.putExtra("extra", str)
            activity.startActivity(activityintent)
        }

    }
}