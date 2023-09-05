package com.kumar.karthik.nycschools.util

import kotlinx.coroutines.CoroutineDispatcher

/**
 * Wrapper around dispatchers.
 */
interface CoroutineDispatchers {

    /**
     * Returns the main dispatcher
     */
    val main: CoroutineDispatcher

    /**
     * Return the default dispatcher
     */
    val default: CoroutineDispatcher

    /**
     * Returns the io context
     */
    val io: CoroutineDispatcher
}