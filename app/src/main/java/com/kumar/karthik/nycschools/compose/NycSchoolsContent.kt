package com.kumar.karthik.nycschools.compose

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kumar.karthik.nycschools.Destination
import com.kumar.karthik.nycschools.NycSchoolsViewModel
import com.kumar.karthik.nycschools.R
import com.kumar.karthik.nycschools.data.SchoolPerformanceRecord
import com.kumar.karthik.nycschools.data.SchoolPerformanceRecordState

@Composable
fun NycSchoolContent(modifier: Modifier, currentDestination: Destination, nycSchoolsViewModel: NycSchoolsViewModel) {
    NycSchoolsHome(modifier, currentDestination) { composableModifier: Modifier ->
        val contentModifier = composableModifier.then(modifier)
        val schoolState: SchoolPerformanceRecordState by nycSchoolsViewModel.schoolRecordFlow.collectAsStateWithLifecycle()
        when (schoolState) {
            SchoolPerformanceRecordState.MissingSchoolPerformanceRecordState -> {
                EmptyView(modifier = contentModifier)
            }
            SchoolPerformanceRecordState.InvalidSchoolPerformanceRecordState -> {
                ErrorView(modifier = contentModifier)
            }

            SchoolPerformanceRecordState.UnknownSchoolPerformanceRecordState -> {
                UnknownErrorScreen(modifier = contentModifier)
            }

            is SchoolPerformanceRecordState.SchoolPerformanceDataRecordState -> {
                NycSchoolPerformanceContent(
                    modifier = contentModifier,
                    schoolPerformanceRecord = (schoolState as SchoolPerformanceRecordState.SchoolPerformanceDataRecordState).schoolsRecord
                )
            }
            SchoolPerformanceRecordState.LoadingState -> {
                LoadingScreen(modifier = contentModifier)
            }
        }
    }
}

@Composable
fun NycSchoolPerformanceContent(modifier: Modifier, schoolPerformanceRecord: SchoolPerformanceRecord) {
    Column(modifier = modifier) {
        TextContent(stringRes = R.string.school, text = schoolPerformanceRecord.schoolName)
        TextContent(stringRes = R.string.reading_score, text = schoolPerformanceRecord.readingScore.toString())
        TextContent(stringRes = R.string.math_score, text = schoolPerformanceRecord.mathScore.toString())
        TextContent(stringRes = R.string.writing_score, text = schoolPerformanceRecord.writingScore.toString())
    }
}

@Composable
fun TextContent(@StringRes stringRes: Int, text: String) {
    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(bottom = 8.dp)
        .semantics(mergeDescendants = true) {}) {
        Text(text = stringResource(id = stringRes), style = MaterialTheme.typography.bodyMedium)
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = text, style = MaterialTheme.typography.bodyMedium)
    }
}