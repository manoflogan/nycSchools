package com.kumar.karthik.nycschools.repository

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.kumar.karthik.nycschools.TestCoroutineRule
import com.kumar.karthik.nycschools.data.SchoolsRecord
import com.kumar.karthik.nycschools.data.SchoolsState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.Buffer
import org.hamcrest.MatcherAssert
import org.hamcrest.Matchers
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.InputStreamReader
import java.util.concurrent.TimeUnit

@OptIn(ExperimentalCoroutinesApi::class)
class NycSchoolRepositoryTest {

    private lateinit var nycSchoolRepository: NycSchoolRepositoryImpl

    private lateinit var nycSchoolsService: NycSchoolsService

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @get:Rule
    val mockWebServer = MockWebServer()

    @Before
    fun setUp() {
        val client = OkHttpClient.Builder()
            .connectTimeout(1, TimeUnit.SECONDS)
            .readTimeout(1, TimeUnit.SECONDS)
            .writeTimeout(1, TimeUnit.SECONDS)
            .build()
        nycSchoolsService = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build()
            .create(NycSchoolsService::class.java)
        nycSchoolRepository = NycSchoolRepositoryImpl(nycSchoolsService)
    }

    @Test
    fun `validate that mocked response is returned when a valid response simullated`() = runTest {
        ClassLoader.getSystemResourceAsStream(RESOURCE_FILE)!!.use {
            mockWebServer.enqueue(
                MockResponse().setResponseCode(200).setBody(
                    Buffer().readFrom(it)
                )
            )
        }
        val schoolRecords: List<SchoolsRecord>
        ClassLoader.getSystemResourceAsStream(RESOURCE_FILE)!!.use {
            val listType = object: TypeToken<List<SchoolsRecord>>() {}.type
            schoolRecords = Gson().fromJson(InputStreamReader(it), listType);
        }
        launch(UnconfinedTestDispatcher()) {
            nycSchoolRepository.fetchAllNycSchools().take(2).collect {
                MatcherAssert.assertThat(
                    it, Matchers.instanceOf(SchoolsState.ValidSchoolDataState::class.java)
                )
                MatcherAssert.assertThat(
                    (it as SchoolsState.ValidSchoolDataState).schoolsRecords, Matchers.`is`(schoolRecords)
                )
            }
        }
    }

    @Test
    fun `validate that when an empty response is returned then a correct response state is returned`() = runTest {
        mockWebServer.enqueue(
            MockResponse().setResponseCode(200).setBody(
                Buffer().readFrom(
                    "[]".byteInputStream()
                )
            )
        )
        launch(UnconfinedTestDispatcher()) {
            nycSchoolRepository.fetchAllNycSchools().take(2).collect {
                MatcherAssert.assertThat(
                    it, Matchers.`is`(SchoolsState.EmptySchoolDataState)
                )
            }
        }
    }

    @Test
    fun `validate that when an error response is returned then a correct response state is returned`() = runTest {
        mockWebServer.enqueue(
            MockResponse().setResponseCode(500)
        )
        launch(UnconfinedTestDispatcher()) {
            nycSchoolRepository.fetchAllNycSchools().take(2).collect {
                MatcherAssert.assertThat(
                    it, Matchers.`is`(SchoolsState.ErrorState)
                )
            }
        }
    }

    companion object {
        private const val RESOURCE_FILE = "com/kumar/karthik/nycschools/repository/fake_repository_response.json"
    }
}