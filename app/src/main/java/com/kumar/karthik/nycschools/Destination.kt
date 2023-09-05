package com.kumar.karthik.nycschools

import androidx.annotation.StringRes
import androidx.core.net.toUri

/**
 * Encapsulates all supported destination
 */
sealed class Destination(
    /**
     * Represents the route required to display the school record datta
     */
    val route: String,
    /**
     * Title representation to be displayed on the screen
     */
    @StringRes val title: Int
) {
    /**
     * Destination used to render feed information
     */
    object Feed: Destination("Feed", R.string.schools_list)

    /**
     * Destination used to render individual school content
     */
    object School: Destination("School", R.string.school_record)

    companion object {
        /**
         * Fetches the destination to be rendered; defaults to feed information
         */
        fun fromString(route: String?): Destination {
            return when (route?.toUri()?.pathSegments?.firstOrNull()) {
                Feed.route -> Feed
                School.route -> School
                else -> Feed
            }
        }
    }
}
