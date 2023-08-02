package com.moo.nycschools.view

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.moo.nycschools.R
import com.moo.nycschools.databinding.FragmentDetailsBinding
import com.moo.nycschools.model.SATScores
import com.moo.nycschools.util.Status
import com.moo.nycschools.viewmodel.NYCSchoolsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsFragment : Fragment(R.layout.fragment_details) {
    private val binding: FragmentDetailsBinding by viewBinding()
    private val viewModel: NYCSchoolsViewModel by viewModels()
    private val args: DetailsFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getScores(args.school.dbn)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setView()
        setObservers()
    }

    private fun setView() {
        binding.tvName.text = args.school.schoolName
        binding.tvDesc.text = args.school.overviewParagraph
        binding.tvAddress.text = args.school.location.substringBeforeLast(" (")
    }

    private fun setObservers() {
        viewModel.satScoresState.observe(viewLifecycleOwner) { satScoresState ->
            when (satScoresState.status) {
                Status.LOADING -> {
                    showProgressBar()
                }

                Status.SUCCESS -> {
                    hideProgressBar()
                    setSATScores(satScoresState.data ?: emptyList())
                    Log.d("FETCH SCORES SUCCESS", satScoresState.data.toString())
                }

                Status.ERROR -> {
                    hideProgressBar()
                    Log.d("FETCH SCORES ERROR", satScoresState.error?.message.toString())
                }
            }
        }
    }

    private fun setSATScores(scores: List<SATScores>) {
        if (scores.isEmpty()) {
            binding.tvScores.text = getString(R.string.no_data_found)
        } else {
            val score = scores[0]
            binding.tvScores.text = getString(
                R.string.reading_math_writing,
                score.satCriticalReadingAvgScore,
                score.satMathAvgScore,
                score.satWritingAvgScore
            )
        }

    }

    //to reduce redundancy, showProgressBar() and hideProgressBar() should be in some Base class that we inherit from
    private fun showProgressBar() {
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        binding.progressBar.visibility = View.GONE
    }
}