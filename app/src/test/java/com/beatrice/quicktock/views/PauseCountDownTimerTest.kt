package com.beatrice.quicktock.views

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.beatrice.quicktock.MainActivity
import com.beatrice.quicktock.TestApplication
import com.beatrice.quicktock.di.appModule
import com.beatrice.quicktock.ui.stateMachine.UiState
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.startKoin
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
        composeTestRule.activity.timerViewModel.updateUiState(UiState.CountingDown(TEST_DURATION))
        ShadowLog.stream = System.out // Redirect Logcat to console
        stopKoin()
        startKoin {
            modules(appModule)
        }
    }

    @After
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun `when you click pause button the app moved to PAUSED state`() {
        launchTimerScreen(composeTestRule) {
            TimerDurationTextIsPresent()
            stopButtonIsPresent()
            pauseButtonIsPresentAndClick()
        } verify {
            TimerDurationTextIsPresent()
            pauseButtonNotPresent()
            stopButtonIsPresent()
            resumeButtonIsPresent()
        }
    }
    // TODO: STOP button test here
    // renaming the class
}
