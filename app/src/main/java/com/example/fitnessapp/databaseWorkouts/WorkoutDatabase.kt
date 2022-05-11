package com.example.fitnessapp.databaseWorkouts

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = arrayOf(Workout::class), version = 1)
abstract class WorkoutDatabase : RoomDatabase() {
    abstract fun workoutDao() : WorkoutDao
}