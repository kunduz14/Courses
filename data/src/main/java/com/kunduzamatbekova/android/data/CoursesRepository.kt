package com.kunduzamatbekova.android.data

import android.content.Context
import com.kunduzamatbekova.android.data.dao.CourseDao
import com.kunduzamatbekova.android.data.mapper.toCourse
import com.kunduzamatbekova.android.data.mapper.toEntity
import com.kunduzamatbekova.android.data.model.Course
import com.kunduzamatbekova.android.data.model.CoursesResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.json.Json

class CoursesRepository(
    private val context: Context,
    private val courseDao: CourseDao
) {
    fun getAllCourses(): Flow<List<Course>> =
        courseDao.getAll().map { list -> list.map { it.toCourse() } }

    fun getFavouriteCourses(): Flow<List<Course>> =
        courseDao.getFavourites().map { list -> list.map { it.toCourse() } }

    suspend fun setFavourite(courseId: Int, isFavourite: Boolean) {
        courseDao.setFavourite(courseId, isFavourite)
    }

    suspend fun initializeCoursesFromJson() {
        if (courseDao.count() == 0) {
            val jsonString = context.applicationContext.assets.open("courses.json").bufferedReader()
                .use { it.readText() }
            val coursesResponse = Json.decodeFromString<CoursesResponse>(jsonString)
            val entities = coursesResponse.courses.map { it.toEntity() }
            courseDao.insertAll(entities)
        }
    }
} 