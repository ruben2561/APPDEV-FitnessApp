package com.example.fitnessapp

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.fitnessapp.Workouts.CustomWorkoutDao
import com.example.fitnessapp.exercises.Exercise
import com.example.fitnessapp.exercises.ExerciseDao
import com.example.fitnessapp.Workouts.Workout
import com.example.fitnessapp.Workouts.WorkoutDao
import com.example.fitnessapp.progress.Picture
import com.example.fitnessapp.progress.PictureDao

@Database(entities = arrayOf(Exercise::class, Workout::class, Workout::class, Picture::class), version = 1)
abstract class GymBuddyDatabase : RoomDatabase() {
    abstract fun exerciseDao() : ExerciseDao
    abstract fun workoutDao() : WorkoutDao
    abstract fun customWorkoutDao(): CustomWorkoutDao
    abstract fun pictureDao() : PictureDao
}

/**
 * exercises.database
 * pictures.database
 * workouts.database
 * custom_workouts.database
 *
 */