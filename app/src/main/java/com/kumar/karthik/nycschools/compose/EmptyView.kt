package com.kumar.karthik.nycschools.compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.kumar.karthik.nycschools.R

@Composable
fun EmptyView(modifier: Modifier) {
    Column(modifier = modifier.then(Modifier.fillMaxSize()), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
        Text(
            text = stringResource(id = R.string.no_records_found),
            style = MaterialTheme.typography.headlineLarge
        )
    }
}