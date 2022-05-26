package com.example.fitnessapp.progress

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity

data class Picture(

    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "imageData") var imageData: String,
    @ColumnInfo(name = "weight") var weight: String,
    @PrimaryKey (autoGenerate = true) val id: Int = 0
)