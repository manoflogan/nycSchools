package com.kumar.karthik.nycschools.repository

import com.kumar.karthik.nycschools.data.SchoolsState
import kotlinx.coroutines.flow.Flow

/**
 * Repository implementation to fetch NYC Schools
 */
interface NycSchoolRepository {

    /**
     * Fetches the list of nyc schools records
     */
    suspend fun fetchAllNycSchools(): Flow<SchoolsState>
}