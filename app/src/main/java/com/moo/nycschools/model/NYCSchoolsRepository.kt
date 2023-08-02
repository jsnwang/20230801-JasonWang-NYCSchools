package com.moo.nycschools.model

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException
import javax.inject.Inject

class NYCSchoolsRepository @Inject constructor(private val api: NYCSchoolsApi) {
    suspend fun getHighSchools(): List<HighSchool> = withContext(Dispatchers.IO) {
        try {
            val response = api.getHighSchools()
            response
        } catch (e: IOException) {
            throw IOException("Error fetching HighSchools data", e)
        }
    }

    //Queries the SATScores api using the unique dbn of the school
    suspend fun getSATScores(schoolDbn: String): List<SATScores> = withContext(Dispatchers.IO) {
        try {
            val response = api.getSATScores(schoolDbn)
            response
        } catch (e: IOException) {
            throw IOException("Error fetching SATScores data", e)
        }
    }
}