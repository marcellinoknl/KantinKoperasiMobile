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
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.codepam.R
import com.example.codepam.Room.MyDatabaseBarang
import com.example.codepam.helper.Helper
import com.example.codepam.model.Barang
import com.example.codepam.util.Config
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_detail_barang.*
import kotlinx.android.synthetic.main.activity_detail_makanan_minuman.btn_keranjang
import kotlinx.android.synthetic.main.toolbar_custom.*

class DetailBarangActivity : AppCompatActivity() {

    lateinit var tv_nama: TextView
    lateinit var tv_harga: TextView
    lateinit var tv_jenisbarang: TextView
    lateinit var tv_stockbarang: TextView
    lateinit var tv_pesanbarang: TextView
    lateinit var image: ImageView
    lateinit var toolbar: Toolbar
    lateinit var barang: Barang
    lateinit var myDb: MyDatabaseBarang

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_barang)
        tv_nama = findViewById(R.id.tv_nama)
        tv_harga = findViewById(R.id.tv_harga)
        tv_jenisbarang = findViewById(R.id.tv_jenismakananminuman)
        tv_stockbarang = findViewById(R.id.tv_stockmakananminuman)
        tv_pesanbarang= findViewById(R.id.pesanbarang)
        image = findViewById(R.id.image)
        toolbar = findViewById(R.id.toolbar)
        myDb = MyDatabaseBarang.getInstance(this)!! // call database

        getInfo()
        mainButton()
        checkKeranjang()
    }

    private fun mainButton() {
        btn_keranjang.setOnClickListener {
            val data = myDb.daoKeranjangbarang().getBarang(barang.barang_id)

            if(data == null){
                insert()
            }else{
                data.jumlah = data.jumlah + 1
                update(data)
            }
            barang.stock -= 1
            tv_stockbarang.text = barang.stock.toString()
            val listNote = myDb.daoKeranjangbarang().getAll() // get All data
            for(note : Barang in listNote){
                println("-----------------------")
                println(note.barang_id)
                println(note.nama_barang)
                println(note.harga)
            }
        }
        btn_toKeranjang.setOnClickListener{
            val intent2 = Intent("event:keranjangbarang")
            LocalBroadcastManager.getInstance(this).sendBroadcast(intent2)
            onBackPressed()
        }
        pesanbarang.setOnClickListener{
            val intent2 = Intent("event:keranjangbarang")
            LocalBroadcastManager.getInstance(this).sendBroadcast(intent2)
            onBackPressed()
        }
    }
    fun insert(){

        CompositeDisposable().add(Observable.fromCallable { myDb.daoKeranjangbarang().insert(barang) }
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                checkKeranjang()
                Log.d("respons", "data inserted")
                Toast.makeText(this,"Telah ditambahkan ke keranjang", Toast.LENGTH_SHORT).show()
            })
    }

    fun update(data: Barang){

        CompositeDisposable().add(Observable.fromCallable { myDb.daoKeranjangbarang().update(data) }
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                checkKeranjang()
                Log.d("respons", "data inserted")
                Toast.makeText(this,"Telah ditambahkan ke keranjang", Toast.LENGTH_SHORT).show()
            })
    }

    private fun checkKeranjang(){
        val dataKeranjang = myDb.daoKeranjangbarang().getAll()
        if(dataKeranjang.isNotEmpty()){
            div_angka.visibility = View.VISIBLE
            tv_angka.text = dataKeranjang.size.toString()
        }else{
            div_angka.visibility = View.GONE
        }
    }

    private fun getInfo() {
        val data = intent.getStringExtra("extra")
        barang = Gson().fromJson(data, Barang::class.java)

        // set Value
        tv_nama.text = barang.nama_barang
        tv_harga.text = Helper().gantiRupiah(barang.harga)

        val datajenisbarang = barang.id_levelbarang
        if(datajenisbarang==1){
            print("Snack")
        }else if (datajenisbarang==2){
            print("Peralatan Olahraga")
        }else if(datajenisbarang==3){
            print("Peralatan Kebersihan")
        }else if(datajenisbarang==4){
            print("ATK")
        }
        tv_jenisbarang.text = datajenisbarang.toString()

        tv_stockbarang.text = barang.stock.toString()

        val img = Config.barangUrl+ barang.file_foto
        Picasso.get()
            .load(img)
            .placeholder(R.drawable.product)
            .error(R.drawable.product)
            .resize(400, 400)
            .into(image)

        // setToolbar
        setSupportActionBar(toolbar)
        supportActionBar!!.title = barang.nama_barang
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}
