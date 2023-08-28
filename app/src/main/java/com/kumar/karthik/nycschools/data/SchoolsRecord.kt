package com.kumar.karthik.nycschools.data

import com.google.gson.annotations.SerializedName

/**
 * Instance of this class limit
 */
data class SchoolsRecord(
    val dbn: String,
    @SerializedName("school_name")
    val schoolName: String
)
