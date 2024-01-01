package com.kumar.karthik.nycschools.compose

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.kumar.karthik.nycschools.data.SchoolsRecord
import java.io.InputStreamReader

fun Context.fetchFromResources(filePath: String): List<SchoolsRecord> {
    val schoolRecords: List<SchoolsRecord>
    assets.open(filePath).use {
        val listType = object: TypeToken<List<SchoolsRecord>>() {}.type
        schoolRecords = Gson().fromJson(InputStreamReader(it), listType)
    }
    return schoolRecords
}