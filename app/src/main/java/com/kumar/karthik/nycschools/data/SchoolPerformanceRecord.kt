package com.kumar.karthik.nycschools.data

import com.google.gson.annotations.SerializedName

data class SchoolPerformanceRecord(
    val dbn: String,
    @SerializedName("school_name")
    val schoolName: String,
    @SerializedName("sat_critical_reading_avg_score")
    val readingScore: Int,
    @SerializedName("sat_math_avg_score")
    val mathScore: Int,
    @SerializedName("sat_writing_avg_score")
    val writingScore: Int
)
