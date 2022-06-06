package com.example.codepam.adapter

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.codepam.R
import com.example.codepam.Room.MyDatabase
import com.example.codepam.activity.DetailMakananMinumanActivity
import com.example.codepam.helper.Helper
import com.example.codepam.model.DataPemesanan
import kotlin.collections.ArrayList

class AdapterDataPemesanan( var data:ArrayList<DataPemesanan>, var listener: Listeners): RecyclerView.Adapter<AdapterDataPemesanan.Holder>() {
    class Holder(view: View):RecyclerView.ViewHolder(view){
        val tvNama =  view.findViewById<TextView>(R.id.tv_nama)
        val tvPhone =  view.findViewById<TextView>(R.id.tv_phone)
        val tvNIM =  view.findViewById<TextView>(R.id.tv_nim)
        val tvProdi =  view.findViewById<TextView>(R.id.tv_prodi)
        val tvAngkatan =  view.findViewById<TextView>(R.id.tv_angkatan)
        val rd_datapemesanan =  view.findViewById<RadioButton>(R.id.rd_datapemesanan)
        val layout =  view.findViewById<CardView>(R.id.layout)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view: View = LayoutInflater.from(parent.context).
        inflate(R.layout.item_datapemesanan, parent, false)
        return Holder(view)
    }

    //menghitung banyak data
    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {

        val data = data[position]
        holder.rd_datapemesanan.isChecked = data.isSelected
        holder.tvNama.text= data.name
        holder.tvPhone.text= data.phone
        holder.tvNIM.text= data.nim
        holder.tvProdi.text= data.prodi
        holder.tvAngkatan.text= data.angkatan

        holder.rd_datapemesanan.setOnClickListener{
            data.isSelected = true
            listener.onClicked(data)
        }
        holder.layout.setOnClickListener{
            data.isSelected = true
            listener.onClicked(data)
        }

    }

    interface Listeners{
        fun onClicked(data:DataPemesanan)
    }
}