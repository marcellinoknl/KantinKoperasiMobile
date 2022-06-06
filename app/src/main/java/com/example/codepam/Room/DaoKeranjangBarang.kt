package com.example.codepam.Room

import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import com.example.codepam.model.Barang


@Dao
interface DaoKeranjangBarang {

    @Insert(onConflict = REPLACE)
    fun insert(data: Barang)

    @Delete
    fun delete(data: Barang)

    @Delete
    fun delete(data: List<Barang>)

    @Update
    fun update(data: Barang): Int

    @Query("SELECT * from keranjangbarang ORDER BY idTbbarang ASC")
    fun getAll(): List<Barang>

    @Query("SELECT * FROM keranjangbarang WHERE barang_id = :id LIMIT 1")
    fun getBarang(id: Int): Barang

    @Query("DELETE FROM keranjangbarang WHERE barang_id = :id")
    fun deleteById(id: String): Int

    @Query("DELETE FROM keranjangbarang")
    fun deleteAll(): Int
}