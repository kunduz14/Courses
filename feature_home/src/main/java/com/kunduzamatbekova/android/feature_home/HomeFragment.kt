package com.kunduzamatbekova.android.feature_home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.kunduzamatbekova.android.core.R
import com.kunduzamatbekova.android.core.adapter.CoursesAdapter
import com.kunduzamatbekova.android.feature_course_detail.CourseDetailFragment
import com.kunduzamatbekova.android.feature_home.databinding.FragmentHomeBinding
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding
        get() = _binding
            ?: throw IllegalStateException("Binding for FragmentHomeBinding most not be null")

    private val viewModel: HomeFragmentViewModel by viewModel()
    private lateinit var adapter: CoursesAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = CoursesAdapter(
            courses = emptyList(),
            onFavouriteClick = { course, _ ->
                viewModel.perform(HomeEvent.OnClickFavourite(course))
            },
            onMoreClick = { course ->
                val fragment = CourseDetailFragment.Companion.newInstance(course)
                parentFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .addToBackStack(null)
                    .commit()
            }
        )

        binding.rvCourses.adapter = adapter

        binding.tvSort.setOnClickListener {
            viewModel.perform(HomeEvent.OnClickSort)
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.courses.collect { courses ->
                    adapter.submitList(courses)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}