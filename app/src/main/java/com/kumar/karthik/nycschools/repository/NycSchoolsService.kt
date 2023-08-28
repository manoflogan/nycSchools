package com.kumar.karthik.nycschools.repository

import com.kumar.karthik.nycschools.data.SchoolsRecord
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.HeaderMap
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface NycSchoolsService {

    @GET("f9bf-2cp4.json")
    fun fetchAllSchools(@HeaderMap headers: Map<String, String>, @QueryMap queries: Map<String, String>): Call<List<SchoolsRecord>>
}