package com.kumar.karthik.nycschools

import android.content.Context
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.platform.app.InstrumentationRegistry
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.kumar.karthik.nycschools.compose.Tags
import com.kumar.karthik.nycschools.data.SchoolsRecord
import com.kumar.karthik.nycschools.data.SchoolsState
import com.kumar.karthik.nycschools.repository.NycRepositoryFake
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.RuleChain
import java.io.InputStreamReader
import javax.inject.Inject

@HiltAndroidTest
class NycSchoolsActivityTest {

    private val composeRule = createComposeRule()

    private val hiltAndroidRule = HiltAndroidRule(this)

    @get:Rule
    val ruleChain = RuleChain.outerRule(hiltAndroidRule).around(composeRule)

    private lateinit var schoolRecords: List<SchoolsRecord>

    @Inject
    lateinit var nycRepositoryFake: NycRepositoryFake

    @Before
    fun setUp() {
        InstrumentationRegistry.getInstrumentation().context.assets.open("com/kumar/karthik/nycschools/repository/fake_repository_response.json").use {
            val listType = object: TypeToken<List<SchoolsRecord>>() {}.type
            schoolRecords = Gson().fromJson(InputStreamReader(it), listType)
        }
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
}