package com.moo.nycschools.model

import com.moo.nycschools.util.Resource
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import java.lang.Exception
import javax.inject.Inject

class NYCSchoolsRepository @Inject constructor(private val api: NYCSchoolsApi) {
    suspend fun getHighSchools(): Resource<List<HighSchool>> {
        return try {
            Resource.Success(data = api.getHighSchools())
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e.message ?: "An unknown error has occured.")
        }
    }

    suspend fun getSATScores(dbn: String): Resource<List<SATScores>> {
        return try {
            Resource.Success(data = api.getSATScores(dbn))
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e.message ?: "An unknown error has occured.")
        }
    }
}