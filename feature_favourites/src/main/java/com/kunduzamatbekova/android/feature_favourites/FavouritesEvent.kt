package com.kunduzamatbekova.android.feature_favourites

import com.kunduzamatbekova.android.data.model.Course

sealed interface FavouritesEvent {
    data class OnSetFavourite(val course: Course, val isFavourite: Boolean) : FavouritesEvent
}