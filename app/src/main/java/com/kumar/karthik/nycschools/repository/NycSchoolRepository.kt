package com.kumar.karthik.nycschools.repository

import com.kumar.karthik.nycschools.data.SchoolPerformanceRecord
import com.kumar.karthik.nycschools.data.SchoolPerformanceRecordState
import com.kumar.karthik.nycschools.data.SchoolsRecord
import com.kumar.karthik.nycschools.data.SchoolsState
import kotlinx.coroutines.flow.Flow

/**
 * Repository implementation to fetch NYC Schools
 */
interface NycSchoolRepository {

    suspend fun fetchAllNycSchools(): Flow<SchoolsState>

    suspend fun fetchNycSchoolByDbn(dbn: String): Flow<SchoolPerformanceRecordState>
}