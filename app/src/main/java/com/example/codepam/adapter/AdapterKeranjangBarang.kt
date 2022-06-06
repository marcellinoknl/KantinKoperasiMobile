package com.example.codepam.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.codepam.R
import com.example.codepam.Room.MyDatabaseBarang
import com.example.codepam.helper.Helper
import com.example.codepam.model.Barang
import com.example.codepam.util.Config
import com.squareup.picasso.Picasso
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class AdapterKeranjangBarang(var activity: Activity, var data:ArrayList<Barang>, var listener:Listeners): RecyclerView.Adapter<AdapterKeranjangBarang.Holder>() {
    class Holder(view: View): RecyclerView.ViewHolder(view){
        val tvNama =  view.findViewById<TextView>(R.id.tv_nama)
        val tvHarga =  view.findViewById<TextView>(R.id.tv_harga)
        val tvGambar =  view.findViewById<ImageView>(R.id.img_barang)
        val layout =  view.findViewById<CardView>(R.id.layout)
        val tambah =  view.findViewById<ImageView>(R.id.btn_tambah)
        val kurang =  view.findViewById<ImageView>(R.id.btn_kurang)
        val delete =  view.findViewById<ImageView>(R.id.btn_delete)
        val checkBox =  view.findViewById<CheckBox>(R.id.checkBox)
        val tvjumlah =  view.findViewById<TextView>(R.id.tv_jumlah)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view: View = LayoutInflater.from(parent.context).
        inflate(R.layout.item_keranjang_barang, parent, false)
        return Holder(view)
    }

    //menghitung banyak data
    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {

        val barang = data[position]
        val harga = barang.harga
        holder.tvNama.text= barang.nama_barang
        holder.tvHarga.text= Helper().gantiRupiah(harga * barang.jumlah)

        var jumlah = data[position].jumlah
        holder.tvjumlah.text = jumlah.toString()
        holder.checkBox.isChecked= barang.selected
        holder.checkBox.setOnCheckedChangeListener{ buttonView, isChecked ->
            barang.selected = isChecked
            update(barang)

        }
        val image = Config.barangUrl+data[position].file_foto
        Picasso.get()
            .load(image)
            .placeholder(R.drawable.product)
            .error(R.drawable.product)
            .into(holder.tvGambar)

        holder.tambah.setOnClickListener{

            jumlah++
            barang.jumlah = jumlah
            update(barang)
            holder.tvjumlah.text = jumlah.toString()
            holder.tvHarga.text = Helper().gantiRupiah(harga*jumlah)
        }
        holder.kurang.setOnClickListener{
            if(jumlah <= 1) return@setOnClickListener
            jumlah--

            barang.jumlah = jumlah
            update(barang)
            holder.tvjumlah.text = jumlah.toString()
            holder.tvHarga.text = Helper().gantiRupiah(harga*jumlah)

        }

        holder.delete.setOnClickListener{
            delete(barang)
            listener.onDelete(position)
        }
    }

    interface Listeners{
        fun onUpdate()
        fun onDelete(position: Int)
    }
    fun update(data: Barang){
        val myDb = MyDatabaseBarang.getInstance(activity)
        CompositeDisposable().add(Observable.fromCallable { myDb!!.daoKeranjangbarang().update(data) }
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                listener.onUpdate()
            })
    }

    fun delete(data: Barang){
        val myDb = MyDatabaseBarang.getInstance(activity)
        CompositeDisposable().add(Observable.fromCallable { myDb!!.daoKeranjangbarang().delete(data) }
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {

            })
    }
}