package com.kumar.karthik.nycschools.compose

import com.kumar.karthik.nycschools.data.SchoolsRecord

object SchoolTestConstants {
    private const val DBN = "dbn"
    private const val SCHOOL_NAME = "schoolName"
    private const val READING_SCORE = "400"
    private const val MATH_SCORE = "100"
    private const val WRITING_SCORE = "150"

    val SCHOOL_RECORD = SchoolsRecord(
        dbn = DBN, schoolName = SCHOOL_NAME, readingScore = READING_SCORE,
        mathScore = MATH_SCORE, writingScore = WRITING_SCORE
    )

    internal const val RESOURCE_FILE = "com/kumar/karthik/nycschools/repository/fake_repository_response.json"
}