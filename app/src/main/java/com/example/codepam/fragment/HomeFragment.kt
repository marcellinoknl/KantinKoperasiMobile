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

        val layoutManager2 = LinearLayoutManager(activity)
        layoutManager2.orientation = LinearLayoutManager.HORIZONTAL
//
//        rvBarang.adapter = AdapterBarang(arrBarang)
//        rvBarang.layoutManager = layoutManager2
//
//        val layoutManager3 = LinearLayoutManager(activity)
//        layoutManager2.orientation = LinearLayoutManager.HORIZONTAL
//
//        rvRuangan.adapter = AdapterRuangan(arrRuangan)
//        rvRuangan.layoutManager = layoutManager3
    }

    fun init(view: View){
        vpSlider = view.findViewById(R.id.vp_slider)
        rvMakanMinuman = view.findViewById(R.id.rv_makananminuman)
        rvBarang = view.findViewById(R.id.rv_barang)
        rvRuangan = view.findViewById(R.id.rv_ruangan)
    }

//    val arrMakanMinum: ArrayList<Makanan_Minuman>get(){
//        val arr = ArrayList<Makanan_Minuman>()
//        val p1 = Makanan_Minuman()
//        p1.nama_produk = "Nasi Goreng Spesial"
//        p1.harga = "Rp.25.000"
//        p1.gambar = R.drawable.nasigoreng
//
//        val p2 = Makanan_Minuman()
//        p2.nama = "Sate"
//        p2.harga = "Rp.15.000"
//        p2.gambar = R.drawable.sate
//
//        val p3 = Makanan_Minuman()
//        p3.nama = "Minuman bersoda"
//        p3.harga = "Rp.5.000"
//        p3.gambar = R.drawable.soda
//
//        val p4 = Makanan_Minuman()
//        p4.nama = "Jus Mentimun"
//        p4.harga = "Rp.5.000"
//        p4.gambar = R.drawable.jus
//
//        val p5 = Makanan_Minuman()
//        p5.nama = "Pillows"
//        p5.harga = "Rp.7.000"
//        p5.gambar = R.drawable.pillows
//
//        arr.add(p1)
//        arr.add(p2)
//        arr.add(p3)
//        arr.add(p4)
//        arr.add(p5)
//
//        return arr
//    }
//
//    val arrBarang: ArrayList<Barang>get(){
//        val arr = ArrayList<Barang>()
//        val p1 = Barang()
//        p1.nama = "Pensil"
//        p1.harga = "Rp.4.000"
//        p1.gambar = R.drawable.pensil
//
//        val p2 = Barang()
//        p2.nama = "Pulpen"
//        p2.harga = "Rp.5.000"
//        p2.gambar = R.drawable.pulpen
//
//        val p3 = Barang()
//        p3.nama = "Penghapus"
//        p3.harga = "Rp.3.000"
//        p3.gambar = R.drawable.penghapus
//
//        val p4 = Barang()
//        p4.nama = "Double Folio"
//        p4.harga = "Rp.5.000"
//        p4.gambar = R.drawable.folio
//
//        val p5 = Barang()
//        p5.nama = "Buku Tulis"
//        p5.harga = "Rp.6.000"
//        p5.gambar = R.drawable.bukutulis
//
//        arr.add(p1)
//        arr.add(p2)
//        arr.add(p3)
//        arr.add(p4)
//        arr.add(p5)
//
//        return arr
//    }
//
//    val arrRuangan: ArrayList<Ruangan>get(){
//        val arr = ArrayList<Ruangan>()
//        val p1 = Ruangan()
//        p1.nama = "Ruangan Rapat"
//        p1.harga = "Rp.4.000"
//        p1.gambar = R.drawable.pensil
//
//        val p2 = Ruangan()
//        p2.nama = "Pulpen"
//        p2.harga = "Rp.5.000"
//        p2.gambar = R.drawable.pulpen
//
//        val p3 = Ruangan()
//        p3.nama = "Penghapus"
//        p3.harga = "Rp.3.000"
//        p3.gambar = R.drawable.penghapus
//
//        val p4 = Ruangan()
//        p4.nama = "Double Folio"
//        p4.harga = "Rp.5.000"
//        p4.gambar = R.drawable.folio
//
//        val p5 = Ruangan()
//        p5.nama = "Buku Tulis"
//        p5.harga = "Rp.6.000"
//        p5.gambar = R.drawable.bukutulis
//
//        arr.add(p1)
//        arr.add(p2)
//        arr.add(p3)
//        arr.add(p4)
//        arr.add(p5)
//
//        return arr
//    }
}