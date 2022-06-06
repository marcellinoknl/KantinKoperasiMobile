package com.example.codepam.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.codepam.R
import com.example.codepam.Room.MyDatabase
import com.example.codepam.helper.Helper
import com.example.codepam.helper.SharedPref
import com.example.codepam.model.TransaksiMakananMinuman
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_detailkeranjangmakananminuman.*
import kotlinx.android.synthetic.main.toolbar.*

class DetailKeranjangMakananMinuman : AppCompatActivity() {

    lateinit var btn_tambahdatapemesanan: Button
    lateinit var btn_bayar: Button
    lateinit var myDB:MyDatabase
    var totalHarga = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detailkeranjangmakananminuman)
        btn_tambahdatapemesanan = findViewById(R.id.btn_tambahdatapemesanan)
        btn_bayar = findViewById(R.id.btn_bayar)
        Helper().setToolbar(this,toolbar,"Detail Keranjang")
        myDB = MyDatabase.getInstance(this)!!
        totalHarga = Integer.valueOf(intent.getStringExtra("extra")!!)
        mainButton()
    }


    private fun mainButton(){
        btn_tambahdatapemesanan.setOnClickListener {
            startActivity(Intent(this,ListDataPemesanan::class.java))
        }
        btn_bayar.setOnClickListener{
            bayar()
        }

        }
    private fun bayar(){
        val user = SharedPref(this).getUser()!!

        val listMakananMinuman = myDB.daoKeranjang().getAll() as ArrayList
        val datapemesanan =myDB.daoDataPemesanan().getByStatus(true)!!
        var totalItem = 0
        var totalHarga = 0
        val produks = ArrayList<TransaksiMakananMinuman.Item>()
        for (p in listMakananMinuman){
            if (p.selected){
                totalItem += p.jumlah
                totalHarga += (p.jumlah+p.harga)

                val produk = TransaksiMakananMinuman.Item()
                produk.produk_id = p.produk_id.toString()
                produk.total_item = p.jumlah.toString()
                produk.total_harga = (p.jumlah+p.harga).toString()
                produk.catatan ="Catatan Baru"

                produks.add(produk)
            }
        }
        val transaksi = TransaksiMakananMinuman()
        transaksi.user_id = ""+user!!.id
        transaksi.total_item = ""+totalItem
        transaksi.total_harga = ""+totalHarga
        transaksi.name = datapemesanan.name
        transaksi.phone = datapemesanan.phone
        transaksi.total_transfer = (totalHarga*totalItem).toString()
        transaksi.produks = produks

        val json = Gson().toJson(transaksi,TransaksiMakananMinuman::class.java)
        Log.d("Respon:","json:"+json)
        val intent = Intent(this,PembayaranActivity::class.java)
        intent.putExtra("extra",json)
        startActivity(intent)

    }

    @SuppressLint("SetTextI18n")
    private fun checkDataPemesanan(){
        val myDb = MyDatabase.getInstance(this)!!
        if(myDb.daoDataPemesanan().getByStatus(true)!= null){
            div_datapemesanan.visibility = View.VISIBLE
            div_kosong.visibility = View.GONE
            val datapemesanan =myDb.daoDataPemesanan().getByStatus(true)!!
            tv_nama.text = datapemesanan.name
            tv_phone.text= datapemesanan.phone
            tv_nim.text= datapemesanan.nim
            tv_prodi.text= datapemesanan.prodi
            tv_angkatan.text= datapemesanan.angkatan
            btn_tambahdatapemesanan.text = "Ubah Data Pemesanan"

        }else{
            div_datapemesanan.visibility = View.GONE
            div_kosong.visibility = View.VISIBLE
            btn_tambahdatapemesanan.text = "Isi Data Pemesanan"
        }
    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    fun setTotal(){
        tv_totalBelanja.text = Helper().gantiRupiah(totalHarga)
    }

    override fun onResume() {
        checkDataPemesanan()
        setTotal()
        super.onResume()
    }
}