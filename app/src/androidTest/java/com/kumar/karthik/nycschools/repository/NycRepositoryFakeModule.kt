package com.kumar.karthik.nycschools.repository

import android.content.Context
import androidx.test.platform.app.InstrumentationRegistry
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.testing.TestInstallIn

@Module
@TestInstallIn(
    components = [ViewModelComponent::class],
    replaces = [NycRepositoryModule::class]
)
interface NycRepositoryFakeModule {

    @Binds
    fun bindNycRepository(instance: NycRepositoryFake): NycSchoolRepository

    companion object {
        @[Provides TestQualifier]
        fun provideTestContext() = InstrumentationRegistry.getInstrumentation().context
    }
}