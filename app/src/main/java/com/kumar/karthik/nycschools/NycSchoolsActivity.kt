package com.kumar.karthik.nycschools

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import com.kumar.karthik.nycschools.compose.NycSchoolsHost
import com.kumar.karthik.nycschools.ui.theme.NYCSchoolsTheme
import dagger.hilt.android.AndroidEntryPoint

/**
 * Launching activity to display the activity screen.
 */
@AndroidEntryPoint
class NycSchoolsActivity : AppCompatActivity() {

    val nycSchoolsViewModel: NycSchoolsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NYCSchoolsTheme {
                NycSchoolsHost(
                    Modifier.fillMaxSize(),
                    nycSchoolsViewModel
                )
            }
        }
    }
}