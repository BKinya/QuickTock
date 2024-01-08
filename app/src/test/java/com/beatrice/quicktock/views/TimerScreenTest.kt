package com.beatrice.quicktock.views

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.beatrice.quicktock.MainActivity
import com.beatrice.quicktock.TestApplication
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.test.KoinTest
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.robolectric.shadows.ShadowLog

@RunWith(RobolectricTestRunner::class)
@Config(application = TestApplication::class)
class TimerScreenTest : KoinTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Before
    @Throws(Exception::class)
    fun setUp() {
        ShadowLog.stream = System.out // Redirect Logcat to console
    }

    @Test
    fun testStartCountingDown() {
        launchTimerScreen(composeTestRule = composeTestRule) {
            timerScreenIsPresent()
            clickPlayButton()
        } verify {
            countingDownScreenIsPresent()
            playButtonNotPresent()
            pauseButtonPresent()
            stopButtonPresent()
        }
    }

    /**
     * When in [CountingDown] state, clicking pause button should transition to [Paused] State
     * In pause state show resumed and stop buttons
     */
    /**
     * Rememeber your tests are independent
     * so I need a new state for every test
     */
//    @Test
//    fun testPausedState(){
//        launchTimerScreen(composeTestRule = composeTestRule){
//            countingDownScreenIsPresent()// this one has to come from state
//            clickPauseButton()
//        } verify {
//            // Counting down has stopped
//            // Resume and stop buttons are present
//        }
//
//    }
}
