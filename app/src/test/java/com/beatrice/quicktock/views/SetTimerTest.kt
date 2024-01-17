package com.beatrice.quicktock.views

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import com.beatrice.quicktock.MainActivity
import com.beatrice.quicktock.R
import com.beatrice.quicktock.TestApplication
import com.beatrice.quicktock.views.util.launchTimerScreen
import org.junit.After
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.stopKoin
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(application = TestApplication::class)
class SetTimerTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @After
    fun tearUp() {
        stopKoin()
    }

    /**
     * Starting the activity when the timer is not set
     */
    @Test
    fun setTimerTest() {

        launchTimerScreen(composeTestRule){
            setTimerTitleIsDisplayed()
            enterTimerDuration()
            clickSaveBtn()
        } verify {
            timerDurationTextIsPresent()
            playButtonIsDisplayed()
        }
    }


}
