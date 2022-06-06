package com.example.codepam.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.codepam.MainActivity
import com.example.codepam.R
import com.example.codepam.helper.SharedPref
import com.example.codepam.model.ResponModel
import kotlinx.android.synthetic.main.activity_booking_ruangan.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BookingRuanganActivity : AppCompatActivity() {
    lateinit var s: SharedPref
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_booking_ruangan)
        s = SharedPref(this)
        btn_simpan.setOnClickListener{
            booking()
        }
    }
    fun booking(){
        if (edt_nama.text.isEmpty()){
            edt_nama.error = "Nama Peminjam tidak boleh kosong"
            edt_nama.requestFocus()
            return
        }else  if (edt_nim.text.isEmpty()){
            edt_nim.error = "NIM Peminjam tidak boleh kosong"
            edt_nim.requestFocus()
            return
        }else if (edt_prodi.text.isEmpty()){
            edt_prodi.error = "Prodi Peminjam tidak boleh kosong"
            edt_prodi.requestFocus()
            return
        }else if (edt_angkatan.text.isEmpty()){
            edt_angkatan.error = "Angkatan Peminjam tidak boleh kosong"
            edt_angkatan.requestFocus()
            return
        }else if (edt_namaruangan.text.isEmpty()){
            edt_namaruangan.error = "Nama Ruangan tidak boleh kosong"
            edt_namaruangan.requestFocus()
            return
        }else if (edt_jadwal.text.isEmpty()){
            edt_jadwal.error = "Jadwal Penggunaan tidak boleh kosong"
            edt_jadwal.requestFocus()
            return
        }else if (edt_keterangan.text.isEmpty()){
            edt_keterangan.error = "Keterangan Pemakaian tidak boleh kosong"
            edt_keterangan.requestFocus()
            return
        }
        pb.visibility = View.VISIBLE
        ApiConfig.instanceRetrofit.bookingruangan(edt_nama.text.toString(),edt_nim.text.toString(),edt_prodi.text.toString(),edt_angkatan.text.toString(),edt_namaruangan.text.toString(),edt_jadwal.text.toString(),edt_keterangan.text.toString()).enqueue(object :
            Callback<ResponModel> {
            override fun onResponse(call: Call<ResponModel>, response: Response<ResponModel>) {
                //handle ketika sukses
                pb.visibility = View.VISIBLE
                val respon =response.body()!!
                if(respon.success==1){
                    s.setStatusLogin(true)

                    val intent = Intent(this@BookingRuanganActivity, MainActivity::class.java)
                    intent.addFlags(Intent. FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                    finish()
                    Toast.makeText(this@BookingRuanganActivity,"Peminjaman Ruangan sedang diproses",
                        Toast.LENGTH_SHORT).show()
                }
                else{
                    Toast.makeText(this@BookingRuanganActivity,"Error:"+respon.message, Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ResponModel>, t: Throwable) {
                //handle ketika gagal
                pb.visibility = View.GONE
                Toast.makeText(this@BookingRuanganActivity,"Error:"+t.message, Toast.LENGTH_SHORT).show()

            }
        })
    }
}