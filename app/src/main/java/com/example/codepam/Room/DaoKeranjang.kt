package com.example.codepam.Room

import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import com.example.codepam.model.Makanan_Minuman

@Dao
interface DaoKeranjang {

    @Insert(onConflict = REPLACE)
    fun insert(data: Makanan_Minuman)

    @Delete
    fun delete(data: Makanan_Minuman)

    @Delete
    fun delete(data: List<Makanan_Minuman>)

    @Update
    fun update(data: Makanan_Minuman): Int

    @Query("SELECT * from keranjang ORDER BY idTb ASC")
    fun getAll(): List<Makanan_Minuman>

    @Query("SELECT * FROM keranjang WHERE produk_id = :id LIMIT 1")
    fun getMakananMinuman(id: Int): Makanan_Minuman

    @Query("DELETE FROM keranjang WHERE produk_id = :id")
    fun deleteById(id: String): Int

    @Query("DELETE FROM keranjang")
    fun deleteAll(): Int
}