package com.moo.nycschools.view

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.moo.nycschools.R
import com.moo.nycschools.databinding.FragmentListBinding
import com.moo.nycschools.model.HighSchool
import com.moo.nycschools.util.Status
import com.moo.nycschools.viewmodel.NYCSchoolsViewModel
import dagger.hilt.android.AndroidEntryPoint
import retrofit2.HttpException
import java.io.IOException

@AndroidEntryPoint
class SchoolsFragment : Fragment(R.layout.fragment_list) {

    private val viewModel: NYCSchoolsViewModel by viewModels()
    private val binding: FragmentListBinding by viewBinding()
    private lateinit var schoolAdapter: SchoolAdapter

    private var schoolList: List<HighSchool>? = null


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

        schoolAdapter.setOnItemClickListener(object : SchoolAdapter.OnItemClickListener {
            override fun onItemClick(school: HighSchool) {
                navigateToDetails(school)
                binding.etSearch.text.clear()
            }
        })
    }

    private fun setObservers() {
        //Observes the getHighSchools api call and does something based on its result
        viewModel.highSchoolsState.observe(viewLifecycleOwner) { highSchoolState ->
            when (highSchoolState.status) {
                Status.LOADING -> {
                    showProgressBar()
                    println("LOADING")
                }

                Status.SUCCESS -> {
                    hideProgressBar()
                    onGetSchoolsSuccess(highSchoolState.data)
                }

                Status.ERROR -> {
                    hideProgressBar()
                    onGetSchoolsError(highSchoolState.error)
                    Log.d("FETCH ERROR", highSchoolState.error?.message.toString())
                }
            }
        }
    }

    private fun onGetSchoolsSuccess(schools: List<HighSchool>?) {
        schoolAdapter.submitList(schools)
        schoolList = schools
        setSearchBar()
        binding.rvList.visibility = View.VISIBLE
        binding.llError.visibility = View.GONE
    }

    private fun setSearchBar() {
        binding.etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Filter the list based on the entered text
                val searchText = s.toString().trim()
                filterCharacterList(searchText)
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })
    }

    private fun onGetSchoolsError(error: Throwable?) {
        when (error) {
            is IOException -> binding.tvError.text = getString(R.string.io_error)
            is HttpException -> binding.tvError.text = getString(R.string.http_error)
            else -> binding.tvError.text = getString(R.string.generic_error)
        }
        binding.rvList.visibility = View.INVISIBLE
        binding.llError.visibility = View.VISIBLE
        setRetryButtonListener()

    }

    private fun setRetryButtonListener() {
        binding.btRetry.setOnClickListener {
            viewModel.getSchools()
        }
    }

    //to reduce redundancy, showProgressBar() and hideProgressBar() should be in some Base class that we inherit from
    private fun showProgressBar() {
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        binding.progressBar.visibility = View.GONE
    }

    private fun filterCharacterList(searchText: String) {
        val filteredList = if (searchText.isBlank()) {
            schoolList
        } else {
            schoolList?.filter { school ->
                //Searches name and location for queried text
                school.schoolName.contains(
                    searchText,
                    ignoreCase = true
                ) || school.location.contains(searchText, ignoreCase = true)
            }
        }
        schoolAdapter.submitList(filteredList)
    }

    //navigate using safeargs
    private fun navigateToDetails(school: HighSchool) {
        val action = SchoolsFragmentDirections.actionSchoolsFragmentToDetailsFragment(school)
        findNavController().navigate(action)
    }
}
