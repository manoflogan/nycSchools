package com.kumar.karthik.nycschools.data

sealed interface SchoolPerformanceRecordState {
    object InvalidSchoolPerformanceRecordState: SchoolPerformanceRecordState

    data class SchoolPerformanceDataRecordState(val schoolsRecord: SchoolPerformanceRecord): SchoolPerformanceRecordState

    object MissingSchoolPerformanceRecordState: SchoolPerformanceRecordState

    object UnknownSchoolPerformanceRecordState: SchoolPerformanceRecordState

    object LoadingState: SchoolPerformanceRecordState
}