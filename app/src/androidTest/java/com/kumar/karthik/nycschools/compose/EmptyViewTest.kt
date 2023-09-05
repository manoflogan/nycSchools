package com.kumar.karthik.nycschools.compose

import androidx.activity.ComponentActivity
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.filters.SmallTest
import com.kumar.karthik.nycschools.R
import com.kumar.karthik.nycschools.ui.theme.NYCSchoolsTheme
import org.junit.Rule
import org.junit.Test

@SmallTest
class EmptyViewTest {

    @get:Rule
    val composeRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun validateThatEmptyViewTextIsShown() {
        composeRule.setContent {
            NYCSchoolsTheme {
                EmptyView(modifier = Modifier)
            }
        }
        composeRule.run {
            onNodeWithText(composeRule.activity.getString(R.string.no_records_found)).assertIsDisplayed()
        }
    }
}