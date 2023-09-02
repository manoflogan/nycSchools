package com.kumar.karthik.nycschools.compose

import androidx.compose.foundation.ScrollState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.kumar.karthik.nycschools.Destination
import com.kumar.karthik.nycschools.NycSchoolsViewModel
import com.kumar.karthik.nycschools.data.SchoolsRecord
import com.kumar.karthik.nycschools.data.SchoolsState
import kotlinx.coroutines.launch

@Composable
fun NycSchoolsListContent(modifier: Modifier, currentDestination: Destination,
                          viewModel: NycSchoolsViewModel, navHostController: NavHostController
) {
    NycSchoolsHome(modifier, currentDestination) { composableModifier: Modifier ->
        val contentModifier = composableModifier.then(modifier)
        val coroutineScope = rememberCoroutineScope()
        val schoolRecordState: SchoolsState by viewModel.schoolsStateFlow.collectAsStateWithLifecycle()
        when(schoolRecordState) {
            SchoolsState.LoadingState -> {
                LoadingScreen(modifier = contentModifier)
            }
            is SchoolsState.ValidSchoolDataState -> {
                SchoolRecordListScreen(
                    modifier = contentModifier,
                    schoolRecords = (schoolRecordState as SchoolsState.ValidSchoolDataState).schoolsRecords,
                    onClick = { scrollState: ScrollState, index: Int, schoolRecord: SchoolsRecord ->
                        coroutineScope.launch {
                            scrollState.animateScrollTo(index)
                            navHostController.navigate("${Destination.School.route}/${schoolRecord.dbn}") {
                                popUpTo(navHostController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    }
                )
            }
            SchoolsState.EmptySchoolDateState -> {
                EmptyView(modifier = contentModifier)
            }
            SchoolsState.ErrorState -> {
                ErrorView(modifier = contentModifier)
            }
            SchoolsState.UnknownState -> {
                UnknownErrorScreen(modifier = contentModifier)
            }
        }
    }
}