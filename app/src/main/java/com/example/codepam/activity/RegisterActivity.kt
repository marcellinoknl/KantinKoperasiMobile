package com.example.codepam.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.codepam.MainActivity
import com.example.codepam.R
import com.example.codepam.helper.SharedPref
import com.example.codepam.model.ResponModel
import com.google.android.material.textfield.TextInputLayout
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterActivity: AppCompatActivity() {
    lateinit var btnRegister:Button
    lateinit var editNoKTP:EditText
    lateinit var editNama:EditText
    lateinit var editNoTel:EditText
    lateinit var editEmail:EditText
    lateinit var editPass:EditText
    lateinit var pb: ProgressBar
    lateinit var s: SharedPref

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        s = SharedPref(this)
        btnRegister =  findViewById(R.id.btn_register)
        editNoKTP =  findViewById(R.id.edit_noKTP)
        editNama =  findViewById(R.id.edit_nama)
        editNoTel =  findViewById(R.id.edit_phone)
        editEmail =  findViewById(R.id.edit_email)
        editPass =  findViewById(R.id.edit_password)
        pb = findViewById(R.id.pb)
        btnRegister.setOnClickListener{
            register()
        }
    }

    fun register(){
        if (editNoKTP.text.isEmpty()){
            editNoKTP.error = "No.KTP Anda tidak boleh kosong"
            editNoKTP.requestFocus()
            return
        }else  if (editNama.text.isEmpty()){
            editNama.error = "Nama Anda tidak boleh kosong"
            editNama.requestFocus()
            return
        }else if (editNoTel.text.isEmpty()){
            editNoTel.error = "Nomor Telepon Anda tidak boleh kosong"
            editNoTel.requestFocus()
            return
        }else if (editEmail.text.isEmpty()){
            editEmail.error = "Email Anda tidak boleh kosong"
            editEmail.requestFocus()
            return
        }else if (editPass.text.isEmpty()){
            editPass.error = "Password Anda tidak boleh kosong"
            editPass.requestFocus()
            return
        }
        pb.visibility = View.VISIBLE
        ApiConfig.instanceRetrofit.register(editNoKTP.text.toString(),editNama.text.toString(),editNoTel.text.toString(),editEmail.text.toString(),editPass.text.toString()).enqueue(object : Callback<ResponModel>{
            override fun onResponse(call: Call<ResponModel>, response: Response<ResponModel>) {
                //handle ketika sukses
                pb.visibility = View.VISIBLE
                val respon =response.body()!!
                if(respon.success==1){
                    s.setStatusLogin(true)

                    val intent = Intent(this@RegisterActivity, MainActivity::class.java)
                    intent.addFlags(Intent. FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                    finish()
                    Toast.makeText(this@RegisterActivity,"Selamat Datang Customer Kami, "+respon.user.nama_lengkap,Toast.LENGTH_SHORT).show()
                }
                else{
                    Toast.makeText(this@RegisterActivity,"Error:"+respon.message,Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ResponModel>, t: Throwable) {
                //handle ketika gagal
                pb.visibility = View.GONE
                Toast.makeText(this@RegisterActivity,"Error:"+t.message,Toast.LENGTH_SHORT).show()

            }
        })
    }
}