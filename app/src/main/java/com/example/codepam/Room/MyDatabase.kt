package com.example.codepam.Room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.codepam.model.Makanan_Minuman

@Database(entities = [Makanan_Minuman::class] /* List model Ex:NoteModel */, version = 1)
abstract class MyDatabase : RoomDatabase() {
    abstract fun daoKeranjang(): DaoKeranjang

    companion object {
        private var INSTANCE: MyDatabase? = null

        fun getInstance(context: Context): MyDatabase? {
            if (INSTANCE == null) {
                synchronized(MyDatabase::class) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        MyDatabase::class.java, "koperasi_kantinV9" // Database Name
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