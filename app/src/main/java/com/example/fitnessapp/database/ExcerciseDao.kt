package com.example.fitnessapp.database

import androidx.room.*

@Dao

interface ExcerciseDao {
    @Query("SELECT * FROM excercise")
    fun getAll(): MutableList<Excercise>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(items: List<Excercise>)

    @Query("SELECT * FROM excercise WHERE id IN (:excerciseIds)")
    fun loadAllByIds(excerciseIds: IntArray): List<Excercise>

    @Query("SELECT * FROM excercise WHERE name LIKE :first AND " + "muscleGroup LIKE :last LIMIT 1")
    fun findByName(first: String, last: Boolean): Excercise

    @Delete
    fun delete(excercise: Excercise)

    @Query("DELETE FROM excercise WHERE id = :id")
    fun deleteById(id: Int)
}