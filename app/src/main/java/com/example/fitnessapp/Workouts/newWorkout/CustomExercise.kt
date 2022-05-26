package com.example.fitnessapp.Workouts.newWorkout

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity

data class CustomExercise(

    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "muscleGroup") var muscleGroup: String,
    @ColumnInfo(name = "repsAndWeight") var RepsAndWeight: String,
    @PrimaryKey (autoGenerate = true) val id: Int = 0
)