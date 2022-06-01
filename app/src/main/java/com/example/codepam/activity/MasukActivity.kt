package com.example.codepam.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.codepam.R
import com.example.codepam.helper.SharedPref


class MasukActivity : AppCompatActivity() {

    lateinit var s:SharedPref
    lateinit var btnLogin:Button
    lateinit var btnRegister:Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_masuk)
        s = SharedPref(this)

        mainButton()

    }

    private fun mainButton(){
        btnLogin = findViewById(R.id.button_login)
        btnRegister =  findViewById(R.id.btn_register)

        btnLogin.setOnClickListener{
            startActivity(Intent(this,LoginActivity::class.java))
        }
        btnRegister.setOnClickListener{
            startActivity(Intent(this,RegisterActivity::class.java))
        }

    }

}