package com.kumar.karthik.nycschools.data

import com.google.gson.annotations.SerializedName

/**
 * Instance of this class limit
 */
data class SchoolsRecord(
    val dbn: String,
    @SerializedName("school_name")
    val schoolName: String,
    @SerializedName("sat_critical_reading_avg_score")
    val readingScore: String,
    @SerializedName("sat_math_avg_score")
    val mathScore: String,
    @SerializedName("sat_writing_avg_score")
    val writingScore: String
)
