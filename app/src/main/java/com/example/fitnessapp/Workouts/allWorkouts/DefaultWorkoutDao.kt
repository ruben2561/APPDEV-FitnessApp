package com.example.fitnessapp.Workouts.allWorkouts

import androidx.room.*

@Dao

interface DefaultWorkoutDao {
    @Query("SELECT * FROM defaultWorkout")
    fun getAll(): MutableList<DefaultWorkout>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(items: List<DefaultWorkout>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOne(items: DefaultWorkout)

    @Delete
    fun delete(defaultWorkout: DefaultWorkout)

    @Query("DELETE FROM defaultWorkout WHERE id = :id")
    fun deleteById(id: Int)

}