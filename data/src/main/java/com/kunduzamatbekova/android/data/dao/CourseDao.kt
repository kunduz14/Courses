package com.kunduzamatbekova.android.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.kunduzamatbekova.android.data.model.CourseEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CourseDao {
    @Query("SELECT * FROM courses")
    fun getAll(): Flow<List<CourseEntity>>

    @Query("SELECT * FROM courses WHERE hasLike = 1")
    fun getFavourites(): Flow<List<CourseEntity>>

    @Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
    suspend fun insertAll(courses: List<CourseEntity>)

    @Query("UPDATE courses SET hasLike = :isFavourite WHERE id = :courseId")
    suspend fun setFavourite(courseId: Int, isFavourite: Boolean)

    @Query("SELECT COUNT(*) FROM courses")
    suspend fun count(): Int
}