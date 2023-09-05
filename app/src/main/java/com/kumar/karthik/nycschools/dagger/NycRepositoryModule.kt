package com.kumar.karthik.nycschools.dagger

import com.kumar.karthik.nycschools.repository.NycSchoolRepository
import com.kumar.karthik.nycschools.repository.NycSchoolRepositoryImpl
import com.kumar.karthik.nycschools.repository.NycSchoolsService
import com.kumar.karthik.nycschools.util.CoroutineDispatchers
import com.kumar.karthik.nycschools.util.CoroutineDispatchersImpl
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

    @Binds
    fun bindCoroutineDispatchers(instance: CoroutineDispatchersImpl): CoroutineDispatchers

    companion object {
        @Provides
        fun provideNycService(retrofit: Retrofit) = retrofit.create(NycSchoolsService::class.java)
    }
}