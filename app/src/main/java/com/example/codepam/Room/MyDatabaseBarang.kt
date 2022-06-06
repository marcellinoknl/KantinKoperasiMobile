package com.example.codepam.Room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.codepam.model.Barang
import com.example.codepam.model.Makanan_Minuman

@Database(entities = [Barang::class] /* List model Ex:NoteModel */, version = 1)
abstract class MyDatabaseBarang : RoomDatabase() {
    abstract fun daoKeranjangbarang(): DaoKeranjangBarang

    companion object {
        private var INSTANCE: MyDatabaseBarang? = null

        fun getInstance(context: Context): MyDatabaseBarang? {
            if (INSTANCE == null) {
                synchronized(MyDatabaseBarang::class) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        MyDatabaseBarang::class.java, "koperasi_kantinbarangV11" // Database Name
                    ).allowMainThreadQueries().build()
                }
            }
            return INSTANCE
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }
}