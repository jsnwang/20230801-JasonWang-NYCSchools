package com.moo.nycschools.view

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.moo.nycschools.R
import com.moo.nycschools.databinding.FragmentListBinding
import com.moo.nycschools.util.Status
import com.moo.nycschools.viewmodel.NYCSchoolsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SchoolsFragment : Fragment(R.layout.fragment_list) {

    private val viewModel: NYCSchoolsViewModel by viewModels()
    private val binding: FragmentListBinding by viewBinding()
    private lateinit var schoolAdapter: SchoolAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getSchools()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        schoolAdapter = SchoolAdapter()
        setView()
        setObservers()
    }

    private fun setView() {
        binding.rvList.layoutManager = LinearLayoutManager(requireContext())
        binding.rvList.adapter = schoolAdapter
    }

    private fun setObservers() {
        viewModel.highSchoolsState.observe(viewLifecycleOwner) { highSchoolState ->
            when (highSchoolState.status) {
                Status.LOADING -> {
                    showProgressBar()
                    println("Fetching hs")
                }
                Status.SUCCESS -> {
                    hideProgressBar()
                    schoolAdapter.submitList(highSchoolState.data)
                    println("Fetch highschools success")
                }
                Status.ERROR -> {
                    hideProgressBar()
                    Log.d("FETCH ERROR", highSchoolState.error?.message.toString())
                }
            }
        }
    }

    private fun showProgressBar() {
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        binding.progressBar.visibility = View.GONE
    }


}