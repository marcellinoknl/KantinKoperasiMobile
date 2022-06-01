package com.example.codepam.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.codepam.MainActivity
import com.example.codepam.R
import com.example.codepam.helper.SharedPref
import com.example.codepam.model.ResponModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.math.log


class LoginActivity : AppCompatActivity() {

    lateinit var s:SharedPref
    lateinit var btnLogin:Button
    lateinit var editEmail: EditText
    lateinit var editPass: EditText
    lateinit var pb:ProgressBar


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        s = SharedPref(this)
        editEmail =  findViewById(R.id.edit_email)
        editPass =  findViewById(R.id.edit_password)
        btnLogin = findViewById(R.id.button_login)
        pb = findViewById(R.id.pb)
        btnLogin.setOnClickListener{
            login()
        }
    }
    fun login(){
        if (editEmail.text.isEmpty()){
            editEmail.error = "Email Anda tidak boleh kosong"
            editEmail.requestFocus()
            return
        }else  if (editPass.text.isEmpty()){
            editPass.error = "Password Anda tidak boleh kosong"
            editPass.requestFocus()
            return
        }

        pb.visibility = View.VISIBLE

        ApiConfig.instanceRetrofit.login(editEmail.text.toString(),editPass.text.toString()).enqueue(object :
            Callback<ResponModel> {
            override fun onResponse(call: Call<ResponModel>, response: Response<ResponModel>) {
                //handle ketika sukses
                pb.visibility = View.VISIBLE
                val respon =response.body()!!
                if(respon.success==1){
                    s.setStatusLogin(true)
                    s.setUser(respon.user)
//                    s.setString(s.nama_lengkap,respon.user.nama_lengkap)
//                    s.setString(s.ktp,respon.user.ktp)
//                    s.setString(s.nohp,respon.user.nohp)
//                    s.setString(s.email,respon.user.email)
                    val intent = Intent(this@LoginActivity,MainActivity::class.java)
                    intent.addFlags(Intent. FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                    finish()
                    Toast.makeText(this@LoginActivity,"Selamat Datang Customer Kami, "+respon.user.nama_lengkap,
                        Toast.LENGTH_SHORT).show()
                }
                else{
                    Toast.makeText(this@LoginActivity,"Error:"+respon.message, Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ResponModel>, t: Throwable) {
                //handle ketika gagal
                pb.visibility = View.GONE
                Toast.makeText(this@LoginActivity,"Error:"+t.message, Toast.LENGTH_SHORT).show()

            }
        })
    }

}