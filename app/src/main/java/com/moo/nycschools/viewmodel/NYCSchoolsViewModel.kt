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
class NYCSchoolsViewModel @Inject constructor(private val repo: NYCSchoolsRepository) :
    ViewModel() {

    //Used LiveData over StateFlow/Flow because of familiarity
    private val _highSchoolsState = MutableLiveData<UiState<List<HighSchool>>>()
    val highSchoolsState: LiveData<UiState<List<HighSchool>>> = _highSchoolsState

    private val _satScoresState = MutableLiveData<UiState<List<SATScores>>>()
    val satScoresState: LiveData<UiState<List<SATScores>>> = _satScoresState


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
        _satScoresState.value = UiState(status = Status.LOADING)
        try {
            val response = repo.getSATScores(dbn)
            _satScoresState.value = UiState(status = Status.SUCCESS, data = response)
        } catch (e: Exception) {
            _satScoresState.value = UiState(status = Status.ERROR, error = e)
        }
    }
}
