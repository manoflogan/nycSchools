package com.kumar.karthik.nycschools

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.kumar.karthik.nycschools.compose.NycSchoolsHome
import com.kumar.karthik.nycschools.compose.NycSchoolsHost
import com.kumar.karthik.nycschools.data.SchoolsState
import com.kumar.karthik.nycschools.ui.theme.NYCSchoolsTheme
import dagger.hilt.android.AndroidEntryPoint

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