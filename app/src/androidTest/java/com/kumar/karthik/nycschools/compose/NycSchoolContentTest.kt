package com.kumar.karthik.nycschools.compose

import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import com.kumar.karthik.nycschools.Destination
import com.kumar.karthik.nycschools.NycSchoolsViewModel
import com.kumar.karthik.nycschools.R
import com.kumar.karthik.nycschools.compose.SchoolTestConstants.SCHOOL_RECORD
import com.kumar.karthik.nycschools.data.SchoolPerformanceRecordState
import com.kumar.karthik.nycschools.ui.theme.NYCSchoolsTheme
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.junit4.MockKRule
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Rule
import org.junit.Test

class NycSchoolContentTest {

    @get:Rule
    val composeRule = createAndroidComposeRule<ComponentActivity>()

    @get:Rule
    val mockkRule = MockKRule(this)

    @MockK
    private lateinit var viewModel: NycSchoolsViewModel

    @Test
    fun whenTheStateHasRecordsListThenLoadingComposableIsViewed() {
        coEvery {
            viewModel.schoolRecordFlow
        } returns MutableStateFlow(
            SchoolPerformanceRecordState.SchoolPerformanceDataState(SCHOOL_RECORD)
        )
        composeRule.setContent {
            NYCSchoolsTheme {
                NycSchoolContent(
                    modifier = Modifier.fillMaxSize(), currentDestination = Destination.School,
                    nycSchoolsViewModel =  viewModel
                )
            }
        }
        composeRule.run {
            onNodeWithText(SCHOOL_RECORD.schoolName!!).assertIsDisplayed()
            onNodeWithText(SCHOOL_RECORD.readingScore!!).assertIsDisplayed()
            onNodeWithText(SCHOOL_RECORD.mathScore!!).assertIsDisplayed()
            onNodeWithText(SCHOOL_RECORD.writingScore!!).assertIsDisplayed()
            onAllNodesWithText(Destination.School.route).onFirst().assertIsDisplayed()
        }
    }

    @Test
    fun whenTheStateIsLoadingThenLoadingComposableIsViewed() {
        coEvery {
            viewModel.schoolRecordFlow
        } returns MutableStateFlow(
            SchoolPerformanceRecordState.LoadingState
        )
        composeRule.setContent {
            NYCSchoolsTheme {
                NycSchoolContent(
                    modifier = Modifier.fillMaxSize(), currentDestination = Destination.School,
                    nycSchoolsViewModel =  viewModel
                )
            }
        }
        composeRule.run {
           onNodeWithTag(Tags.LOADING_SCREEN_TAG).assertIsDisplayed()
        }
    }

    @Test
    fun whenTheStateIsMissingSchoolPerfomanceThenLoadingComposableIsViewed() {
        coEvery {
            viewModel.schoolRecordFlow
        } returns MutableStateFlow(
            SchoolPerformanceRecordState.MissingSchoolPerformanceRecordState
        )
        composeRule.setContent {
            NYCSchoolsTheme {
                NycSchoolContent(
                    modifier = Modifier.fillMaxSize(), currentDestination = Destination.School,
                    nycSchoolsViewModel =  viewModel
                )
            }
        }
        composeRule.run {
            onNodeWithText(composeRule.activity.getString(R.string.no_records_found)).assertIsDisplayed()
        }
    }
}