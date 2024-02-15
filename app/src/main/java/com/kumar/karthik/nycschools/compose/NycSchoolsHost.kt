package com.kumar.karthik.nycschools.compose

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.kumar.karthik.nycschools.Destination
import com.kumar.karthik.nycschools.NycSchoolsViewModel

/**
 * Navigation host composable that manages the display of both feed, and individual content.
 *
 * @param modifier inherited modifier
 * @param nycSchoolsViewModel view model instance
 */
@Composable
fun NycSchoolsHost(
    modifier: Modifier = Modifier,
    nycSchoolsViewModel: NycSchoolsViewModel = viewModel(),
    navController: NavHostController = rememberNavController()
) {
    val navBackStackEntry = navController.currentBackStackEntryAsState()
    val currentDestination by remember(navBackStackEntry) {
        derivedStateOf {
            Destination.fromString(navBackStackEntry.value?.destination?.route)
        }
    }
    NavHost(navController = navController, startDestination = Destination.Feed.route, modifier = modifier) {
        /**
         * Base route to display the school feed
         */
        composable(Destination.Feed.route) {
            NycSchoolsListContent(
                Modifier.fillMaxSize(), currentDestination,
                nycSchoolsViewModel.schoolsStateFlow.collectAsStateWithLifecycle(),
            ) { route: String ->
                navController.navigate(route) {
                    popUpTo(navController.graph.findStartDestination().id) {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            }
        }
        /**
         * Shows the individual school item
         */
        composable(
            "${Destination.School.route}/{dbn}",
            arguments = listOf(
                navArgument("dbn") {
                    type = NavType.StringType
                }
            )
        ) { navBackStackEntry ->
            navBackStackEntry.arguments?.getString("dbn")?.let {
                nycSchoolsViewModel.fetchNycSchoolData(it)
                NycSchoolContent(
                    Modifier
                        .fillMaxWidth()
                        .wrapContentSize(),
                    currentDestination,
                    nycSchoolsViewModel.schoolRecordFlow.collectAsStateWithLifecycle())
            }
        }
    }
}