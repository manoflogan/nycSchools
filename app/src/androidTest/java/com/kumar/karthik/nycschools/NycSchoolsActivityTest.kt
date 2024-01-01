package com.kumar.karthik.nycschools

import android.content.Context
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.core.app.ActivityScenario
import androidx.test.platform.app.InstrumentationRegistry
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.kumar.karthik.nycschools.data.SchoolsRecord
import com.kumar.karthik.nycschools.repository.TestQualifier
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

    @Before
    fun setUp() {
        InstrumentationRegistry.getInstrumentation().context.assets.open("com/kumar/karthik/nycschools/repository/fake_repository_response.json").use {
            val listType = object: TypeToken<List<SchoolsRecord>>() {}.type
            schoolRecords = Gson().fromJson(InputStreamReader(it), listType)
        }
    }
    @Test
    fun launchNycSchoolsActivity() {
        ActivityScenario.launch(NycSchoolsActivity::class.java)
        composeRule.run {
            schoolRecords.first().also {
                onNodeWithText(it.dbn!!).assertIsDisplayed()
                onNodeWithText(it.schoolName!!).assertIsDisplayed()
            }
        }
    }
}