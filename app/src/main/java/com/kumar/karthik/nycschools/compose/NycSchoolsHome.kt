package com.kumar.karthik.nycschools.compose

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import com.kumar.karthik.nycschools.Destination
import com.kumar.karthik.nycschools.ui.theme.Typography

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NycSchoolsHome(
    modifier: Modifier = Modifier,
    currentDestination: Destination,
    content: @Composable (Modifier) -> Unit
) {
    val snackbarHostState = remember { SnackbarHostState() }

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
                        text = stringResource(currentDestination.title),
                        style = Typography.headlineMedium,
                        modifier = Modifier.semantics {
                            heading()
                        }
                    )
                }
            )
        }
    ) {
        content(Modifier.padding(it))
    }
}