package com.kunduzamatbekova.android.data

import androidx.room.RoomDatabase
import com.kunduzamatbekova.android.data.dao.CourseDao
import com.kunduzamatbekova.android.data.model.CourseEntity

@androidx.room.Database(entities = [CourseEntity::class], version = 1, exportSchema = false)
abstract class CoursesDatabase : RoomDatabase() {
    abstract fun courseDao(): CourseDao
}