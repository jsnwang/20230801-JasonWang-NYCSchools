package com.moo.nycschools.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.moo.nycschools.databinding.ListItemBinding
import com.moo.nycschools.model.HighSchool
import com.moo.nycschools.util.DiffUtil


class SchoolAdapter : ListAdapter<HighSchool, SchoolAdapter.SchoolViewHolder>(DiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SchoolViewHolder {
        val binding = ListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SchoolViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SchoolViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    private var listener: OnItemClickListener? = null

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    interface OnItemClickListener {
        fun onItemClick(school: HighSchool)
    }

    inner class SchoolViewHolder(private val binding: ListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(school: HighSchool) {
            binding.cvSchoolItem.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    listener?.onItemClick(getItem(position))
                }
            }

            binding.tvName.text = school.schoolName
            binding.tvAddress.text = school.primaryAddressLine
            binding.tvCity.text =
                "${school.city}, ${school.stateCode} ${school.zip}" //this should be a string resource

        }
    }
}