package com.example.fitnessapp.Workouts.allWorkouts

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity

data class DefaultWorkout(

    @ColumnInfo(name = "name") var name: String,
    @ColumnInfo(name = "exersices id") var exersicesId: String,
    @PrimaryKey (autoGenerate = true) val id: Int = 0
)

