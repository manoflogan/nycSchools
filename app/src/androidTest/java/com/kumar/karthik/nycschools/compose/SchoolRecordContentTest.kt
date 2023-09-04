package com.kumar.karthik.nycschools.compose

import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.unit.dp
import com.kumar.karthik.nycschools.ui.theme.NYCSchoolsTheme
import org.hamcrest.MatcherAssert
import org.hamcrest.Matchers
import org.junit.Rule
import org.junit.Test

class SchoolRecordContentTest {

    @get:Rule
    val composeRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun validateThatBothDbnAndSchoolNameAreDisplayed() {
        composeRule.setContent { 
            NYCSchoolsTheme {
                SchoolsRecordContent(
                    modifier = Modifier.padding(8.dp).fillMaxWidth(),
                    schoolsRecord = SchoolTestConstants.SCHOOL_RECORD
                ) {

                }
            }
        }
        composeRule.run {
            onNodeWithText(SchoolTestConstants.SCHOOL_RECORD.dbn!!).assertIsDisplayed()
            onNodeWithText(SchoolTestConstants.SCHOOL_RECORD.schoolName!!).assertIsDisplayed()
        }
    }

    @Test
    fun validateThatBothDbnAndSchoolNameAreDisplayedAndClickActionIsTriggered() {
        var isClicked = false
        composeRule.setContent {
            NYCSchoolsTheme {
                SchoolsRecordContent(
                    modifier = Modifier.padding(8.dp).fillMaxWidth(),
                    schoolsRecord = SchoolTestConstants.SCHOOL_RECORD
                ) {
                    MatcherAssert.assertThat(it, Matchers.`is`(SchoolTestConstants.SCHOOL_RECORD))
                    isClicked = true
                }
            }
        }
        composeRule.run {
            onNodeWithText(SchoolTestConstants.SCHOOL_RECORD.dbn!!).performClick()
            onNodeWithText(SchoolTestConstants.SCHOOL_RECORD.schoolName!!).assertIsDisplayed()
        }
        MatcherAssert.assertThat(isClicked, Matchers.`is`(true))
    }
}