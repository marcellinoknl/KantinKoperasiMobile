package com.example.codepam

import android.content.*
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.codepam.activity.LoginActivity
import com.example.codepam.activity.MasukActivity
import com.example.codepam.fragment.AkunFragment
import com.example.codepam.fragment.HomeFragment
import com.example.codepam.fragment.KeranjangBarang
import com.example.codepam.fragment.KeranjangFragment
import com.example.codepam.helper.SharedPref
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    val fargmentHome: Fragment = HomeFragment()
    val fargmentKeranjang: Fragment = KeranjangFragment()
    val fargmentKeranjangBarang: Fragment = KeranjangBarang()
    val fargmentAkun: Fragment = AkunFragment()
    val fm: FragmentManager = supportFragmentManager
    var active: Fragment = fargmentHome //menandai fragment mana yang default

    private lateinit var menu: Menu
    private lateinit var menuItem: MenuItem
    private lateinit var bottomNavigationView: BottomNavigationView

    private var statusLogin = false

    private lateinit var s:SharedPref
    private var  dariDetail : Boolean = false
    private var  dariDetail2 : Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setUpBottomNav()

        s = SharedPref(this)
        LocalBroadcastManager.getInstance(this).registerReceiver(pesan, IntentFilter("event:keranjang"))
        LocalBroadcastManager.getInstance(this).registerReceiver(pesan2, IntentFilter("event:keranjangbarang"))

    }

    val pesan : BroadcastReceiver = object : BroadcastReceiver(){
        override fun onReceive(p0: Context?, p1: Intent?) {
            dariDetail = true

        }


    }
    val pesan2 : BroadcastReceiver = object : BroadcastReceiver(){
        override fun onReceive(p0: Context?, p1: Intent?) {
            dariDetail2 = true
        }


    }
    fun setUpBottomNav(){
        fm.beginTransaction().add(R.id.container, fargmentHome).show(fargmentHome).commit()
        fm.beginTransaction().add(R.id.container, fargmentKeranjang).hide(fargmentKeranjang).commit()
        fm.beginTransaction().add(R.id.container, fargmentKeranjangBarang).hide(fargmentKeranjangBarang).commit()
        fm.beginTransaction().add(R.id.container, fargmentAkun).hide(fargmentAkun).commit()

        bottomNavigationView = findViewById(R.id.nav_view)
        menu = bottomNavigationView.menu
        menuItem = menu.getItem(0)
        menuItem.isChecked = true

        bottomNavigationView.setOnNavigationItemSelectedListener {item->

            when(item.itemId){
                R.id.navigation_home->{
//                    Log.d("Respons", "Home")
//                    menuItem = menu.getItem(0)
//                    menuItem.isChecked = true
//                    fm.beginTransaction().hide(active).show(fargmentHome).commit()
//                    active = fargmentHome
                    callFargment(0,fargmentHome)

                }
                R.id.navigation_keranjang->{
//                    Log.d("Respons", "Keranjang")
//                    menuItem = menu.getItem(1)
//                    menuItem.isChecked = true
//                    fm.beginTransaction().hide(active).show(fargmentKeranjang).commit()
//                    active = fargmentKeranjang
                    callFargment(1,fargmentKeranjang)
                }
                R.id.navigation_keranjangbarang->{
//                    Log.d("Respons", "Keranjang")
//                    menuItem = menu.getItem(1)
//                    menuItem.isChecked = true
//                    fm.beginTransaction().hide(active).show(fargmentKeranjang).commit()
//                    active = fargmentKeranjang
                    callFargment(2,fargmentKeranjangBarang)
                }
                R.id.navigation_akun->{
//                    Log.d("Respons", "Akun")
//                    menuItem = menu.getItem(2)
//                    menuItem.isChecked = true
//                    fm.beginTransaction().hide(active).show(fargmentAkun).commit()
//                    active = fargmentAkun
                    if (s.getStatusLogin()){
                        callFargment(3,fargmentAkun)
                    }else{
                        startActivity(Intent(this, MasukActivity::class.java))
                    }

                }
            }

            false
        }
    }

    fun callFargment (int:Int, fragment: Fragment){
        menuItem = menu.getItem(int)
        menuItem.isChecked = true
        fm.beginTransaction().hide(active).show(fragment).commit()
        active = fragment
    }

    override fun onResume() {
        if(dariDetail){
            dariDetail = false
            callFargment(1,fargmentKeranjang)
        }
        if(dariDetail2){
            dariDetail2 = false
            callFargment(2,fargmentKeranjangBarang)
        }
        super.onResume()
    }
}