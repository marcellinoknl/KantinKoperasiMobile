package com.example.codepam.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.codepam.R
import com.example.codepam.Room.MyDatabaseBarang
import com.example.codepam.adapter.AdapterKeranjangBarang
import com.example.codepam.helper.Helper
import com.example.codepam.helper.SharedPref
import com.example.codepam.model.Barang



class KeranjangBarang : Fragment() {


    lateinit var rv_barang:RecyclerView
    lateinit var tvTotal:TextView
    lateinit var btnBayar:TextView
    lateinit var myDB : MyDatabaseBarang
    lateinit var cbAll:CheckBox
    lateinit var adapter : AdapterKeranjangBarang
    var listBarang = ArrayList<Barang>()
    lateinit var s:SharedPref



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fragment_keranjang_barang, container, false)
        init(view)
        mainButton()
        myDB = MyDatabaseBarang.getInstance(requireActivity())!!
        s=SharedPref(requireActivity())
        return view
    }

    private fun init(view: View){

        rv_barang = view.findViewById(R.id.rv_barang)
        tvTotal = view.findViewById(R.id.tv_totalbarang)
        btnBayar = view.findViewById(R.id.btn_bayar)
        cbAll = view.findViewById(R.id.cb_all)
    }

    fun hitungTotal(){
        val listBarang = myDB.daoKeranjangbarang().getAll() as ArrayList

        var totalHarga = 0
        var isCentangSemua = true
        for(barang in listBarang) {
            if (barang.selected) {
                totalHarga += (barang.harga * barang.jumlah)
            }else{
                isCentangSemua = false
            }
        }
        cbAll.isSelected = isCentangSemua
        tvTotal.text = Helper().gantiRupiah(totalHarga)
    }

    private fun displayBarang(){
        listBarang = myDB.daoKeranjangbarang().getAll() as ArrayList

        val layoutManager = LinearLayoutManager(activity)
        layoutManager.orientation = LinearLayoutManager.VERTICAL

         adapter =  AdapterKeranjangBarang(requireActivity(),listBarang, object : AdapterKeranjangBarang.Listeners{
            @SuppressLint("NotifyDataSetChanged")
            override fun onDelete(position: Int) {
                listBarang.removeAt(position)
                adapter.notifyDataSetChanged()
                hitungTotal()
            }

            override fun onUpdate() {
                hitungTotal()
            }
        })
        rv_barang.adapter = adapter
        rv_barang.layoutManager = layoutManager
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun mainButton(){

        btnBayar.setOnClickListener{

        }
        cbAll.setOnClickListener{
            for (i in listBarang.indices){
                var barang = listBarang[i]
                barang.selected = cbAll.isChecked
                listBarang[i] = barang
            }
            adapter.notifyDataSetChanged()
        }
    }


    override fun onResume() {
        displayBarang()
        hitungTotal()
        super.onResume()
    }


}