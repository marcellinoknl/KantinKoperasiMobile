package com.example.codepam.model

class TransaksiMakananMinuman {

    lateinit var user_id:String
    lateinit var total_item:String
    lateinit var total_harga:String
    lateinit var name:String
    lateinit var phone:String
    lateinit var total_transfer:String
    lateinit var bank:String
    var produks = ArrayList<Item>()

    class Item{
        lateinit var produk_id:String
        lateinit var total_item:String
        lateinit var total_harga:String
        lateinit var catatan:String
    }



}

