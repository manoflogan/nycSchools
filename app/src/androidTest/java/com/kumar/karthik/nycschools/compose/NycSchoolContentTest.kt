package com.kumar.karthik.nycschools.compose

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.test.core.app.ApplicationProvider
import com.kumar.karthik.nycschools.Destination
import com.kumar.karthik.nycschools.R
import com.kumar.karthik.nycschools.compose.SchoolTestConstants.SCHOOL_RECORD
import com.kumar.karthik.nycschools.data.SchoolPerformanceRecordState
import com.kumar.karthik.nycschools.ui.theme.NYCSchoolsTheme
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@SuppressLint("UnrememberedMutableState")
class NycSchoolContentTest {

    @get:Rule
    val composeRule = createComposeRule()

    private lateinit var context: Context

    @Before
    fun setUp() {
        context = ApplicationProvider.getApplicationContext()
    }


    @Test
    fun whenTheStateHasRecordsListThenLoadingComposableIsViewed() {
        composeRule.setContent {
            NYCSchoolsTheme {
                NycSchoolContent(
                    modifier = Modifier.fillMaxSize(), currentDestination = Destination.School,
                    mutableStateOf( SchoolPerformanceRecordState.SchoolPerformanceDataState(SCHOOL_RECORD))
                )
            }
        }
        composeRule.run {
            onNodeWithText(SCHOOL_RECORD.schoolName!!).assertIsDisplayed()
            onNodeWithText(SCHOOL_RECORD.readingScore!!).assertIsDisplayed()
            onNodeWithText(SCHOOL_RECORD.mathScore!!).assertIsDisplayed()
            onNodeWithText(SCHOOL_RECORD.writingScore!!).assertIsDisplayed()
            onAllNodesWithText(context.getString(Destination.School.title)).onFirst().assertIsDisplayed()
        }
    }

    @Test
    fun whenTheStateIsLoadingThenLoadingComposableIsViewed() {
        composeRule.setContent {
            NYCSchoolsTheme {
                NycSchoolContent(
                    modifier = Modifier.fillMaxSize(), currentDestination = Destination.School,
                    mutableStateOf(SchoolPerformanceRecordState.LoadingState)
                )
            }
        }
        composeRule.run {
           onNodeWithTag(Tags.LOADING_SCREEN_TAG).assertIsDisplayed()
        }
    }

    @Test
    fun whenTheStateIsMissingSchoolPerfomanceThenLoadingComposableIsViewed() {
        composeRule.setContent {
            NYCSchoolsTheme {
                NycSchoolContent(
                    modifier = Modifier.fillMaxSize(), currentDestination = Destination.School,
                    mutableStateOf(SchoolPerformanceRecordState.MissingSchoolPerformanceRecordState)
                )
            }
        }
        composeRule.run {
            onNodeWithText(context.getString(R.string.no_records_found)).assertIsDisplayed()
        }
    }
}