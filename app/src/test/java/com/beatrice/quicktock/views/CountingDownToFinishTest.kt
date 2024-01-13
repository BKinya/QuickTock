package com.beatrice.quicktock.views

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.printToLog
import com.beatrice.quicktock.MainActivity
import com.beatrice.quicktock.di.appModule
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.robolectric.RobolectricTestRunner
import org.robolectric.shadows.ShadowLog

@RunWith(RobolectricTestRunner::class)
class CountingDownToFinishTest {
    /**
     * FIXME: Write integration test for when the app moves from
     *       [CountingDownStarted] state to [CountingDown] state
     *       and finally  [Finished] state
     */
    /**
     * Start test should be [Counting Down started]
     * send a [countinueCountingDown] => countingDown state
     * send on Finish event to the VM => finished state
     */

    /**
     * Question is
     * How do I override the initial state of the state machine
     * Or how do I override the dependency
     * maybe during setUp
     */
    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Before
    @Throws(Exception::class)
    fun setUp() {
        ShadowLog.stream = System.out // Redirect Logcat to console
        /**
         * Fast-forward the state of state machine to [CountDownStarted]
         */
        with(composeTestRule.activity.timerViewModel) {
            onStartCountDown(TEST_DURATION)
        }
        stopKoin()
        startKoin {
            modules(appModule)
        }
    }

    fun tearDown() {
        stopKoin()
    }

    @Test
    fun test1() {
        // Arrange
        // make sure you do not have any buttons
        // Act
        // send the event to the state machine
        // Assert
        launchTimerScreen(composeTestRule) {
            composeTestRule.activity.timerViewModel.onContinueCountingDown(10)
            composeTestRule.onRoot().printToLog("TAAAAG1")
        } verify {
        }
    }
}
