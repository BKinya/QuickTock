package com.beatrice.quicktock.views

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.beatrice.quicktock.MainActivity
import com.beatrice.quicktock.TestApplication
import com.beatrice.quicktock.views.util.launchTimerScreen
import com.beatrice.quicktock.views.util.sendUiEvent
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.robolectric.shadows.ShadowLog

const val TEST_DURATION = 5
const val TIME_LEFT_ONE = 7 // TODO: Update these later... the VM test should be failing
const val TIME_LEFT_TWO = 1

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

    /**
     * 1. Count Down to finish without interruption
     * 2. Count Down  then pause then resume then finish
     * 3. count Down then stop
     * 4. count down pause stop
     */

    @Test
    fun `test counting down to finish`() {
        launchTimerScreen(composeTestRule) {
            timerDurationTextIsPresent()
            playButtonIsPresentAndClick()
        } verify {
            timerDurationTextIsPresent()
            playButtonNotPresent()
        }

        // wait till pause button appears
        // wait until

        /**
         * Send [onContinueCountingDown] event to state machine
         */
        sendUiEvent(composeTestRule) {
            composeTestRule.activity.timerViewModel.onContinueCountingDown(TEST_DURATION)
        } verify {
            waitForTimeLeftTextToUpdate()
            pauseButtonPresent()
            stopButtonIsPresent()
            playButtonNotPresent()
        }

        /**
         * Send [onFinish] event to state machine
         */

        sendUiEvent(composeTestRule) {
            composeTestRule.activity.timerViewModel.onFinishCountingDown()
        } verify {
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
