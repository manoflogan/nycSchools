package com.kumar.karthik.nycschools.compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.kumar.karthik.nycschools.R
import com.kumar.karthik.nycschools.compose.Tags.ERROR_ICON_TAG

/**
 * Shows the error view if the response fetch generates an error.
 */
@Composable
fun ErrorView(modifier: Modifier) {
    Row(
        modifier = modifier.then(Modifier.fillMaxWidth()),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(modifier = Modifier.testTag(ERROR_ICON_TAG).size(36.dp), painter = painterResource(R.drawable.error_black_24dp), contentDescription = null)
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = stringResource(id = R.string.error_found), style = MaterialTheme.typography.headlineMedium)
    }
}