package com.example.fitnessapp.Workouts.customWorkouts

import androidx.room.*

@Dao

interface CustomWorkoutDao {
    @Query("SELECT * FROM customWorkout")
    fun getAll(): MutableList<CustomWorkout>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(items: List<CustomWorkout>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOne(items: CustomWorkout)

    @Delete
    fun delete(customWorkout: CustomWorkout)

    @Query("DELETE FROM customWorkout WHERE id = :id")
    fun deleteById(id: Int)

}