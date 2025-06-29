package com.kunduzamatbekova.android.feature_favourites

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
import com.kunduzamatbekova.android.feature_favourites.databinding.FragmentFavouritesCoursesBinding
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavouritesCoursesFragment : Fragment() {

    private var _binding: FragmentFavouritesCoursesBinding? = null
    private val binding
        get() = _binding
            ?: throw IllegalStateException("Binding for FragmentFavouritesCoursesBinding most not be null")

    private val viewModel: FavouritesCoursesViewModel by viewModel()
    private lateinit var adapter: CoursesAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFavouritesCoursesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = CoursesAdapter(
            courses = emptyList(),
            onFavouriteClick = { course, _ ->
                viewModel.perform(
                    FavouritesEvent.OnSetFavourite(
                        course,
                        false
                    )
                )
            },
            onMoreClick = { course ->
                val fragment = CourseDetailFragment.Companion.newInstance(course)
                parentFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .addToBackStack(null)
                    .commit()
            }
        )

        binding.rvFavourites.adapter = adapter

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.favouritesCourses.collect { favourites ->
                    adapter.updateFavouritesUI(favourites)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}