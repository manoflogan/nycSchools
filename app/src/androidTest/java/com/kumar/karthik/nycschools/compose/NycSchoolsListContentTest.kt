package com.kumar.karthik.nycschools.compose

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.test.core.app.ApplicationProvider
import androidx.test.platform.app.InstrumentationRegistry
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.kumar.karthik.nycschools.Destination
import com.kumar.karthik.nycschools.R
import com.kumar.karthik.nycschools.data.SchoolsRecord
import com.kumar.karthik.nycschools.data.SchoolsState
import com.kumar.karthik.nycschools.ui.theme.NYCSchoolsTheme
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.io.InputStreamReader

@SuppressLint("UnrememberedMutableState")
class NycSchoolsListContentTest {

    @get:Rule
    val composeRule = createComposeRule()
    
    private lateinit var context: Context
    
    @Before
    fun setUp() {
        context = ApplicationProvider.getApplicationContext()
    }

    @Test
    fun testWhenNycSchoolStateIsLoadingThenLoadingIndicatorIsShown() {
        validateState(SchoolsState.LoadingState) {
            composeRule.onNodeWithTag(Tags.LOADING_SCREEN_TAG).assertIsDisplayed()
        }
    }

    @Test
    fun testWhenNycSchoolStateIsUnknownStateThenMissingStateIndicatorIsShown() {
        validateState(SchoolsState.UnknownState) {
            composeRule.onNodeWithText(context.getString(R.string.unknown_status)).assertIsDisplayed()
        }
    }

    @Test
    fun testWhenNycSchoolStateIsErrorStateThenMissingStateIndicatorIsShown() {
        validateState(SchoolsState.ErrorState) {
            composeRule.onNodeWithTag(Tags.ERROR_ICON_TAG).assertIsDisplayed()
            composeRule.onNodeWithText(context.getString(R.string.error_found)).assertIsDisplayed()
        }
    }

    @Test
    fun testWhenNycSchoolStateIsEmptyStateThenMissingStateIndicatorIsShown() {
        validateState(SchoolsState.EmptySchoolDataState) {
            composeRule.onNodeWithText(context.getString(R.string.no_records_found)).assertIsDisplayed()
        }
    }

    @Test
    fun testWhenNycSchoolStateIValidSchoolDataStateThenMissingStateIndicatorIsShown() {
        val schoolRecords: List<SchoolsRecord>
        InstrumentationRegistry.getInstrumentation().context.resources.assets.open(RESOURCE_FILE).use {
            val listType = object: TypeToken<List<SchoolsRecord>>() {}.type
            schoolRecords = Gson().fromJson(InputStreamReader(it), listType);
        }
        validateState(SchoolsState.ValidSchoolDataState(schoolRecords)) {
            schoolRecords.forEach { schoolRecord ->
                composeRule.onNodeWithText(schoolRecord.dbn!!).assertIsDisplayed()
                composeRule.onNodeWithText(schoolRecord.schoolName!!).assertIsDisplayed()
            }
        }
    }

    private fun validateState(expectedState: SchoolsState, verificationCallback: () -> Unit) {
        composeRule.setContent {
            NYCSchoolsTheme {
                NycSchoolsListContent(
                    modifier = Modifier.fillMaxSize(),
                    currentDestination = Destination.Feed,
                    mutableStateOf(expectedState),
                ) {

                }
            }
        }
        verificationCallback()
    }

    companion object {
        private const val RESOURCE_FILE = "com/kumar/karthik/nycschools/repository/fake_repository_response.json"
    }

}