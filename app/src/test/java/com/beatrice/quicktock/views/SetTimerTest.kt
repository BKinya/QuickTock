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


    @After
    fun tearUp() {
        stopKoin()
    }


    @Test
    fun testSettingTimer() {
        launchTimerScreen(composeTestRule) {
            setTimerTitleIsDisplayed()
            durationInputIsDisplayed()
            saveButtonIsDisplayed()
            enterDuration()
            clickSaveButton()
        }verify {
            timerDurationTextIsPresent()
            playButtonIsDisplayed()
        }
    }
}