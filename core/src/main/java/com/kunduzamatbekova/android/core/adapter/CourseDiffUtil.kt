package com.kunduzamatbekova.android.core.adapter

import androidx.recyclerview.widget.DiffUtil
import com.kunduzamatbekova.android.data.model.Course

class CourseDiffUtil(
    private val oldList: List<Course>,
    private val newList: List<Course>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(
        oldItemPosition: Int,
        newItemPosition: Int
    ): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun areContentsTheSame(
        oldItemPosition: Int,
        newItemPosition: Int
    ): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}