package com.kumar.karthik.nycschools.data

/**
 * Indicates the states involved in fetching the school records
 */
sealed interface SchoolsState {

    /**
     * Indicates that the school records are still being fetched
     */
    object LoadingState: SchoolsState

    /**
     * Indicates that the school records have been fetched, and initialised
     */
    data class ValidSchoolDataState(val schoolsRecords: List<SchoolsRecord>): SchoolsState

    /**
     * Indicates that no school records were fetched
     */
    object EmptySchoolDataState: SchoolsState

    /**
     * Indicates that fetching school records returned an error
     */
    object ErrorState: SchoolsState

    /**
     * Indicates that the act to fetch school records is undeterminate
     */
    object UnknownState: SchoolsState
}