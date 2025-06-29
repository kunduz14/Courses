package com.kunduzamatbekova.android.feature_home

import com.kunduzamatbekova.android.data.model.Course

sealed interface HomeEvent {
    object OnClickSort : HomeEvent
    data class OnClickFavourite(val course: Course) : HomeEvent
}