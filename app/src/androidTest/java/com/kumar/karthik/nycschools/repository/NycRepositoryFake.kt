package com.kumar.karthik.nycschools.repository

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.kumar.karthik.nycschools.data.SchoolsRecord
import com.kumar.karthik.nycschools.data.SchoolsState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.InputStreamReader

import javax.inject.Inject

class NycRepositoryFake @Inject constructor(
    @TestQualifier private val context: Context
): NycSchoolRepository {

    override suspend fun fetchAllNycSchools(): Flow<SchoolsState> = flow {
        emit(
            runCatching {
                val schoolRecords: List<SchoolsRecord>
                context.assets.open("com/kumar/karthik/nycschools/repository/fake_repository_response.json").use {
                    val listType = object: TypeToken<List<SchoolsRecord>>() {}.type
                    schoolRecords = Gson().fromJson(InputStreamReader(it), listType)
                }
                if (schoolRecords.isEmpty()) {
                    SchoolsState.EmptySchoolDataState
                } else {
                    SchoolsState.ValidSchoolDataState(schoolRecords)
                }
            }.getOrDefault(SchoolsState.EmptySchoolDataState)
        )
    }
}