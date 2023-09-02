package com.kumar.karthik.nycschools.compose

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import com.kumar.karthik.nycschools.data.SchoolPerformanceRecordState
import com.kumar.karthik.nycschools.data.SchoolsRecord

@Composable
fun NycSchoolContent(modifier: Modifier, currentDestination: Destination, nycSchoolsViewModel: NycSchoolsViewModel) {
    NycSchoolsHome(modifier, currentDestination) { composableModifier: Modifier ->
        val contentModifier = composableModifier.then(modifier)
        val schoolState: SchoolPerformanceRecordState by nycSchoolsViewModel.schoolRecordFlow.collectAsStateWithLifecycle()
        when (schoolState) {
            SchoolPerformanceRecordState.MissingSchoolPerformanceRecordState -> {
                EmptyView(modifier = contentModifier)
            }
            is SchoolPerformanceRecordState.SchoolPerformanceDataRecordState -> {
                NycSchoolPerformanceContent(
                    modifier = contentModifier,
                    schoolRecord = (schoolState as SchoolPerformanceRecordState.SchoolPerformanceDataRecordState).schoolsRecord
                )
            }
            SchoolPerformanceRecordState.LoadingState -> {
                LoadingScreen(modifier = contentModifier)
            }
        }
    }
}

@Composable
fun NycSchoolPerformanceContent(modifier: Modifier, schoolRecord: SchoolsRecord) {
    Column(modifier = modifier) {
        TextContent(stringRes = R.string.school, text = schoolRecord.schoolName)
        TextContent(stringRes = R.string.reading_score, text = schoolRecord.readingScore)
        TextContent(stringRes = R.string.math_score, text = schoolRecord.mathScore)
        TextContent(stringRes = R.string.writing_score, text = schoolRecord.writingScore)
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