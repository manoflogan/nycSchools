package com.kumar.karthik.nycschools.compose

import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollTo
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.kumar.karthik.nycschools.data.SchoolsRecord
import com.kumar.karthik.nycschools.ui.theme.NYCSchoolsTheme
import org.hamcrest.MatcherAssert
import org.hamcrest.Matchers
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import java.io.InputStreamReader

@Config(sdk = [Config.OLDEST_SDK])
@RunWith(RobolectricTestRunner::class)
class SchoolRecordListScreenTest {

    @get:Rule
    val composeRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun validateThaListOfColumnsAreDisplayed() {
        val schoolRecords: List<SchoolsRecord>
        ClassLoader.getSystemResourceAsStream(RESOURCE_FILE)!!.use {
            val listType = object: TypeToken<List<SchoolsRecord>>() {}.type
            schoolRecords = Gson().fromJson(InputStreamReader(it), listType);
        }
        composeRule.setContent {
            NYCSchoolsTheme {
                SchoolRecordScreen(modifier = Modifier.fillMaxSize(), schoolRecords) {
                }
            }
        }
        composeRule.run {
            schoolRecords.forEach {
                onNodeWithText(it.schoolName!!).assertIsDisplayed()
                onNodeWithText(it.dbn!!).assertIsDisplayed()
            }
        }
    }

    @Test
    fun validateThaListOfColumnsAreDisplayedAndClickActionIsGenerated() {
        val schoolRecords: List<SchoolsRecord>
        ClassLoader.getSystemResourceAsStream(RESOURCE_FILE)!!.use {
            val listType = object: TypeToken<List<SchoolsRecord>>() {}.type
            schoolRecords = Gson().fromJson(InputStreamReader(it), listType);
        }
        var isClicked = false
        composeRule.setContent {
            NYCSchoolsTheme {
                SchoolRecordScreen(modifier = Modifier.fillMaxSize(), schoolRecords) {
                    isClicked = true
                }
            }
        }
        composeRule.run {
           val lastSchoolRecord = schoolRecords.last()
           onNodeWithText(lastSchoolRecord.dbn!!).performScrollTo().performClick()
        }
        MatcherAssert.assertThat(isClicked, Matchers.`is`(true))
    }

    companion object {
        private const val RESOURCE_FILE = "com/kumar/karthik/nycschools/repository/fake_repository_response.json"
    }
}