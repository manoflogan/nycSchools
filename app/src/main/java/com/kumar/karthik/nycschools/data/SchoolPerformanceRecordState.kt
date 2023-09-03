package com.kumar.karthik.nycschools.data

sealed interface SchoolPerformanceRecordState {

    data class SchoolPerformanceDataState(val schoolsRecord: SchoolsRecord): SchoolPerformanceRecordState

    object MissingSchoolPerformanceRecordState: SchoolPerformanceRecordState

    object LoadingState: SchoolPerformanceRecordState
}