package com.beatrice.quicktock.views

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.hasContentDescription
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.beatrice.quicktock.MainActivity
import com.beatrice.quicktock.R
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

//            val pauseBtnDesc = composeTestRule.activity.getString(R.string.pauseButtonDesc)
//            waitUntilNodeExists(hasContentDescription(pauseBtnDesc))
//            pauseButtonPresent()
//            stopButtonPresent()
////            countDownFinishedScreenPresent()
        }
    }




}
