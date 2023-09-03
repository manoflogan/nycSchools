package com.kumar.karthik.nycschools.compose

import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import com.kumar.karthik.nycschools.R
import com.kumar.karthik.nycschools.ui.theme.NYCSchoolsTheme
import org.junit.Rule
import org.junit.Test

class LoadingScreenTest {

    @get:Rule
    val composeRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun validateThatLoadingViewIsShown() {
        composeRule.setContent {
            NYCSchoolsTheme {
                LoadingScreen(modifier = Modifier.fillMaxSize())
            }
        }
        composeRule.run {
            onNodeWithTag(Tags.LOADING_SCREEN_TAG).assertIsDisplayed()
        }
    }
}