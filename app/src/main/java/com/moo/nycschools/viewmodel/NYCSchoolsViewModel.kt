package com.moo.nycschools.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moo.nycschools.model.HighSchool
import com.moo.nycschools.model.NYCSchoolsRepository
import com.moo.nycschools.model.SATScores
import com.moo.nycschools.util.Status
import com.moo.nycschools.util.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NYCSchoolsViewModel @Inject constructor(private val repo: NYCSchoolsRepository): ViewModel() {
    private val _highSchoolsState = MutableLiveData<UiState<List<HighSchool>>>()
    val highSchoolsState: LiveData<UiState<List<HighSchool>>> = _highSchoolsState

    private val _SATScoresState = MutableLiveData<UiState<List<SATScores>>>()
    val SATScoresState: LiveData<UiState<List<SATScores>>> = _SATScoresState


    fun getSchools() = viewModelScope.launch {
        _highSchoolsState.value = UiState(status = Status.LOADING)
        try {
            val response = repo.getHighSchools()
            _highSchoolsState.value = UiState(status = Status.SUCCESS, data = response)
        } catch (e: Exception) {
            _highSchoolsState.value = UiState(status = Status.ERROR, error = e)
        }
    }

    fun getScores(dbn: String) = viewModelScope.launch {
        _SATScoresState.value = UiState(status = Status.LOADING)
        try {
            val response = repo.getSATScores(dbn)
            _SATScoresState.value = UiState(status = Status.SUCCESS, data = response)
        } catch (e: Exception) {
            _SATScoresState.value = UiState(status = Status.ERROR, error = e)
        }
    }
}
