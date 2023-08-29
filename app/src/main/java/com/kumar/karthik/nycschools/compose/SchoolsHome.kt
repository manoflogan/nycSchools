package com.kumar.karthik.nycschools.compose

import androidx.annotation.StringRes
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.kumar.karthik.nycschools.NycSchoolsViewModel
import com.kumar.karthik.nycschools.R
import com.kumar.karthik.nycschools.data.SchoolsRecord
import com.kumar.karthik.nycschools.data.SchoolsState
import com.kumar.karthik.nycschools.ui.theme.Typography
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NycSchoolsHome(
    modifier: Modifier = Modifier,
    viewModel: NycSchoolsViewModel = viewModel()
) {
    val navController = rememberNavController()
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    Scaffold(
        modifier = modifier,
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar(
                colors = topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.inverseSurface,
                    titleContentColor = MaterialTheme.colorScheme.surfaceVariant,
                ),
                title = {
                    Text(
                        text = stringResource(id = R.string.schools_list),
                        style = Typography.headlineMedium
                    )
                }
            )
        }
    ) {
        val paddingModifier = Modifier.padding(it)
        val schoolRecordState: SchoolsState by viewModel.schoolsStateFlow.collectAsStateWithLifecycle()
        when(schoolRecordState) {
            SchoolsState.LoadingState -> {
                LoadingScreen(modifier = paddingModifier)
            }
            is SchoolsState.ValidSchoolDataState -> {
                SchoolRecordListScreen(
                    modifier = paddingModifier,
                    schoolRecords = (schoolRecordState as SchoolsState.ValidSchoolDataState).schoolsRecords,
                    onClick = { scrollState: ScrollState, index: Int, schoolRecord: SchoolsRecord ->
                        coroutineScope.launch {
                            scrollState.animateScrollTo(index)
                        }
                    }
                )
            }
            SchoolsState.EmptySchoolDateState -> {
                EmptyView(modifier = paddingModifier)
            }
            SchoolsState.ErrorState -> {
                ErrorView(modifier = paddingModifier)
            }
            SchoolsState.UnknownState -> {
                UnknownErrorScreen(modifier = paddingModifier)
            }
        }
    }
}


@Composable
fun LoadingScreen(modifier: Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator(
            modifier = Modifier.size(32.dp),
            color = MaterialTheme.colorScheme.inverseSurface,
        )
    }
}

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

@Composable
fun SchoolsRecordContent(modifier: Modifier, schoolsRecord: SchoolsRecord, index: Int,
                         scrollState: ScrollState, onClick: (ScrollState, index: Int, SchoolsRecord) -> Unit) {
    Column(
        modifier = modifier.then(
            Modifier
                .padding(8.dp)
                .clickable(
                    role = Role.Button,
                    onClickLabel = stringResource(id = R.string.school_click_accessibility)
                ) {
                    onClick(scrollState, index, schoolsRecord)
                }
        )
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            SchoolContent(stringResource = R.string.dbn, text = schoolsRecord.dbn)
            SchoolContent(stringResource = R.string.school, text = schoolsRecord.schoolName)
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


@Composable
fun EmptyView(modifier: Modifier) {
    Column(modifier = modifier.then(Modifier.fillMaxSize()), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
        Text(
            text = stringResource(id = R.string.no_records_found),
            style = MaterialTheme.typography.bodyLarge
        )
    }
}


@Composable
fun ErrorView(modifier: Modifier) {
    Row(
        modifier = modifier.then(Modifier.fillMaxWidth()),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(painter = painterResource(R.drawable.error_black_24dp), contentDescription = null)
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = stringResource(id = R.string.error_found))
    }
}


@Composable
fun UnknownErrorScreen(modifier: Modifier) {
    Row(
        modifier = modifier.then(Modifier.fillMaxWidth()),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = stringResource(id = R.string.unknown_status))
    }
}
