package com.beatrice.quicktock.views

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.beatrice.quicktock.MainActivity
import com.beatrice.quicktock.TestApplication
import com.beatrice.quicktock.views.util.launchTimerScreen
import org.junit.After
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.stopKoin
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(application = TestApplication::class)
class SetTimerTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    /**
     * IDLE => Send setTimer event.. verify the UI
     * Then type the numbers... click a button then verify the numbers
     */

    @After
    fun tearUp() {
        stopKoin()
    }

    /**
     * when state is SettingTimer show set timer ui
     */

    @Test
    fun `when state is SettingTimer show ui to set the timer`() {
        // TODO: Rename
        launchTimerScreen(composeTestRule) {
            setTimerTitleIsDisplayed()
            durationInputIsDisplayed()
            saveButtonIsDisplayed()
            enterDuration()
            clickSaveButton()
        }verify {
            // we mighty have to wait... but this has not been implemented yet
            timerDurationTextIsPresent()
            playButtonIsDisplayed()
            // When you click the button the expectation is
            /**
             * Duration is saved successfully
             * the UI goes to TimerSet state
             */


        }

    }

}