package com.kumar.karthik.nycschools.compose

import android.content.Context
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.platform.app.InstrumentationRegistry
import com.kumar.karthik.nycschools.Destination
import com.kumar.karthik.nycschools.NycSchoolsViewModel
import com.kumar.karthik.nycschools.R
import com.kumar.karthik.nycschools.data.SchoolsState
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.junit4.MockKRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class NycSchoolsHostTest {

    @get:Rule
    val composeRule = createComposeRule()

    @get:Rule
    val mockkRule = MockKRule(this)

    @MockK
    private lateinit var viewModel: NycSchoolsViewModel

    private lateinit var navigationController: TestNavHostController

    private lateinit var testContext: Context

    private lateinit var context: Context

    @Before
    fun setUp() {
        testContext = InstrumentationRegistry.getInstrumentation().context
        context = ApplicationProvider.getApplicationContext()
    }

    @Test
    fun validateThatWhenScreenListIsSeenWhenDefaultImplementationIsLoaded() = runTest {
        val schoolRecords = testContext.fetchFromResources(SchoolTestConstants.RESOURCE_FILE)
        coEvery{
            viewModel.schoolsStateFlow
        } returns MutableStateFlow(
            SchoolsState.ValidSchoolDataState(
                schoolRecords
            )
        )
        validationCallback {
            onNodeWithText(context.getString(Destination.Feed.title)).assertIsDisplayed()
            schoolRecords.forEach {
                onNodeWithText(it.dbn!!, substring = true).assertIsDisplayed()
                onNodeWithText(it.schoolName!!, substring = true).assertIsDisplayed()
            }
        }
    }

    @Test
    fun validateThatWhenLoadingScreenIsSeenWhenDefaultImplementationIsLoaded() = runTest {
        coEvery{
            viewModel.schoolsStateFlow
        } returns MutableStateFlow(
            SchoolsState.LoadingState
        )
        validationCallback {
            onNodeWithText(context.getString(Destination.Feed.title)).assertIsDisplayed()
            onNodeWithTag(Tags.LOADING_SCREEN_TAG).assertIsDisplayed()
        }
    }

    @Test
    fun validateThatWhenErrorScreenIsSeenWhenDefaultImplementationIsLoaded() = runTest {
        coEvery{
            viewModel.schoolsStateFlow
        } returns MutableStateFlow(
            SchoolsState.EmptySchoolDataState
        )
        validationCallback {
            onNodeWithText(context.getString(Destination.Feed.title)).assertIsDisplayed()
            onNodeWithText(context.getString(R.string.no_records_found)).assertIsDisplayed()
        }
    }

    private fun validationCallback(callback: ComposeContentTestRule.() -> Unit) {
        composeRule.setContent {
            navigationController = TestNavHostController(LocalContext.current).apply {
                navigatorProvider.addNavigator(ComposeNavigator())
            }
            NycSchoolsHost(modifier = Modifier.fillMaxSize(), nycSchoolsViewModel = viewModel)
        }
        composeRule.run {
            this.callback()
        }
    }


}