package com.example.fitnessapp.Workouts.allWorkouts

import androidx.room.*
import com.example.fitnessapp.Workouts.allWorkouts.DefaultWorkout

@Dao

interface DefaultWorkoutDao {
    @Query("SELECT * FROM defaultWorkout")
    fun getAll(): MutableList<DefaultWorkout>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(items: List<DefaultWorkout>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOne(items: DefaultWorkout)

    //@Query("SELECT * FROM workout WHERE name LIKE :first AND " + "muscleGroup LIKE :last LIMIT 1")
    //fun findByName(first: String, last: Boolean): Workout

    @Delete
    fun delete(defaultWorkout: DefaultWorkout)

    @Query("DELETE FROM defaultWorkout WHERE id = :id")
    fun deleteById(id: Int)

}