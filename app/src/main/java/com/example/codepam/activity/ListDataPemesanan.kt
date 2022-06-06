package com.example.codepam.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.codepam.R
import com.example.codepam.Room.MyDatabase
import com.example.codepam.adapter.AdapterDataPemesanan
import com.example.codepam.adapter.AdapterMakananMinuman
import com.example.codepam.helper.Helper
import com.example.codepam.model.DataPemesanan
import com.example.codepam.model.Makanan_Minuman
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_listdatapemesanan.*
import kotlinx.android.synthetic.main.toolbar.*


class ListDataPemesanan: AppCompatActivity() {

    private lateinit var btn_tambahdatapemesanan:Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listdatapemesanan)
        Helper().setToolbar(this,toolbar,"List Data Pemesanan")
        btn_tambahdatapemesanan = findViewById(R.id.btn_tambahdatapemesanan)

        mainButton()

    }

    private fun display(){
        val myDb = MyDatabase.getInstance(this)!!
        val arrayList = myDb.daoDataPemesanan().getAll() as ArrayList

        if(arrayList.isEmpty()) div_kosong.visibility = View.VISIBLE
        else div_kosong.visibility = View.GONE

        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL

        rv_datapemesanan.adapter = AdapterDataPemesanan(arrayList,object : AdapterDataPemesanan.Listeners{

            override fun onClicked(data: DataPemesanan) {
                if(myDb.daoDataPemesanan().getByStatus(true) != null){
                    val datapemesananTerpilih =myDb.daoDataPemesanan().getByStatus(true)!!
                    datapemesananTerpilih.isSelected = false
                    update(datapemesananTerpilih)
                }

                data.isSelected = true
                update(data)
                onBackPressed()


            }

        })
        rv_datapemesanan.layoutManager = layoutManager
    }
    private fun update(data: DataPemesanan){
        val myDb = MyDatabase.getInstance(this)
        CompositeDisposable().add(Observable.fromCallable { myDb!!.daoDataPemesanan().update(data) }
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
            })
    }
    private fun mainButton(){
        btn_tambahdatapemesanan.setOnClickListener {
            startActivity(Intent(this,TambahDataPemesanan::class.java))
        }
    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    override fun onResume() {
        display()
        super.onResume()
    }
}