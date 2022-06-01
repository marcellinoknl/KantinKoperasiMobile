package com.example.codepam.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.codepam.R
import com.example.codepam.Room.MyDatabase
import com.example.codepam.adapter.AdapterKeranjang
import com.example.codepam.adapter.AdapterMakananMinuman


class KeranjangFragment : Fragment() {

    lateinit var btnDelete:ImageView
    lateinit var rv_makananminuman:RecyclerView
    lateinit var tvTotal:TextView
    lateinit var btnBayar:TextView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fragment_keranjang, container, false)
        init(view)
        mainButton()
        displayMakananMinuman()

        return view
    }

    private fun init(view: View){
        btnDelete = view.findViewById(R.id.btn_delete)
        rv_makananminuman = view.findViewById(R.id.rv_makananminuman)
        tvTotal = view.findViewById(R.id.tv_total)
        btnBayar = view.findViewById(R.id.btn_bayar)
    }

    private fun displayMakananMinuman(){
        val myDB = MyDatabase.getInstance(requireActivity())
        val lisMakananMinuman = myDB!!.daoKeranjang().getAll() as ArrayList

        val layoutManager = LinearLayoutManager(activity)
        layoutManager.orientation = LinearLayoutManager.VERTICAL

        rv_makananminuman.adapter = AdapterKeranjang(requireActivity(),lisMakananMinuman)
        rv_makananminuman.layoutManager = layoutManager
    }


    private fun mainButton(){
        btnDelete.setOnClickListener{

        }
        btnBayar.setOnClickListener{

        }
    }
    override fun onResume() {
        displayMakananMinuman()
        super.onResume()
    }
}