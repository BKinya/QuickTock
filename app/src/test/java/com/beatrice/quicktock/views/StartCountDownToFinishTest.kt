package com.beatrice.quicktock.views

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import com.beatrice.quicktock.MainActivity
import com.beatrice.quicktock.R
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.shadows.ShadowLog

const val TEST_DURATION = 10
const val TIME_LEFT_ONE = 7
const val TIME_LEFT_TWO = 1

// TODO: Rename this class and file
@RunWith(RobolectricTestRunner::class)
class StartCountDownTimerTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Before
    @Throws(Exception::class)
    fun setUp() {
        ShadowLog.stream = System.out // Redirect Logcat to console
    }

    @Test
    fun `test transition from TIMER_SET state to COUNTING_DOWN_STARTED state to COUNTING_DOWN state to FINISHED state`() {
        launchTimerScreen(composeTestRule){
            TimerDurationTextIsPresent()
            playButtonIsPresentAndClick()

        } verify {
            TimerDurationTextIsPresent()
            playButtonNotPresent()
        }

        /**
         * Send [onContinueCountingDown] event to state machine
         */
        sendUiEvent(composeTestRule){
            composeTestRule.activity.timerViewModel.onContinueCountingDown(TEST_DURATION)
        }verify {
            waitForTimeLeftTextToUpdate()
            pauseButtonPresent()
            stopButtonIsPresent()
            playButtonNotPresent()
        }

        /**
         * Send [onFinish] event to state machine
         */

        sendUiEvent(composeTestRule){
            composeTestRule.activity.timerViewModel.onFinishCountingDown()
        }verify {
            waitUntilFinishTextIsDispalyed()
            pauseButtonNotPresent()
            stopButtonNotPresent()
        }
    }

    // TODO 2:
    /**
     * COUNTING DOWN -> PAUSE -> RESUME -> FINISH
     */
}
