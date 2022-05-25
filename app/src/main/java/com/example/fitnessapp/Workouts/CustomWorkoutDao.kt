package com.example.fitnessapp.Workouts

import androidx.room.*

@Dao

interface CustomWorkoutDao {
    @Query("SELECT * FROM customWorkout")
    fun getAll(): MutableList<CustomWorkout>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(items: List<CustomWorkout>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOne(items: CustomWorkout)

    //@Query("SELECT * FROM workout WHERE name LIKE :first AND " + "muscleGroup LIKE :last LIMIT 1")
    //fun findByName(first: String, last: Boolean): Workout

    @Delete
    fun delete(customWorkout: CustomWorkout)

    @Query("DELETE FROM customWorkout WHERE id = :id")
    fun deleteById(id: Int)

}