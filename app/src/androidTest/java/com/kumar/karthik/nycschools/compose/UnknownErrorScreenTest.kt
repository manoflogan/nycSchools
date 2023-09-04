package com.kumar.karthik.nycschools.compose

import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.kumar.karthik.nycschools.R
import com.kumar.karthik.nycschools.ui.theme.NYCSchoolsTheme
import org.junit.Rule
import org.junit.Test

class UnknownErrorScreenTest {

    @get:Rule
    val composeRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun validateThatErrorScreenTextIsShown() {
        composeRule.setContent {
            NYCSchoolsTheme {
                UnknownErrorScreen(modifier = Modifier.fillMaxSize())
            }
        }
        composeRule.run {
            onNodeWithText(composeRule.activity.getString( R.string.unknown_status)).assertIsDisplayed()
        }
    }
}