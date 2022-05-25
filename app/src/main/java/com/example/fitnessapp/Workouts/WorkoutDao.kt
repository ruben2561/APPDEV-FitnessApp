package com.example.fitnessapp.Workouts

import androidx.room.*

@Dao

interface WorkoutDao {
    @Query("SELECT * FROM workout")
    fun getAll(): MutableList<Workout>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(items: List<Workout>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOne(items: Workout)

    //@Query("SELECT * FROM workout WHERE name LIKE :first AND " + "muscleGroup LIKE :last LIMIT 1")
    //fun findByName(first: String, last: Boolean): Workout

    @Delete
    fun delete(workout: Workout)

    @Query("DELETE FROM workout WHERE id = :id")
    fun deleteById(id: Int)

}