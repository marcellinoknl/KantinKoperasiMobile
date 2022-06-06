package com.example.codepam.model

class ResponModel {

    var success = 0
    lateinit var message:String
    var user = User()
    var produks:ArrayList<Makanan_Minuman> = ArrayList()
    var barangs:ArrayList<Barang> = ArrayList()
    var transaksi = Transaksi()
    var transaksis:ArrayList<Transaksi> = ArrayList()
}