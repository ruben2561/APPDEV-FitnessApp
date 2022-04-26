package com.example.fitnessapp.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = arrayOf(Excercise::class), version = 1)
abstract class ExcerciseDatabase : RoomDatabase() {
    abstract fun excerciseDao() : ExcerciseDao
}