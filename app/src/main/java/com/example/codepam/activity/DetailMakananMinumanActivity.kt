package com.example.codepam.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.example.codepam.R
import com.example.codepam.Room.MyDatabase
import com.example.codepam.helper.Helper
import com.example.codepam.model.Makanan_Minuman
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_detail_makanan_minuman.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.toolbar_custom.*

class DetailMakananMinumanActivity : AppCompatActivity() {

    lateinit var tv_nama:TextView
    lateinit var tv_harga:TextView
    lateinit var tv_jenismakananminuman:TextView
    lateinit var tv_stockmakananminuman:TextView
    lateinit var image:ImageView
    lateinit var toolbar:Toolbar
    lateinit var makananminuman:Makanan_Minuman
    lateinit var myDb:MyDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_makanan_minuman)
        tv_nama = findViewById(R.id.tv_nama)
        tv_harga = findViewById(R.id.tv_harga)
        tv_jenismakananminuman = findViewById(R.id.tv_jenismakananminuman)
        tv_stockmakananminuman = findViewById(R.id.tv_stockmakananminuman)
        image = findViewById(R.id.image)
        toolbar = findViewById(R.id.toolbar)
        myDb = MyDatabase.getInstance(this)!! // call database

        getInfo()
        mainButton()
        checkKeranjang()
    }

    private fun mainButton() {
        btn_keranjang.setOnClickListener {
            val data = myDb.daoKeranjang().getMakananMinuman(makananminuman.produk_id)

            if(data == null){
                insert()
            }else{
                data.jumlah = data.jumlah + 1
                update(data)
            }

            val listNote = myDb.daoKeranjang().getAll() // get All data
            for(note :Makanan_Minuman in listNote){
                println("-----------------------")
                println(note.produk_id)
                println(note.nama_produk)
                println(note.harga)
            }
        }
        btn_toKeranjang.setOnClickListener{
//            startActivity(Intent(this,))
        }
    }
    fun insert(){

        CompositeDisposable().add(Observable.fromCallable { myDb.daoKeranjang().insert(makananminuman) }
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                checkKeranjang()
                Log.d("respons", "data inserted")
                Toast.makeText(this,"Telah ditambahkan ke keranjang",Toast.LENGTH_SHORT).show()
            })
    }

    fun update(data:Makanan_Minuman){

        CompositeDisposable().add(Observable.fromCallable { myDb.daoKeranjang().update(data) }
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                checkKeranjang()
                Log.d("respons", "data inserted")
                Toast.makeText(this,"Telah ditambahkan ke keranjang",Toast.LENGTH_SHORT).show()
            })
    }

    private fun checkKeranjang(){
        val dataKeranjang = myDb.daoKeranjang().getAll()
        if(dataKeranjang.isNotEmpty()){
            div_angka.visibility = View.VISIBLE
            tv_angka.text = dataKeranjang.size.toString()
        }else{
            div_angka.visibility = View.GONE
        }
    }

    private fun getInfo() {
        val data = intent.getStringExtra("extra")
        makananminuman = Gson().fromJson(data, Makanan_Minuman::class.java)

        // set Value
        tv_nama.text = makananminuman.nama_produk
        tv_harga.text = Helper().gantiRupiah(makananminuman.harga)

        val datajenismakananminuman = makananminuman.id_levelproduk
        if(datajenismakananminuman==1){
            print("Makanan Berat")
        }else if (datajenismakananminuman==2){
            print("Makanan Ringan")
        }else if(datajenismakananminuman==3){
            print("Minuman Berat")
        }else if(datajenismakananminuman==4){
            print("Minuman Ringan")
        }
        tv_jenismakananminuman.text = datajenismakananminuman.toString()

        tv_stockmakananminuman.text = makananminuman.stock

        val img ="http://192.168.100.11/PAM-Kantin_Koperasi_Web/public/images/MakananMinuman/"+ makananminuman.file_foto
        Picasso.get()
            .load(img)
            .placeholder(R.drawable.product)
            .error(R.drawable.product)
            .resize(400, 400)
            .into(image)

        // setToolbar
        setSupportActionBar(toolbar)
        supportActionBar!!.title = makananminuman.nama_produk
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}
