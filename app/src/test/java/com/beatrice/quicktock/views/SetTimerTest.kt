package com.beatrice.quicktock.views

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.beatrice.quicktock.MainActivity
import com.beatrice.quicktock.TestApplication
import com.beatrice.quicktock.views.util.launchAndVerifyTimerScreen
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
    fun tearDown() {
        stopKoin()
    }

    /**
     * Starting the activity when the timer is not set
     */
    @Test
    fun setTimerTest() {
        launchAndVerifyTimerScreen(composeTestRule) {
            setTimerTitleIsDisplayed()
            enterTimerDuration()
            clickSaveBtn()
        } verify {
            timerDurationTextIsDisplayed()
            playButtonIsDisplayed()
        }
    }
}
