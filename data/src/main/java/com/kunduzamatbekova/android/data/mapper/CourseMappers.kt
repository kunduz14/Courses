package com.kunduzamatbekova.android.data.mapper

import com.kunduzamatbekova.android.data.model.Course
import com.kunduzamatbekova.android.data.model.CourseEntity

fun CourseEntity.toCourse() = Course(
    id = id,
    title = title,
    text = text,
    price = price,
    rate = rate,
    startDate = startDate,
    hasLike = hasLike,
    publishDate = publishDate
)

fun Course.toEntity() = CourseEntity(
    id = id,
    title = title,
    text = text,
    price = price,
    rate = rate,
    startDate = startDate,
    hasLike = hasLike,
    publishDate = publishDate
)