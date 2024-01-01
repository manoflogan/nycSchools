package com.kumar.karthik.nycschools.repository

import dagger.hilt.GeneratesRootInput

@Retention(AnnotationRetention.BINARY)
@Target(AnnotationTarget.FUNCTION, AnnotationTarget.CONSTRUCTOR,
    AnnotationTarget.PROPERTY,
    AnnotationTarget.VALUE_PARAMETER
)
@GeneratesRootInput
annotation class TestQualifier
