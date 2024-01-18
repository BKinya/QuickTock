package com.beatrice.quicktock.views

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.beatrice.quicktock.MainActivity
import com.beatrice.quicktock.TestApplication
import com.beatrice.quicktock.data.fake.TEST_DURATION
import com.beatrice.quicktock.views.util.launchAndVerifyTimerScreen
import com.beatrice.quicktock.views.util.performAction
import com.beatrice.quicktock.views.util.waitUntilConditionMet
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.stopKoin
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.robolectric.shadows.ShadowLog

@RunWith(RobolectricTestRunner::class)
@Config(application = TestApplication::class)
class PauseCountDownTimerTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Before
    @Throws(Exception::class)
    fun setUp() {
        ShadowLog.stream = System.out // Redirect Logcat to console

        /**
         * Fast-forward the state of state machine to [CountingDown]
         */
        with(composeTestRule.activity.timerViewModel) {
            onTimerSet(TEST_DURATION)
            onStartCountDown(TEST_DURATION)
            onContinueCountingDown(TEST_DURATION)
        }
    }

    @After
    fun tearDown() {
        stopKoin()
    }

    /**
     * pause and resume
     * pause and stop
     * stop
     */

    @Test
    fun `test pause and resume counting down`() {
        launchAndVerifyTimerScreen(composeTestRule) {
            timerDurationTextIsDisplayed()
            stopButtonIsDisplayed()
            pauseButtonIsDisplayed()
        }

        waitUntilConditionMet(composeTestRule){
            timeLeftTextIsDisplayed_01()
        }

        // Pause count down
        performAction(composeTestRule){
            clickPauseButton()
        }verify {
            timeLeftTextIsDisplayed_01()
            resumeButtonIsDisplayed()
            stopButtonIsDisplayed()
        }
        // Resume count down
        performAction(composeTestRule) {
            clickResumeButton()
        } verify {
            timeLeftTextIsDisplayed_01()
            resumeButtonNotPresent()
            pauseButtonIsDisplayed()
            stopButtonIsDisplayed()
        }

        waitUntilConditionMet(composeTestRule) {
            timeLeftTextIsDisplayed_02()
        } verify {
            timeLeftTextIsDisplayed_02()
            pauseButtonIsDisplayed()
            stopButtonIsDisplayed()
        }

        // wait until it finishes countdown
        waitUntilConditionMet(composeTestRule) {
            pauseButtonIsNotPresent()
        } verify {
            finishedTextIsDispalayed()
        }
    }

    @Test
    fun `test stop countingDown`() {
        launchAndVerifyTimerScreen(composeTestRule){
         timerDurationTextIsDisplayed()
            stopButtonIsDisplayed()
            pauseButtonIsDisplayed()
        }

        waitUntilConditionMet(composeTestRule){
            timeLeftTextIsDisplayed_01()
        }

        // stop count down
        performAction(composeTestRule){
            clickStopButton()
        }verify {
            timerDurationTextIsDisplayed()
            playButtonIsDisplayed()
        }
    }
}
