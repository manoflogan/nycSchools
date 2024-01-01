package com.kumar.karthik.nycschools.repository

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import retrofit2.Retrofit

/**
 * Repository bindings used to fetch school data
 */
@Module
@InstallIn(ViewModelComponent::class)
interface NycRepositoryModule {

    @Binds
    fun bindNycSchoolRepository(instance: NycSchoolRepositoryImpl): NycSchoolRepository

    companion object {
        @Provides
        fun provideNycService(retrofit: Retrofit) = retrofit.create(NycSchoolsService::class.java)
    }
}