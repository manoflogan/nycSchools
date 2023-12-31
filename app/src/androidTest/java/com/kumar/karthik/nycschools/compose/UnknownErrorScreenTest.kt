package com.kumar.karthik.nycschools.compose

import android.content.Context
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.core.app.ApplicationProvider
import com.kumar.karthik.nycschools.R
import com.kumar.karthik.nycschools.ui.theme.NYCSchoolsTheme
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class UnknownErrorScreenTest {

    @get:Rule
    val composeRule = createComposeRule()

    private lateinit var context: Context

    @Before
    fun setUp() {
        context = ApplicationProvider.getApplicationContext()
    }

    @Test
    fun validateThatErrorScreenTextIsShown() {
        composeRule.setContent {
            NYCSchoolsTheme {
                UnknownErrorScreen(modifier = Modifier.fillMaxSize())
            }
        }
        composeRule.run {
            onNodeWithText(context.getString( R.string.unknown_status)).assertIsDisplayed()
        }
    }
}