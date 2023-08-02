package com.moo.nycschools.util

import androidx.recyclerview.widget.DiffUtil
import com.moo.nycschools.model.HighSchool

class DiffUtil : DiffUtil.ItemCallback<HighSchool>() {
    override fun areItemsTheSame(oldItem: HighSchool, newItem: HighSchool): Boolean {
        return oldItem.dbn == newItem.dbn
    }

    override fun areContentsTheSame(oldItem: HighSchool, newItem: HighSchool): Boolean {
        return oldItem == newItem
    }
}