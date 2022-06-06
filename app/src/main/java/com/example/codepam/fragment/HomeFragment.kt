package com.example.codepam.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.example.codepam.MainActivity
import com.example.codepam.R
import com.example.codepam.adapter.AdapterBarang
import com.example.codepam.adapter.AdapterMakananMinuman
import com.example.codepam.adapter.AdapterRuangan
import com.example.codepam.adapter.AdapterSlider
import com.example.codepam.model.Barang
import com.example.codepam.model.Makanan_Minuman
import com.example.codepam.model.ResponModel
import com.example.codepam.model.Ruangan
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment() {
    lateinit var vpSlider: ViewPager
    lateinit var rvMakanMinuman: RecyclerView
    lateinit var rvBarang: RecyclerView
    lateinit var rvRuangan: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_home, container, false)
        init(view)
        getMakananMinuman()
        getBarang()



        return view
    }
    private var listMakananMinuman:ArrayList<Makanan_Minuman> = ArrayList()
    fun getMakananMinuman(){
        ApiConfig.instanceRetrofit.getMakananMinuman().enqueue(object :
            Callback<ResponModel> {
            override fun onResponse(call: Call<ResponModel>, response: Response<ResponModel>) {
                val res = response.body()!!
                if(res.success==1){
                    listMakananMinuman = res.produks
                    displayMakananMinuman()
                }
            }

            override fun onFailure(call: Call<ResponModel>, t: Throwable) {
                //handle ketika gagal

            }
        })
    }
    private var listBarang:ArrayList<Barang> = ArrayList()
    fun getBarang(){
        ApiConfig.instanceRetrofit.getBarang().enqueue(object :
            Callback<ResponModel> {
            override fun onResponse(call: Call<ResponModel>, response: Response<ResponModel>) {
                val res = response.body()!!
                if(res.success==1){
                    listBarang = res.barangs
                    displayBarang()
                }
            }

            override fun onFailure(call: Call<ResponModel>, t: Throwable) {
                //handle ketika gagal

            }
        })
    }

    fun displayMakananMinuman(){
        val arrSlider = ArrayList<Int>()
        arrSlider.add(R.drawable.nasigoreng)
        arrSlider.add(R.drawable.alatulis)
        arrSlider.add(R.drawable.soda)

        val adapterSlider = AdapterSlider(arrSlider,activity)
        vpSlider.adapter = adapterSlider

        val layoutManager = LinearLayoutManager(activity)
        layoutManager.orientation = LinearLayoutManager.HORIZONTAL

        rvMakanMinuman.adapter = AdapterMakananMinuman(requireActivity(),listMakananMinuman )
        rvMakanMinuman.layoutManager = layoutManager

    }

    fun displayBarang(){


        val layoutManager = LinearLayoutManager(activity)
        layoutManager.orientation = LinearLayoutManager.HORIZONTAL

        rvBarang.adapter = AdapterBarang(requireActivity(),listBarang )
        rvBarang.layoutManager = layoutManager

    }


    fun init(view: View){
        vpSlider = view.findViewById(R.id.vp_slider)
        rvMakanMinuman = view.findViewById(R.id.rv_makananminuman)
        rvBarang = view.findViewById(R.id.rv_barang)
        rvRuangan = view.findViewById(R.id.rv_ruangan)
    }


}