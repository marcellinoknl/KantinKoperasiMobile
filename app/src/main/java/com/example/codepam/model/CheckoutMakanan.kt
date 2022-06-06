package com.example.codepam.model

class CheckoutMakanan {
    lateinit var id_user: String
    lateinit var total_item: String
    lateinit var total_pembayaran: String
    lateinit var nama_penerima: String
    lateinit var nomor_telephone: String
    lateinit var catatan: String
    var produks = ArrayList<Item>()

    class Item {
        lateinit var id_makanan_minuman: String
        lateinit var kuantitas: String
        lateinit var total_harga: String
    }
}