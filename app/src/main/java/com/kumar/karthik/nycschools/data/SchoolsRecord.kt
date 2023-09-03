package com.kumar.karthik.nycschools.data

import com.google.gson.annotations.SerializedName

/**
 * Instance of this class limit
 */
data class SchoolsRecord(
    val dbn: String? = null,
    @SerializedName("school_name")
    val schoolName: String? = null,
    @SerializedName("sat_critical_reading_avg_score")
    val readingScore: String? = null,
    @SerializedName("sat_math_avg_score")
    val mathScore: String? = null,
    @SerializedName("sat_writing_avg_score")
    val writingScore: String? = null
)
