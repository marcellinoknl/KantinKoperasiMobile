package com.example.codepam.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Adapter
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.codepam.R
import com.example.codepam.adapter.AdapterBank
import com.example.codepam.helper.Helper
import com.example.codepam.model.Bank
import com.example.codepam.model.ResponModel
import com.example.codepam.model.Transaksi
import com.example.codepam.model.TransaksiMakananMinuman
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_pembayaran.*
import kotlinx.android.synthetic.main.toolbar.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PembayaranActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pembayaran)
        Helper().setToolbar(this, toolbar, "Pembayaran")
        displayBank()
    }

    fun displayBank(){
        var arrBank = ArrayList<Bank>()
        arrBank.add(Bank("Virtual Account BCA","12314324213","Marcellino", R.drawable.bca))
        arrBank.add(Bank("Virtual Account BRI","32322342342","Kelly",R.drawable.bri))
        arrBank.add(Bank("Virtual Account BNI","45435623123","Natanael",R.drawable.bni))
        arrBank.add(Bank("E-Money DANA","3242362234","Natanael",R.drawable.dana))
        arrBank.add(Bank("Virtual Account Indomaret","46363424332","Ucok",R.drawable.indomaret))
        arrBank.add(Bank("Virtual Account Alfamidi","257463433123","Butet",R.drawable.alfamidi))
        arrBank.add(Bank("E-Money Gopay","36442323423","Pariban",R.drawable.gopay))
        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        rv_data.layoutManager = layoutManager
        rv_data.adapter = AdapterBank(arrBank, object : AdapterBank.Listeners{
            override fun onClicked(data: Bank, index: Int) {
                bayar(data)
            }

        })

    }

    private fun bayar(bank:Bank){
        val json = intent.getStringExtra("extra")!!.toString()
        val checkout = Gson().fromJson(json,TransaksiMakananMinuman::class.java)
        checkout.bank = bank.nama
        ApiConfig.instanceRetrofit.transaksi(checkout).enqueue(object :
            Callback<ResponModel> {
            override fun onResponse(call: Call<ResponModel>, response: Response<ResponModel>) {
                val respon =response.body()!!
                if(respon.success==1){

                    val jsBank = Gson().toJson(bank,Bank::class.java)
                    val jsTransaksi = Gson().toJson(respon.transaksi,Transaksi::class.java)
                    val jsCheckout = Gson().toJson(checkout,TransaksiMakananMinuman::class.java)
                    val intent = Intent(this@PembayaranActivity,SuccessActivity::class.java)
                    intent.putExtra("bank",jsBank)
                    intent.putExtra("transaksi",jsTransaksi)
                    intent.putExtra("checkout",jsCheckout)
                    startActivity(intent)
                }
                else{
                    Toast.makeText(this@PembayaranActivity,"Error:"+respon.message, Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ResponModel>, t: Throwable) {
                Toast.makeText(this@PembayaranActivity,"Error:"+t.message, Toast.LENGTH_SHORT).show()

            }
        })
    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}