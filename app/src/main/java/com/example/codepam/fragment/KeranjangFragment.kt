package com.example.codepam.fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.codepam.R
import com.example.codepam.Room.MyDatabase
import com.example.codepam.activity.*
import com.example.codepam.adapter.AdapterKeranjang
import com.example.codepam.helper.Helper
import com.example.codepam.helper.SharedPref
import com.example.codepam.model.Makanan_Minuman


class KeranjangFragment : Fragment() {

    lateinit var rv_makananminuman:RecyclerView
    lateinit var tvTotal:TextView
    lateinit var btnBayar:TextView
    lateinit var myDB : MyDatabase
    lateinit var cbAll:CheckBox
    lateinit var adapter : AdapterKeranjang
    var listMakananMinuman = ArrayList<Makanan_Minuman>()
    lateinit var s:SharedPref


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fragment_keranjang, container, false)
        init(view)
        mainButton()
        myDB = MyDatabase.getInstance(requireActivity())!!
        s=SharedPref(requireActivity())
        return view
    }

    private fun init(view: View){

        rv_makananminuman = view.findViewById(R.id.rv_makananminuman)
        tvTotal = view.findViewById(R.id.tv_total)
        btnBayar = view.findViewById(R.id.btn_bayar)
        cbAll = view.findViewById(R.id.cb_all)
    }
    var totalHarga = 0
    fun hitungTotal(){
        val listMakananMinuman = myDB.daoKeranjang().getAll() as ArrayList
        totalHarga = 0
        var isCentangSemua = true
        for(makananminuman in listMakananMinuman) {
            if (makananminuman.selected) {
                totalHarga += (makananminuman.harga * makananminuman.jumlah)
            }else{
                isCentangSemua = false
            }
        }
        cbAll.isSelected = isCentangSemua
        tvTotal.text = Helper().gantiRupiah(totalHarga)
    }

    private fun displayMakananMinuman(){
        listMakananMinuman = myDB.daoKeranjang().getAll() as ArrayList

        val layoutManager = LinearLayoutManager(activity)
        layoutManager.orientation = LinearLayoutManager.VERTICAL

         adapter =  AdapterKeranjang(requireActivity(),listMakananMinuman, object : AdapterKeranjang.Listeners{
            @SuppressLint("NotifyDataSetChanged")
            override fun onDelete(position: Int) {
                listMakananMinuman.removeAt(position)
                adapter.notifyDataSetChanged()
                hitungTotal()
            }

            override fun onUpdate() {
                hitungTotal()
            }
        })
        rv_makananminuman.adapter = adapter
        rv_makananminuman.layoutManager = layoutManager
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun mainButton(){

        btnBayar.setOnClickListener{

            if(s.getStatusLogin()){
                var isThereProduk = false
                for (p in listMakananMinuman){
                    if(p.selected) isThereProduk = true
                }

                if(isThereProduk){
                    val intent= Intent(requireActivity(),DetailKeranjangMakananMinuman::class.java)
                    intent.putExtra("extra",totalHarga.toString())
                    startActivity(intent)
                }else{
                    Toast.makeText(requireContext(),"Tidak ada Makanan/Minuman yang terpilih",Toast.LENGTH_SHORT).show()
                }
            }else{
                requireActivity().startActivity(Intent(requireActivity(),MasukActivity::class.java))
            }


        }
        cbAll.setOnClickListener{
            for (i in listMakananMinuman.indices){
                var makananminuman = listMakananMinuman[i]
                makananminuman.selected = cbAll.isChecked
                listMakananMinuman[i] = makananminuman
            }
            adapter.notifyDataSetChanged()
        }
    }


    override fun onResume() {
        displayMakananMinuman()
        hitungTotal()
        super.onResume()
    }


}