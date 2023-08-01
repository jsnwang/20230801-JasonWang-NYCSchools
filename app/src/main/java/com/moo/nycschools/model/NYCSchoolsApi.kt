package com.moo.nycschools.model

import retrofit2.http.GET
import retrofit2.http.Path

interface NYCSchoolsApi {

    @GET("resource/s3k6-pzi2.json")
    suspend fun getHighSchools(
    ): List<HighSchool>

    @GET("resource/f9bf-2cp4.json/{dbn}")
    suspend fun getSATScores(
        @Path("dbn") schoolDbn: String
    ): List<SATScores>
}