package com.kumar.karthik.nycschools.repository

import com.kumar.karthik.nycschools.BuildConfig
import com.kumar.karthik.nycschools.data.SchoolsState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class NycSchoolRepositoryImpl @Inject constructor(
    private val nycSchoolsService: NycSchoolsService
): NycSchoolRepository {

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
               response.isSuccessful && responseBody != null -> {
                   SchoolsState.ValidSchoolDataState(responseBody)
               }
                response.isSuccessful -> {
                    SchoolsState.EmptySchoolDateState
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