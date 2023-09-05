package com.kumar.karthik.nycschools

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.kumar.karthik.nycschools.data.SchoolPerformanceRecordState
import com.kumar.karthik.nycschools.data.SchoolsRecord
import com.kumar.karthik.nycschools.data.SchoolsState
import com.kumar.karthik.nycschools.repository.NycSchoolRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.junit4.MockKRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.hamcrest.MatcherAssert
import org.hamcrest.Matchers
import org.junit.Rule
import org.junit.experimental.theories.DataPoints
import org.junit.experimental.theories.Theories
import org.junit.experimental.theories.Theory
import org.junit.runner.RunWith
import java.io.InputStreamReader

@RunWith(Theories::class)
class NycSchoolsViewModelTest {

    private lateinit var nycSchoolsViewModel: NycSchoolsViewModel

    @get:Rule
    val mockkRule = MockKRule(this)

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @MockK
    private lateinit var nycSchoolRepository: NycSchoolRepository

    @Theory
    fun `validate that when the repository returns a state, it is populated in the view model`(
        schoolsState: SchoolsState
    ) = runTest {
        coEvery {
            nycSchoolRepository.fetchAllNycSchools()
        } returns flowOf(schoolsState)
        nycSchoolsViewModel = NycSchoolsViewModel(nycSchoolRepository, testCoroutineRule)
        MatcherAssert.assertThat(
            nycSchoolsViewModel.schoolsStateFlow.value, Matchers.`is`(schoolsState)
        )
        coVerify {
            nycSchoolRepository.fetchAllNycSchools()
        }
    }

    @Theory
    fun `validate that that when school records are generated then valid school performance record is provided`(
        schoolsState: SchoolsState
    ) = runTest {
        coEvery {
            nycSchoolRepository.fetchAllNycSchools()
        } returns flowOf(schoolsState)
        nycSchoolsViewModel = NycSchoolsViewModel(nycSchoolRepository, testCoroutineRule).apply {
            fetchNycSchoolData(DBN)
        }
        MatcherAssert.assertThat(
            nycSchoolsViewModel.schoolRecordFlow.value,
            Matchers.`is`(
                if (schoolsState is SchoolsState.ValidSchoolDataState) {
                    SchoolPerformanceRecordState.SchoolPerformanceDataState(
                        schoolsState.schoolsRecords.last()
                    )
                } else {
                    SchoolPerformanceRecordState.MissingSchoolPerformanceRecordState
                }
            )
        )
        coVerify {
            nycSchoolRepository.fetchAllNycSchools()
        }
    }

    @Theory
    fun `validate that that when school records are generated for mismatched dbn then no data is returned`(
        schoolsState: SchoolsState
    ) = runTest {
        coEvery {
            nycSchoolRepository.fetchAllNycSchools()
        } returns flowOf(schoolsState)
        nycSchoolsViewModel = NycSchoolsViewModel(nycSchoolRepository, testCoroutineRule).apply {
            fetchNycSchoolData("foo")
        }
        MatcherAssert.assertThat(
            nycSchoolsViewModel.schoolRecordFlow.value,
            Matchers.`is`(
               SchoolPerformanceRecordState.MissingSchoolPerformanceRecordState
            )
        )
        coVerify {
            nycSchoolRepository.fetchAllNycSchools()
        }
    }

    companion object {
        private const val DBN = "01M450"
        private const val RESOURCE_FILE =
            "com/kumar/karthik/nycschools/repository/fake_repository_response.json"

        private fun parseSchoolRecords(): List<SchoolsRecord> {
            val schoolRecords: List<SchoolsRecord>
            ClassLoader.getSystemResourceAsStream(RESOURCE_FILE)!!.use {
                val listType = object: TypeToken<List<SchoolsRecord>>() {}.type
                schoolRecords = Gson().fromJson(InputStreamReader(it), listType);
            }
            return schoolRecords
        }

        @JvmField
        @DataPoints
        val schoolRecordStates = arrayOf(
            SchoolsState.LoadingState, SchoolsState.ValidSchoolDataState(parseSchoolRecords()),
            SchoolsState.EmptySchoolDataState, SchoolsState.UnknownState, SchoolsState.ErrorState
        )
    }
}