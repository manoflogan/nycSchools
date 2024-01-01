package com.kumar.karthik.nycschools.compose

import android.content.Context
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.core.app.ApplicationProvider
import com.kumar.karthik.nycschools.Destination
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.experimental.theories.DataPoints
import org.junit.experimental.theories.Theories
import org.junit.experimental.theories.Theory
import org.junit.runner.RunWith

@RunWith(Theories::class)
class NycSchoolsHomeTest {

    @get:Rule
    val composeRule = createComposeRule()

    private lateinit var context: Context

    @Before
    fun setUp() {
        context = ApplicationProvider.getApplicationContext()
    }

    @Theory
    fun validateThatTopBarIsVisible(destination: Destination) {
        composeRule.setContent {
            NycSchoolsHome(
                modifier = Modifier.fillMaxWidth(),
                currentDestination = destination
            ) { modifier: Modifier ->
                Text(modifier = modifier, text = TEXT)
            }
        }

        composeRule.run {
            onNodeWithText(context.getString(destination.title)).assertIsDisplayed()
            onNodeWithText(TEXT).assertIsDisplayed()
        }
    }

    companion object {
        private const val TEXT = "Text"

        @[JvmField DataPoints]
        val destinations = arrayOf(Destination.School, Destination.Feed)
    }
}