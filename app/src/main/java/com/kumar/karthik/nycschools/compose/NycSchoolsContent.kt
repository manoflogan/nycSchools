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

/**
 * Shows the data for the individual school data.
 *
 * @param modifier inherited modifier
 * @param currentDestination current destination to be displayed
 * @param nycSchoolsViewModel view model instance
 */
@Composable
fun NycSchoolContent(
    modifier: Modifier, currentDestination: Destination, nycSchoolsViewModel: NycSchoolsViewModel
) {
    NycSchoolsHome(modifier, currentDestination) { composableModifier: Modifier ->
        val contentModifier = composableModifier.then(modifier)
        val schoolState: SchoolPerformanceRecordState by nycSchoolsViewModel.schoolRecordFlow.collectAsStateWithLifecycle()
        when (schoolState) {
            SchoolPerformanceRecordState.MissingSchoolPerformanceRecordState -> {
                EmptyView(modifier = contentModifier)
            }
            is SchoolPerformanceRecordState.SchoolPerformanceDataState -> {
                NycSchoolContentInternal(
                    modifier = contentModifier,
                    schoolRecord = (schoolState as SchoolPerformanceRecordState.SchoolPerformanceDataState).schoolsRecord
                )
            }
            SchoolPerformanceRecordState.LoadingState -> {
                LoadingScreen(modifier = contentModifier)
            }
        }
    }
}

/**
 * Shows the specific content including the following
 *
 * @param modifier inherited modifier
 * @param schoolRecord whose state is to be displayed
 */
@Composable
internal fun NycSchoolContentInternal(modifier: Modifier, schoolRecord: SchoolsRecord) {
    Column(modifier = modifier) {
        schoolRecord.schoolName?.let {
            TextContent(stringRes = R.string.school_record, text = it)
        }
        schoolRecord.readingScore?.let {
            TextContent(stringRes = R.string.reading_score, text = it)
        }
        schoolRecord.mathScore?.let {
            TextContent(stringRes = R.string.math_score, text = it)
        }
        schoolRecord.writingScore?.let {
            TextContent(stringRes = R.string.writing_score, text = it)
        }
    }
}

/**
 * Shows the text content for a given string
 *
 * @param stringRes string resource to be displayed
 * @param text to be displayed
 */
@Composable
private fun TextContent(@StringRes stringRes: Int, text: String) {
    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(bottom = 8.dp)
        .semantics(mergeDescendants = true) {}
    ) {
        Text(text = stringResource(id = stringRes), style = MaterialTheme.typography.bodyMedium)
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = text, style = MaterialTheme.typography.bodyMedium)
    }
}