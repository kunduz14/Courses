package com.kunduzamatbekova.android.core.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.kunduzamatbekova.android.core.R
import com.kunduzamatbekova.android.core.databinding.ItemCourseCardBinding
import com.kunduzamatbekova.android.core.util.formatDateToRussian
import com.kunduzamatbekova.android.core.util.getImageForCourse
import com.kunduzamatbekova.android.data.model.Course

class CoursesAdapter(
    private var courses: List<Course>,
    private val onFavouriteClick: ((Course, Int) -> Unit)? = null,
    private val onMoreClick: ((Course) -> Unit)? = null
) : RecyclerView.Adapter<CoursesAdapter.CoursesViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CoursesViewHolder {
        val binding =
            ItemCourseCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CoursesViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: CoursesViewHolder,
        position: Int
    ) {
        val course = courses[position]
        val imageRes = getImageForCourse(course.id)
        holder.binding.ivCourseImage.setImageResource(imageRes)
        holder.onBind(course)
    }

    override fun getItemCount(): Int {
        return courses.size
    }

    inner class CoursesViewHolder(val binding: ItemCourseCardBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun onBind(course: Course) {
            with(binding) {
                tvCourseTitle.text = course.title
                tvCourseDescription.text = course.text
                tvCoursePrice.text = binding.root.context.getString(R.string.price_with_ruble, course.price)
                tvRate.text = course.rate
                tvDate.text = formatDateToRussian(course.publishDate)

                if (course.hasLike) {
                    btnFavourite.setImageResource(
                        R.drawable.ic_favourite_filled
                    )
                } else {
                    btnFavourite.setImageResource(
                        R.drawable.ic_favourite_outline
                    )
                }

                btnFavourite.setOnClickListener {
                    onFavouriteClick?.invoke(course, adapterPosition)
                }

                tvMore.setOnClickListener {
                    onMoreClick?.invoke(course)
                }
            }
        }
    }

    fun submitList(newCourses: List<Course>) {
        val diffUtil = CourseDiffUtil(courses, newCourses)
        val diffResult = DiffUtil.calculateDiff(diffUtil)
        courses = newCourses
        diffResult.dispatchUpdatesTo(this)
    }

    fun updateFavouritesUI(favouriteCourses: List<Course>) {
        submitList(favouriteCourses)
    }
}