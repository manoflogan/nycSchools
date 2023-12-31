package com.kumar.karthik.nycschools

import com.kumar.karthik.nycschools.util.CoroutineDispatchers
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.rules.ExternalResource

@OptIn(ExperimentalCoroutinesApi::class)
class TestCoroutineRule(
    val testDispatcher: CoroutineDispatcher = UnconfinedTestDispatcher()
): CoroutineDispatchers, ExternalResource() {

    override val main: CoroutineDispatcher = testDispatcher
    override val default: CoroutineDispatcher = testDispatcher
    override val io: CoroutineDispatcher = testDispatcher

    override fun before() {
        Dispatchers.setMain(testDispatcher)
    }

    override fun after() {
        Dispatchers.resetMain()
    }
}