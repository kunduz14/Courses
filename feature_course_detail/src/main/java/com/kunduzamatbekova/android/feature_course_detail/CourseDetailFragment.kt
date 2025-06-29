package com.kunduzamatbekova.android.feature_course_detail

import android.content.res.ColorStateList
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.kunduzamatbekova.android.core.R
import com.kunduzamatbekova.android.core.util.formatDateToRussian
import com.kunduzamatbekova.android.core.util.getImageForCourse
import com.kunduzamatbekova.android.data.model.Course
import com.kunduzamatbekova.android.data.CoursesRepository
import com.kunduzamatbekova.android.feature_course_detail.databinding.FragmentCourseDetailBinding
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class CourseDetailFragment : Fragment() {

    private var _binding: FragmentCourseDetailBinding? = null
    private val binding
        get() = _binding
            ?: throw IllegalStateException("Binding for FragmentCourseDetailBinding most not be null")

    private var course: Course? = null
    private val coursesRepository: CoursesRepository by inject()

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        course = arguments?.getSerializable(ARG_COURSE, Course::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCourseDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        course?.let { course ->
            with(binding) {
                tvCourseTitle.text = course.title
                tvCourseDescription.text = course.text
                tvRate.text = course.rate
                tvDate.text = formatDateToRussian(course.publishDate)

                val imageRes = getImageForCourse(course.id)
                ivCourseImage.setImageResource(imageRes)
            }

            updateFavouriteIcon(course)

            binding.btnBack.setOnClickListener {
                parentFragmentManager.popBackStack()
            }

            binding.btnFavourite.setOnClickListener {
                toggleFavourite(course)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun updateFavouriteIcon(course: Course) {
        val isFavourite = course.hasLike

        val iconRes = if (isFavourite) {
            R.drawable.ic_favourite_filled
        } else {
            R.drawable.ic_favourite_outline
        }

        binding.btnFavourite.setImageResource(iconRes)

        if (isFavourite) {
            binding.btnFavourite.imageTintList = ColorStateList.valueOf(
                requireContext().getColor(R.color.green)
            )
        } else {
            binding.btnFavourite.imageTintList = ColorStateList.valueOf(
                requireContext().getColor(R.color.dark)
            )
        }
    }

    private fun toggleFavourite(course: Course) {
        val newState = !course.hasLike
        lifecycleScope.launch {
            coursesRepository.setFavourite(course.id, newState)

            val updatedCourse = course.copy(hasLike = newState)
            updateFavouriteIcon(updatedCourse)

            this@CourseDetailFragment.course = updatedCourse
        }
    }

    companion object {
        private const val ARG_COURSE = "arg_course"

        fun newInstance(course: Course): CourseDetailFragment {
            val args = Bundle().apply {
                putSerializable(ARG_COURSE, course)
            }
            return CourseDetailFragment().apply {
                arguments = args
            }
        }
    }
}
