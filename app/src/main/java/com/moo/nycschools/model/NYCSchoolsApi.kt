package com.moo.nycschools.model

import retrofit2.http.GET
import retrofit2.http.Query

interface NYCSchoolsApi {

    @GET("resource/s3k6-pzi2.json")
    suspend fun getHighSchools(
    ): List<HighSchool>

    @GET("resource/f9bf-2cp4.json")
    suspend fun getSATScores(
        @Query("dbn") schoolDbn: String,
    ): List<SATScores>
}