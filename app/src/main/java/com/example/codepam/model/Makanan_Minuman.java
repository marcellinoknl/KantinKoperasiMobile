package com.example.codepam.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "keranjang")
public class Makanan_Minuman implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "idTb")
    public int idTb;

    public int produk_id;
    public String nama_produk;
    public int stock;
    public int harga;
    public String file_foto;
    public int id_levelproduk;
    public String created_at;
    public String updated_at;
    public int jumlah = 1;
    public boolean selected = true;
}
