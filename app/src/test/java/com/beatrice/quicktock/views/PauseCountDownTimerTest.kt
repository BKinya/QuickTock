package com.beatrice.quicktock.views

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.beatrice.quicktock.MainActivity
import com.beatrice.quicktock.TestApplication
import com.beatrice.quicktock.ui.stateMachine.UiState
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.robolectric.shadows.ShadowLog

@RunWith(RobolectricTestRunner::class)
@Config(application = TestApplication::class)
class PauseCountDownTimerTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()
    /**
     * Many problems... the UI has to be in counting down state.... initial value of the VM. It is possible to update this
     * The state machine as well.. it has to be in counting down state
     */
    /**
     * I could but what about when I have more and more tests.
     *
     * I'll do it for this test. Then I'll figure out how to fix the tests or remove them
     */
    @Before
    @Throws(Exception::class)
    fun setUp(){
        composeTestRule.activity.timerViewModel.updateUiState(UiState.CountingDown(TEST_DURATION))
        ShadowLog.stream = System.out // Redirect Logcat to console
    }
    @Test
    fun `when you click pause button the app moved to PAUSED state`(){
        launchTimerScreen(composeTestRule){
            durationTextIsPresent()
            stopButtonIsPresent()
            pauseButtonIsPresentAndClick()
        } verify {
            countingDownScreenIsPresent()
            pauseButtonNotPresent()
            stopButtonIsPresent()
            resumeButtonIsPresent()

        }

    }
    // TODO: STOP button test here
    // renaming the class
}