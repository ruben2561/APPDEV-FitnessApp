package com.example.fitnessapp

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.fitnessapp.Workouts.CustomWorkout
import com.example.fitnessapp.Workouts.CustomWorkoutDao
import com.example.fitnessapp.exercises.Exercise
import com.example.fitnessapp.exercises.ExerciseDao
import com.example.fitnessapp.Workouts.DefaultWorkout
import com.example.fitnessapp.Workouts.DefaultWorkoutDao
import com.example.fitnessapp.progress.Picture
import com.example.fitnessapp.progress.PictureDao

@Database(entities = arrayOf(Exercise::class, DefaultWorkout::class, CustomWorkout::class, Picture::class), version = 1)
abstract class GymBuddyDatabase : RoomDatabase() {
    abstract fun exerciseDao() : ExerciseDao
    abstract fun defaultWorkoutDao() : DefaultWorkoutDao
    abstract fun customWorkoutDao(): CustomWorkoutDao
    abstract fun pictureDao() : PictureDao
}
