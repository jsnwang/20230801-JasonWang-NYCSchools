package com.moo.nycschools.module

import com.moo.nycschools.model.NYCSchoolsApi
import com.moo.nycschools.model.NYCSchoolsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object RepositoryModule {

    @Provides
    fun provideNYCSchoolsRepository(api: NYCSchoolsApi): NYCSchoolsRepository {
        return NYCSchoolsRepository(api)
    }
}
