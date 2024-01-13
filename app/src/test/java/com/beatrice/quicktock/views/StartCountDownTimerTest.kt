package com.beatrice.quicktock.views

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.beatrice.quicktock.MainActivity
import com.beatrice.quicktock.R
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.stopKoin
import org.robolectric.RobolectricTestRunner
import org.robolectric.shadows.ShadowLog

const val TEST_DURATION = 10
const val TIME_LEFT_ONE = 7
const val TIME_LEFT_TWO = 1

/**
 * Observation ... verification start when the app reaches the end state.
 * Intermediate states are skipped
 * so
 * Either test the states separately... This looks like it mighty give me want I want
 * But I have to override initial state for the state machine verytime
 * Not sure how I''ll test counting down to paused I can
 * but not counting down to finished... maybe I could
 */
@OptIn(ExperimentalTestApi::class)
@RunWith(RobolectricTestRunner::class)
class StartCountDownTimerTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Before
    @Throws(Exception::class)
    fun setUp() {
        ShadowLog.stream = System.out // Redirect Logcat to console
    }

    fun tearDown() {
        stopKoin()
    }

    @Test
    fun `test transition from TIMER_SET state to COUNTING_DOWN_STARTED state to COUNTING_DOWN state to FINISHED state`() {
        // 1 TimerSet state
        val duration = composeTestRule.activity.getString(R.string.durationLabel, TEST_DURATION)
        composeTestRule.onNodeWithText(duration).assertIsDisplayed()

        val playButtonDesc = composeTestRule.activity.getString(R.string.playBtnDesc)
        composeTestRule.onNodeWithContentDescription(playButtonDesc)
            .assertIsDisplayed()
            .performClick()

        // 2 CountingDownStarted state
        val timeLeft = composeTestRule.activity.getString(R.string.durationLabel, TEST_DURATION)
        composeTestRule.onNodeWithText(timeLeft).assertIsDisplayed()

        composeTestRule.onNodeWithContentDescription(playButtonDesc).assertDoesNotExist()

        // 3 send onContinueCountingDown event to state machine
        composeTestRule.activity.timerViewModel.onContinueCountingDown(TEST_DURATION)

        // 4 CountingDown state
        val timeLeft2 = composeTestRule.activity.getString(R.string.durationLabel, 9)
        composeTestRule.waitUntilExactlyOneExists(hasText(timeLeft2), timeoutMillis = 3000)

        composeTestRule.onNodeWithText(timeLeft2).assertIsDisplayed()
        val pauseBtnDesc = composeTestRule.activity.getString(R.string.pauseButtonDesc)
        composeTestRule.onNodeWithContentDescription(pauseBtnDesc).assertIsDisplayed()
        val stopBtnDesc = composeTestRule.activity.getString(R.string.stopButtonDesc)
        composeTestRule.onNodeWithContentDescription(stopBtnDesc).assertIsDisplayed()

        // 5 send onFinish event to the state machine
        composeTestRule.activity.timerViewModel.onFinishCountingDown()

        // 6 Finished state
        composeTestRule.waitUntilExactlyOneExists(hasText("Finished"))
        composeTestRule.onNodeWithContentDescription(pauseBtnDesc).assertDoesNotExist()
        composeTestRule.onNodeWithText(timeLeft2).assertDoesNotExist()
        composeTestRule.onNodeWithContentDescription(stopBtnDesc).assertDoesNotExist()
        composeTestRule.onNodeWithText("Finished").assertIsDisplayed()
    }

    // TODO 2:
    /**
     * COUNTING DOWN -> PAUSE -> RESUME -> FINISH
     */
}
