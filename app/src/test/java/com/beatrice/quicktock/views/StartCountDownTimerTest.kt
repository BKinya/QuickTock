package com.beatrice.quicktock.views

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.beatrice.quicktock.MainActivity
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.stopKoin
import org.robolectric.RobolectricTestRunner
import org.robolectric.shadows.ShadowLog

const val TEST_DURATION = 10

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
    fun testStartCountingDown() {
        launchTimerScreen(composeTestRule = composeTestRule) {
            durationTextIsPresent()
            playButtonIsPresentAndClick()
        } verify {
            countingDownScreenIsPresent()
            playButtonNotPresent()
        }
    }
}
