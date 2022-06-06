package com.example.codepam.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "keranjangbarang")
public class Barang implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "idTbbarang")
    public int idTbbarang;

    public int barang_id;
    public String nama_barang;
    public int stock;
    public int harga;
    public String file_foto;
    public int id_levelbarang;
    public String created_at;
    public String updated_at;
    public int jumlah = 1;
    public boolean selected = true;
}