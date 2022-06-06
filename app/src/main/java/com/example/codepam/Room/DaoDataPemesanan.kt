package com.example.codepam.Room

import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import com.example.codepam.model.DataPemesanan
import com.example.codepam.model.Makanan_Minuman

@Dao
interface DaoDataPemesanan {

    @Insert(onConflict = REPLACE)
    fun insert(data: DataPemesanan)

    @Delete
    fun delete(data: DataPemesanan)


    @Update
    fun update(data: DataPemesanan): Int

    @Query("SELECT * from datapemesanan ORDER BY idTb ASC")
    fun getAll(): List<DataPemesanan>

    @Query("SELECT * FROM datapemesanan WHERE id = :id LIMIT 1")
    fun getDataPemesanan(id: Int): DataPemesanan

    @Query("SELECT * FROM datapemesanan WHERE isSelected = :status LIMIT 1")
    fun getByStatus(status: Boolean): DataPemesanan?

    @Query("DELETE FROM datapemesanan WHERE id = :id")
    fun deleteById(id: String): Int

    @Query("DELETE FROM datapemesanan")
    fun deleteAll(): Int
}