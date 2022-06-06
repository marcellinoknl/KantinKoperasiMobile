package com.example.codepam.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.codepam.R
import com.example.codepam.Room.MyDatabase
import com.example.codepam.Room.MyDatabaseBarang
import com.example.codepam.helper.Helper
import com.example.codepam.model.DataPemesanan
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.toolbar.*

class TambahDataPemesanan : AppCompatActivity(){

    private lateinit var btn_simpan:Button
    private lateinit var edt_nama:EditText
    private lateinit var edt_prodi:EditText
    private lateinit var edt_nim:EditText
    private lateinit var edt_angkatan:EditText
    private lateinit var edt_phone:EditText
    private lateinit var pb:ProgressBar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tambahdatapemesanan)
        Helper().setToolbar(this,toolbar,"Form Data Pemesanan")
        btn_simpan = findViewById(R.id.btn_simpan)
        edt_nama = findViewById(R.id.edt_nama)
        edt_prodi = findViewById(R.id.edt_prodi)
        edt_nim = findViewById(R.id.edt_nim)
        edt_angkatan = findViewById(R.id.edt_angkatan)
        edt_phone = findViewById(R.id.edt_phone)
        pb = findViewById(R.id.pb)
        mainButton()
    }

    private fun mainButton() {
        btn_simpan.setOnClickListener{
            simpan()

//            onBackPressed()
        }
    }

    private fun simpan(){
        if (edt_nama.text.isEmpty()){
            edt_nama.error = "Nama Pemesan tidak boleh kosong"
            edt_nama.requestFocus()
            return
        }else  if (edt_prodi.text.isEmpty()){
            edt_prodi.error = "Prodi Pemesan tidak boleh kosong"
            edt_prodi.requestFocus()
            return
        }else if (edt_nim.text.isEmpty()){
            edt_nim.error = "NIM Pemesan tidak boleh kosong"
            edt_nim.requestFocus()
            return
        }else if (edt_angkatan.text.isEmpty()){
            edt_angkatan.error = "Angkatan Pemesan tidak boleh kosong"
            edt_angkatan.requestFocus()
            return
        }else if (edt_phone.text.isEmpty()){
            edt_phone.error = "Nomor telepon pemesan tidak boleh kosong"
            edt_phone.requestFocus()
            return
        }

        val datapemesanan = DataPemesanan()
        datapemesanan.name = edt_nama.text.toString()
        datapemesanan.prodi = edt_prodi.text.toString()
        datapemesanan.nim = edt_nim.text.toString()
        datapemesanan.angkatan = edt_angkatan.text.toString()
        datapemesanan.phone = edt_phone.text.toString()


        insert(datapemesanan)
    }



    fun insert(data:DataPemesanan){
        val myDb = MyDatabase.getInstance(this)!!
        CompositeDisposable().add(Observable.fromCallable { myDb.daoDataPemesanan().insert(data) }
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                Toast.makeText(this@TambahDataPemesanan, "Insert Data Pemesanan Berhasil", Toast.LENGTH_SHORT).show()
                for (datapemesanan in myDb.daoDataPemesanan().getAll()){
                    Log.d("DataPemesanan","pemesan:"+datapemesanan.name+" - "+datapemesanan.nim+" - "+datapemesanan.prodi+" - "+datapemesanan.phone+" - "+datapemesanan.angkatan)
                }
                pb.visibility = View.VISIBLE
                onBackPressed()
            })
    }



    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}