package com.kumar.karthik.nycschools.util

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
interface CoroutineDispatchersModule {

    @Binds
    fun bindCoroutineDispatchers(instance: CoroutineDispatchersImpl): CoroutineDispatchers

}