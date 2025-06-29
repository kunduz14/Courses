package com.kunduzamatbekova.android.core.util

import com.kunduzamatbekova.android.core.R

val images = listOf(
    R.drawable.img1,
    R.drawable.img2,
    R.drawable.img3,
    R.drawable.img4,
    R.drawable.img5
)

fun getImageForCourse(courseId: Int): Int {
    return images[courseId % images.size]
}