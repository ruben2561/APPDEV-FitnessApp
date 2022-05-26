package com.example.fitnessapp.Workouts.customWorkouts

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity

data class CustomWorkout(

    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "exersices id") var exersicesId: String,
    @ColumnInfo(name = "creation date") var creationDate: String,
    @PrimaryKey (autoGenerate = true) val id: Int = 0
)