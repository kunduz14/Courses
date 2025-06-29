package com.kunduzamatbekova.android.feature_favourites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kunduzamatbekova.android.data.model.Course
import com.kunduzamatbekova.android.data.CoursesRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class FavouritesCoursesViewModel(
    private val coursesRepository: CoursesRepository
) : ViewModel() {

    private val _favouritesCourses = MutableStateFlow<List<Course>>(emptyList())
    val favouritesCourses: StateFlow<List<Course>> = _favouritesCourses.asStateFlow()

    init {
        loadFavouritesCourses()
    }

    fun perform(event: FavouritesEvent) {
        when (event) {
            is FavouritesEvent.OnSetFavourite -> setFavourite(event.course, event.isFavourite)
        }
    }

    private fun loadFavouritesCourses() {
        viewModelScope.launch {
            coursesRepository.getFavouriteCourses().collect { favourites ->
                _favouritesCourses.value = favourites
            }
        }
    }

    private fun setFavourite(course: Course, isFavourite: Boolean) {
        viewModelScope.launch {
            coursesRepository.setFavourite(course.id, isFavourite)
        }
    }
}