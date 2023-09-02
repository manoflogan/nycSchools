package com.kumar.karthik.nycschools

import androidx.annotation.StringRes
import androidx.core.net.toUri

sealed class Destination(
    val route: String,
    @StringRes val title: Int
) {
    object Feed: Destination("Feed", R.string.schools_list)
    object School: Destination("School", R.string.school)

    companion object {
        fun fromString(route: String?): Destination {
            return when (route?.toUri()?.pathSegments?.firstOrNull()) {
                Feed.route -> Feed
                School.route -> School
                else -> Feed
            }
        }
    }
}
