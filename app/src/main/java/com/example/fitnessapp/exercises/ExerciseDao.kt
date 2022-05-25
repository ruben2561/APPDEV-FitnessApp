package com.example.fitnessapp.exercises

import androidx.room.*

@Dao

interface ExerciseDao {
    @Query("SELECT * FROM exercise")
    fun getAll(): MutableList<Exercise>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(items: List<Exercise>)

    @Query("SELECT * FROM exercise WHERE id IN (:exerciseIds)")
    fun loadAllByIds(exerciseIds: IntArray): List<Exercise>

    @Query("SELECT * FROM exercise WHERE id IN (:exerciseId)")
    fun loadByIds(exerciseId: Int): Exercise

    @Query("SELECT * FROM exercise WHERE name LIKE :first")
    fun findByName(first: String): Exercise

    @Delete
    fun delete(exercise: Exercise)

    @Query("DELETE FROM exercise WHERE id = :id")
    fun deleteById(id: Int)
}