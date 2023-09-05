package com.kumar.karthik.nycschools.data

/**
 * State to represent the fetching the school records..
 */
sealed interface SchoolPerformanceRecordState {

    /**
     * Indicates that a valid data record was fetched with a valid school record.
     */
    data class SchoolPerformanceDataState(val schoolsRecord: SchoolsRecord): SchoolPerformanceRecordState

    /**
     * Indicates that there was no school record found.
     */
    object MissingSchoolPerformanceRecordState: SchoolPerformanceRecordState

    /**
     * Indicates that the schooll record is being fetched.
     */
    object LoadingState: SchoolPerformanceRecordState
}