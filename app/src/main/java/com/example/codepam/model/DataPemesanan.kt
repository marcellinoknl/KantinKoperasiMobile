package com.example.codepam.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "datapemesanan")
class DataPemesanan {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "idTb")
    var idTb = 0

    var id = 0
    var name = ""
    var phone = ""
    var prodi = ""
    var nim = ""
    var angkatan = ""
    var isSelected = false

}