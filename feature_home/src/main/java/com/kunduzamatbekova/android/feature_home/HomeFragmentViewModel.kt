package com.kunduzamatbekova.android.feature_home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kunduzamatbekova.android.data.model.Course
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeFragmentViewModel(
    private val coursesRepository: com.kunduzamatbekova.android.data.CoursesRepository
) : ViewModel() {

    private val _courses =
        MutableStateFlow<List<Course>>(emptyList())
    val courses: StateFlow<List<Course>> = _courses.asStateFlow()

    private var isSort = true

    init {
        observeCourses()
    }

    fun perform(event: HomeEvent) {
        when (event) {
            is HomeEvent.OnClickFavourite -> onFavouriteClick(event.course)
            HomeEvent.OnClickSort -> sortByPublishDate()
        }
    }

    private fun observeCourses() {
        viewModelScope.launch {
            coursesRepository.initializeCoursesFromJson()
            coursesRepository.getAllCourses().collect { loadedCourses ->
                _courses.value = loadedCourses
            }
        }
    }

    private fun sortByPublishDate() {
        _courses.value = if (isSort) {
            _courses.value.sortedByDescending { it.publishDate }
        } else {
            _courses.value.sortedBy { it.publishDate }
        }
        isSort = !isSort
    }

    private fun onFavouriteClick(course: Course) {
        viewModelScope.launch {
            coursesRepository.setFavourite(course.id, !course.hasLike)
        }
    }
}