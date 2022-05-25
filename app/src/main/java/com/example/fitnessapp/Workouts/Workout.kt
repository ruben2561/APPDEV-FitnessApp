package com.example.fitnessapp.Workouts

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity

data class Workout(

    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "exersices id") var exersicesId: String,
    @PrimaryKey (autoGenerate = true) val id: Int = 0
)

