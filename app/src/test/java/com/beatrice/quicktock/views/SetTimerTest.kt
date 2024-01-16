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
        // will checking the state automatically happen.. we'll see
        launchTimerScreen(composeTestRule) {
            setTimerTitleIsDisplayed()
            durationInputIsDisplayed()
            saveButtonIsDisplayed()
            // enter text
            enterDuration()
            // click button
            clickSaveButton()
            // move to the next state
        }verify {


        }

    }

}