package com.kumar.karthik.nycschools.repository

import dagger.Binds
import dagger.Module
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn

/**
 * Initialises fake bindings.
 */
@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [NycRepositoryModule::class]
)
interface NycRepositoryFakeModule {

    @Binds
    fun bindNycRepository(instance: NycRepositoryFake): NycSchoolRepository
}