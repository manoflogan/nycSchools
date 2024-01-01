package com.kumar.karthik.nycschools.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kumar.karthik.nycschools.Destination
import com.kumar.karthik.nycschools.NycSchoolsViewModel
import com.kumar.karthik.nycschools.data.SchoolsRecord
import com.kumar.karthik.nycschools.data.SchoolsState

/**
 * Handles the school list fetch and display based on fetch state.
 *
 * - If fetch state equals loading state, hen loading view is displayed
 * - If no data is available then empty view is displayed
 * - If the state is an unknown one, an equivalent state is displayed
 * - If a valid data is fetched, the, a list feed is displayed
 *
 * @param modifier inherited modifier
 * @param currentDestination destination to be loaded, whether it is the feed content, or the
 *     individual content
 * @param viewModel view model instance
 * @param onNavigate callback function that loads the specific destination
 */
@Composable
fun NycSchoolsListContent(modifier: Modifier, currentDestination: Destination,
                          schoolState: State<SchoolsState>, onNavigate: (String) -> Unit
) {
    NycSchoolsHome(modifier, currentDestination) { composableModifier: Modifier ->
        val contentModifier = composableModifier.then(modifier)
        val schoolRecordState: SchoolsState by schoolState
        when(schoolRecordState) {
            SchoolsState.LoadingState -> {
                LoadingScreen(modifier = contentModifier)
            }
            is SchoolsState.ValidSchoolDataState -> {
                SchoolRecordListScreen(
                    modifier = contentModifier,
                    schoolRecords = (schoolRecordState as SchoolsState.ValidSchoolDataState).schoolsRecords,
                    onClick = { schoolRecord: SchoolsRecord ->
                        onNavigate("${Destination.School.route}/${schoolRecord.dbn}")
                    }
                )
            }
            SchoolsState.EmptySchoolDataState -> {
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