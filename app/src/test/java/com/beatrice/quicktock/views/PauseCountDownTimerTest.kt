package com.beatrice.quicktock.views

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.beatrice.quicktock.MainActivity
import com.beatrice.quicktock.TestApplication
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

    @Test
    fun `test transition from COUNTING_DOWN state to PAUSED state and then back to COUNTING_DOWN state `() {
        launchTimerScreen(composeTestRule) {
            timerDurationTextIsPresent()
            stopButtonIsPresent()
            pauseButtonIsPresentAndClick()
        } verify {
            timerDurationTextIsPresent()
            pauseButtonNotPresent()
            stopButtonIsPresent()
            resumeButtonIsPresent()
        }

        /**
         * Send [onResume] event to state machine
         */
        sendUiEvent(composeTestRule) {
            composeTestRule.activity.timerViewModel.onResumeCountingDown(TEST_DURATION)
        } verify {
            timerDurationTextIsPresent()
            pauseButtonPresent()
            stopButtonIsPresent()
            resumeButtonIsNotPresent()
        }
    }
}
