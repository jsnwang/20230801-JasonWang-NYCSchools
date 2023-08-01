package com.moo.nycschools.module

import com.moo.nycschools.model.NYCSchoolsApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    private const val BASE_URL = "https://frog-api.glitch.me"

    @Provides
    @Singleton
    fun provideRetrofit(): NYCSchoolsApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NYCSchoolsApi::class.java)
    }
}