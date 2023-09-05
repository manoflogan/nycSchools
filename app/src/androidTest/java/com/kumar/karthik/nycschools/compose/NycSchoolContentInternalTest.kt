package com.kumar.karthik.nycschools.compose

import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.unit.dp
import com.kumar.karthik.nycschools.R
import com.kumar.karthik.nycschools.compose.SchoolTestConstants.SCHOOL_RECORD
import com.kumar.karthik.nycschools.ui.theme.NYCSchoolsTheme
import org.junit.Rule
import org.junit.Test

class NycSchoolContentInternalTest {

    @get:Rule
    val composeRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun validateThatLoadingViewIsShown() {
        composeRule.setContent {
            NYCSchoolsTheme {
                NycSchoolContentInternal(modifier = Modifier.padding(16.dp), SCHOOL_RECORD)
            }
        }
        composeRule.run {
            onNodeWithText(composeRule.activity.getString(R.string.school_record)).assertIsDisplayed()
            onNodeWithText(SCHOOL_RECORD.schoolName!!).assertIsDisplayed()
            onNodeWithText(composeRule.activity.getString(R.string.reading_score)).assertIsDisplayed()
            onNodeWithText(SCHOOL_RECORD.readingScore!!).assertIsDisplayed()
            onNodeWithText(composeRule.activity.getString(R.string.math_score)).assertIsDisplayed()
            onNodeWithText(SCHOOL_RECORD.mathScore!!).assertIsDisplayed()
            onNodeWithText(composeRule.activity.getString(R.string.writing_score)).assertIsDisplayed()
            onNodeWithText(SCHOOL_RECORD.writingScore!!).assertIsDisplayed()
        }
    }
}