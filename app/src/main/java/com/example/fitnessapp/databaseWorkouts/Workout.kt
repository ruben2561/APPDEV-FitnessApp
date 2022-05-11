package com.example.fitnessapp.databaseWorkouts

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.fitnessapp.database.Excercise

@Entity

data class Workout(

    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "excersices id") var excersicesId: String,
    @PrimaryKey (autoGenerate = true) val id: Int = 0
)

