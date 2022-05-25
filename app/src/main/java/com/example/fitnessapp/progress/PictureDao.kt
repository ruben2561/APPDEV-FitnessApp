package com.example.fitnessapp.progress

import android.graphics.Bitmap
import androidx.room.*
import java.util.*

@Dao
interface PictureDao {
    @Query("SELECT * FROM picture")
    fun getAll(): MutableList<Picture>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(items: Picture)

    @Query("SELECT * FROM picture WHERE id IN (:pictureIds)")
    fun loadAllByIds(pictureIds: IntArray): List<Picture>

    @Query("SELECT * FROM picture WHERE id IN (:pictureId)")
    fun loadByIds(pictureId: Int): Picture

    @Query("SELECT * FROM picture WHERE name LIKE :first")
    fun findByDate(first: String): Picture

    @Delete
    fun delete(picture: Picture)

    @Query("DELETE FROM picture WHERE id = :id")
    fun deleteById(id: Int)


}