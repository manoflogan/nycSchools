package com.kumar.karthik.nycschools.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kumar.karthik.nycschools.Destination
import com.kumar.karthik.nycschools.NycSchoolsViewModel
import com.kumar.karthik.nycschools.data.SchoolsRecord
import com.kumar.karthik.nycschools.data.SchoolsState

@Composable
fun NycSchoolsListContent(modifier: Modifier, currentDestination: Destination,
                          viewModel: NycSchoolsViewModel, onNavigate: (String) -> Unit
) {
    NycSchoolsHome(modifier, currentDestination) { composableModifier: Modifier ->
        val contentModifier = composableModifier.then(modifier)
        val schoolRecordState: SchoolsState by viewModel.schoolsStateFlow.collectAsStateWithLifecycle()
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