package com.kumar.karthik.nycschools.repository

import com.kumar.karthik.nycschools.BuildConfig
import com.kumar.karthik.nycschools.data.SchoolsState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * Implementation to fetch the list of school records
 */
class NycSchoolRepositoryImpl @Inject constructor(
    private val nycSchoolsService: NycSchoolsService
): NycSchoolRepository {

    /**
     * Returns a list of 50 nyc schools.
     *
     * The implementation of this class passes the authentication token associated and a limit of 50
     * records
     */
    override suspend fun fetchAllNycSchools(): Flow<SchoolsState> = flow {
        val response = nycSchoolsService.fetchAllSchools(
            mapOf(
                "X-App-Token" to BuildConfig.API_KEY
            ),
            mapOf(
                "\$limit" to 50.toString()
            )
        ).execute()
        val responseBody = response.body().takeUnless {
            it.isNullOrEmpty()
        }
        emit(
            when {
                response.isSuccessful && !responseBody.isNullOrEmpty() -> {
                    SchoolsState.ValidSchoolDataState(responseBody)
                }
                response.isSuccessful  -> {
                    SchoolsState.EmptySchoolDataState
                }
                !response.isSuccessful -> {
                    SchoolsState.ErrorState
                }
                else -> {
                    SchoolsState.UnknownState
                }
            }
        )
    }
}