package com.example.fitnessapp.databaseExcercises

import androidx.room.*

@Dao

interface ExcerciseDao {
    @Query("SELECT * FROM excercise")
    fun getAll(): MutableList<Excercise>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(items: List<Excercise>)

    @Query("SELECT * FROM excercise WHERE id IN (:excerciseIds)")
    fun loadAllByIds(excerciseIds: IntArray): List<Excercise>

    @Query("SELECT * FROM excercise WHERE id IN (:excerciseId)")
    fun loadByIds(excerciseId: Int): Excercise

    @Query("SELECT * FROM excercise WHERE name LIKE :first")
    fun findByName(first: String): Excercise

    @Delete
    fun delete(excercise: Excercise)

    @Query("DELETE FROM excercise WHERE id = :id")
    fun deleteById(id: Int)
}