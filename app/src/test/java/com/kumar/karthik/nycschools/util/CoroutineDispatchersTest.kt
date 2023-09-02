package com.kumar.karthik.nycschools.util

import kotlinx.coroutines.Dispatchers
import org.hamcrest.MatcherAssert
import org.hamcrest.Matchers
import org.junit.Before
import org.junit.Test

class CoroutineDispatchersTest {

    private lateinit var coroutineDispatchers: CoroutineDispatchersImpl

    @Before
    fun setUp() {
        coroutineDispatchers = CoroutineDispatchersImpl()
    }

    @Test
    fun `verify that coroutines are not null`() {
        with(coroutineDispatchers) {
            MatcherAssert.assertThat(main, Matchers.`is`(Dispatchers.Main))
            MatcherAssert.assertThat(default, Matchers.`is`(Dispatchers.Default))
            MatcherAssert.assertThat(io, Matchers.`is`(Dispatchers.IO))
        }
    }
}