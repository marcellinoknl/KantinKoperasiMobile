package com.example.codepam.model

class Transaksi {
    var id= 0
    var bank= ""
    var name= ""
    var phone= ""
    var total_harga= ""
    var total_item= ""
    var total_transfer= ""
    var user_id= ""
    var kode_payment= ""
    var kode_trx= ""
    var kode_unik= 0
    var status= ""
    var expired_at= ""
    var updated_at= ""
    var created_at= ""
    var details= ArrayList<DetailTransaksi>()

}