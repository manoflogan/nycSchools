package com.kumar.karthik.nycschools.compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kumar.karthik.nycschools.data.SchoolsRecord
import kotlinx.coroutines.launch

@Composable
fun SchoolRecordListScreen(modifier: Modifier, schoolRecords: List<SchoolsRecord>,
                           onClick: (SchoolsRecord) -> Unit) {
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
    LazyColumn(state = listState, modifier = modifier, verticalArrangement = Arrangement.spacedBy(8.dp)) {
        itemsIndexed(schoolRecords) { index: Int, item: SchoolsRecord ->
            SchoolsRecordContent(
                modifier = Modifier.padding(8.dp).fillMaxWidth(), schoolsRecord = item,
                onClick = { schoolRecord: SchoolsRecord ->
                    coroutineScope.launch {
                        listState.scrollToItem(index)
                    }
                    onClick(schoolRecord)
                }
            )
            if (index < schoolRecords.lastIndex) {
                Divider(color = MaterialTheme.colorScheme.inverseSurface, thickness = 1.dp)
            }
        }
    }
}