package com.kumar.karthik.nycschools.compose

import android.content.Context
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.unit.dp
import androidx.test.core.app.ApplicationProvider
import com.kumar.karthik.nycschools.R
import com.kumar.karthik.nycschools.compose.SchoolTestConstants.SCHOOL_RECORD
import com.kumar.karthik.nycschools.ui.theme.NYCSchoolsTheme
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class NycSchoolContentInternalTest {

    @get:Rule
    val composeRule = createComposeRule()
    
    private lateinit var context: Context
    
    @Before
    fun setUp() {
        context = ApplicationProvider.getApplicationContext()
    }

    @Test
    fun validateThatLoadingViewIsShown() {
        composeRule.setContent {
            NYCSchoolsTheme {
                NycSchoolContentInternal(modifier = Modifier.padding(16.dp), SCHOOL_RECORD)
            }
        }
        composeRule.run {
            onNodeWithText(context.getString(R.string.school)).assertIsDisplayed()
            onNodeWithText(SCHOOL_RECORD.schoolName!!).assertIsDisplayed()
            onNodeWithText(context.getString(R.string.reading_score)).assertIsDisplayed()
            onNodeWithText(SCHOOL_RECORD.readingScore!!).assertIsDisplayed()
            onNodeWithText(context.getString(R.string.math_score)).assertIsDisplayed()
            onNodeWithText(SCHOOL_RECORD.mathScore!!).assertIsDisplayed()
            onNodeWithText(context.getString(R.string.writing_score)).assertIsDisplayed()
            onNodeWithText(SCHOOL_RECORD.writingScore!!).assertIsDisplayed()
        }
    }
}