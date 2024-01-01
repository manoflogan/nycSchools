package com.kumar.karthik.nycschools.repository

import com.kumar.karthik.nycschools.data.SchoolsState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

import javax.inject.Inject
import javax.inject.Singleton

/**
 * Test implementation that returns the preinitialised response instead of making network calls.
 */
@Singleton
class NycRepositoryFake @Inject constructor(): NycSchoolRepository {

    private var _schoolsState: MutableStateFlow<SchoolsState> = MutableStateFlow(SchoolsState.LoadingState)
    var schoolsState: StateFlow<SchoolsState> = _schoolsState.asStateFlow()

    fun initialiseSchoolState(schoolsState: SchoolsState) {
        _schoolsState.update {
            schoolsState
        }
    }

    override suspend fun fetchAllNycSchools(): Flow<SchoolsState> = schoolsState
}