package com.kumar.karthik.nycschools.compose

import com.kumar.karthik.nycschools.data.SchoolsRecord

object SchoolTestConstants {
    internal const val DBN = "dbn"
    internal const val SCHOOL_NAME = "schoolName"
    internal const val READING_SCORE = "400"
    internal const val MATH_SCORE = "100"
    internal const val WRITING_SCORE = "150"

    val SCHOOL_RECORD = SchoolsRecord(
        dbn = DBN, schoolName = SCHOOL_NAME, readingScore = READING_SCORE,
        mathScore = MATH_SCORE, writingScore = WRITING_SCORE
    )
}