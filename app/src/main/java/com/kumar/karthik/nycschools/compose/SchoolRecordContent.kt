package com.kumar.karthik.nycschools.compose

import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.kumar.karthik.nycschools.R
import com.kumar.karthik.nycschools.data.SchoolsRecord

@Composable
fun SchoolsRecordContent(modifier: Modifier, schoolsRecord: SchoolsRecord,
                         onClick: (SchoolsRecord) -> Unit) {
    Column(
        modifier = modifier.then(
            Modifier.fillMaxWidth()
                .semantics(mergeDescendants = true) {  }
                .clickable(
                    role = Role.Button,
                    onClickLabel = stringResource(id = R.string.school_click_accessibility)
                ) {
                    onClick(schoolsRecord)
                }
        )
    ) {
        schoolsRecord.dbn?.let {
            SchoolContent(stringResource = R.string.dbn, text = it)
        }
        schoolsRecord.schoolName?.let {
            SchoolContent(stringResource = R.string.school, text = it)
        }
    }
}


@Composable
private fun SchoolContent(@StringRes stringResource: Int, text: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(text = stringResource(stringResource), style = MaterialTheme.typography.bodyMedium)
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = text, style = MaterialTheme.typography.bodyMedium, maxLines = 1)
    }
}