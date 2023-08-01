package com.moo.nycschools.viewmodel

import com.moo.nycschools.model.NYCSchoolsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NYCSchoolsViewModel @Inject constructor(private val repo: NYCSchoolsRepository) {
}