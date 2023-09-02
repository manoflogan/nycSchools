package com.kumar.karthik.nycschools.data

sealed interface SchoolPerformanceRecordState {

    data class SchoolPerformanceDataRecordState(val schoolsRecord: SchoolsRecord): SchoolPerformanceRecordState

    object MissingSchoolPerformanceRecordState: SchoolPerformanceRecordState

    object LoadingState: SchoolPerformanceRecordState
}