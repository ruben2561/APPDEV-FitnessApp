package com.example.fitnessapp.exercises

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity

data class Exercise(

    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "muscleGroup") var muscleGroup: String,
    @PrimaryKey (autoGenerate = true) val id: Int = 0
)

