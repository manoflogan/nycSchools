package com.kumar.karthik.nycschools.compose

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kumar.karthik.nycschools.data.SchoolsRecord

@Composable
fun SchoolRecordListScreen(modifier: Modifier, schoolRecords: List<SchoolsRecord>,
                           onClick: (ScrollState, index: Int, SchoolsRecord) -> Unit) {
    val scrollState = rememberScrollState()
    LazyColumn(modifier = modifier, verticalArrangement = Arrangement.spacedBy(8.dp)) {
        itemsIndexed(schoolRecords) { index: Int, item: SchoolsRecord ->
            SchoolsRecordContent(
                modifier = Modifier.fillMaxWidth(), schoolsRecord = item, index = index,
                scrollState = scrollState, onClick = onClick
            )
            if (index < schoolRecords.lastIndex) {
                Divider(color = MaterialTheme.colorScheme.inverseSurface, thickness = 1.dp)
            }
        }
    }
}