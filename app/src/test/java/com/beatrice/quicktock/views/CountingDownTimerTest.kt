package com.beatrice.quicktock.views

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.beatrice.quicktock.MainActivity
import com.beatrice.quicktock.TestApplication
import com.beatrice.quicktock.data.fake.TEST_DURATION
import com.beatrice.quicktock.views.util.launchAndVerifyTimerScreen
import com.beatrice.quicktock.views.util.waitUntilConditionMet
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.robolectric.shadows.ShadowLog

@RunWith(RobolectricTestRunner::class)
@Config(application = TestApplication::class)
class CountingDownTimerTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Before
    @Throws(Exception::class)
    fun setUp() {
        ShadowLog.stream = System.out // Redirect Logcat to console
        /**
         * Fast-forward the state of state machine to [TimerSet]
         */
        with(composeTestRule.activity.timerViewModel) {
            onTimerSet(TEST_DURATION)
        }
    }

    @Test
    fun `test counting down to finish`() {
        launchAndVerifyTimerScreen(composeTestRule) {
            timerDurationTextIsDisplayed()
            playButtonIsDisplayedAndClick()
        } verify {
            timerDurationTextIsPresent()
            playButtonNotPresent()
        }

        waitUntilConditionMet(composeTestRule) {
            timeLeftTextIsDisplayed()
        } verify {
            timeLeftTextIsDisplayed()
            pauseButtonIsDisplayed()
            stopButtonIsDisplayed()
        }

        waitUntilConditionMet(composeTestRule) {
            // wait until it finished countdown
            pauseButtonIsNotPresent()
        } verify {
            finishedTextIsDispalayed()
        }
    }
}
