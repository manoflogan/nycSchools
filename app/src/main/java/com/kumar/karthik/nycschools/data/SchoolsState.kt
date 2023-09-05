package com.kumar.karthik.nycschools.data

sealed interface SchoolsState {

    object LoadingState: SchoolsState

    data class ValidSchoolDataState(val schoolsRecords: List<SchoolsRecord>): SchoolsState

    object EmptySchoolDataState: SchoolsState

    object ErrorState: SchoolsState

    object UnknownState: SchoolsState
}