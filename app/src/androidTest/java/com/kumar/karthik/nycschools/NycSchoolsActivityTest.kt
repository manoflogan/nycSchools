package com.kumar.karthik.nycschools

import android.content.Context
import android.content.Intent
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createEmptyComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.core.net.toUri
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.platform.app.InstrumentationRegistry
import com.kumar.karthik.nycschools.compose.Tags
import com.kumar.karthik.nycschools.compose.fetchFromResources
import com.kumar.karthik.nycschools.data.SchoolsRecord
import com.kumar.karthik.nycschools.data.SchoolsState
import com.kumar.karthik.nycschools.repository.NycRepositoryFake
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.RuleChain
import javax.inject.Inject

@OptIn(ExperimentalTestApi::class)
@HiltAndroidTest
class NycSchoolsActivityTest {

    private val composeRule = createEmptyComposeRule()

    private val hiltAndroidRule = HiltAndroidRule(this)

    @get:Rule
    val ruleChain = RuleChain.outerRule(hiltAndroidRule).around(composeRule)

    private lateinit var schoolRecords: List<SchoolsRecord>

    @Inject
    lateinit var nycRepositoryFake: NycRepositoryFake

    @Before
    fun setUp() {
        schoolRecords = InstrumentationRegistry.getInstrumentation().context.fetchFromResources(
            "com/kumar/karthik/nycschools/repository/fake_repository_response.json"
        )
        hiltAndroidRule.inject()
    }
    @Test
    fun launchNycSchoolsActivityWithValidData_ValidatesThatDataIsDisplayed() {
        nycRepositoryFake.initialiseSchoolState(SchoolsState.ValidSchoolDataState(schoolRecords))
        ActivityScenario.launch(NycSchoolsActivity::class.java)
        composeRule.run {
            schoolRecords.forEach {
                onNodeWithText(it.dbn!!).assertIsDisplayed()
                onNodeWithText(it.schoolName!!).assertIsDisplayed()
            }
        }
    }

    @Test
    fun launchNycSchoolsActivity_ValidatesThatLoadingScreenIsShownInTheBeginning() {
        ActivityScenario.launch(NycSchoolsActivity::class.java)
        composeRule.run {
            onNodeWithTag(Tags.LOADING_SCREEN_TAG).assertIsDisplayed()
        }
    }

    @Test
    fun launchNycSchoolsActivity_ValidatesThatWhenEmptyListIsReturnedThenEmptyViewIsShown() {
        nycRepositoryFake.initialiseSchoolState(SchoolsState.EmptySchoolDataState)
        ActivityScenario.launch(NycSchoolsActivity::class.java)
        composeRule.run {
            onNodeWithText(
                ApplicationProvider.getApplicationContext<Context>().getString(
                    R.string.no_records_found
                )
            ).assertIsDisplayed()
        }
    }

    @Test
    fun launchNycSchoolsActivity_ValidatesThatWhenBackendReturnsAnErrorThenErrorViewIsLoaded() {
        nycRepositoryFake.initialiseSchoolState(SchoolsState.ErrorState)
        ActivityScenario.launch(NycSchoolsActivity::class.java)
        composeRule.run {
            onNodeWithTag(Tags.ERROR_ICON_TAG).assertIsDisplayed()
            onNodeWithText(
                ApplicationProvider.getApplicationContext<Context>().getString(
                    R.string.error_found
                )
            ).assertIsDisplayed()
        }
    }

    @Test
    fun launchNycSchoolsActivity_ValidatesThatWhenRecordIsClickedThenDetailsAreDisplyaed() {
        nycRepositoryFake.initialiseSchoolState(SchoolsState.ValidSchoolDataState(schoolRecords))
        ActivityScenario.launch(NycSchoolsActivity::class.java)
        composeRule.run {
            schoolRecords[1].also {
                onNodeWithText(it.dbn!!).assertIsDisplayed()
                onNodeWithText(it.schoolName!!).assertIsDisplayed().performClick()
                // wait for the other text to be displayed
                waitUntilAtLeastOneExists(hasText(it.mathScore!!), 3_000)
                onNodeWithText(it.mathScore!!).assertIsDisplayed()
                onNodeWithText(it.writingScore!!).assertIsDisplayed()
                onNodeWithText(it.readingScore!!).assertIsDisplayed()
            }
        }
    }
}