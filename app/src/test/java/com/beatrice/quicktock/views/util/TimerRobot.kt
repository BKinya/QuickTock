package com.beatrice.quicktock.views.util

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.beatrice.quicktock.MainActivity
import com.beatrice.quicktock.R
import com.beatrice.quicktock.views.TEST_DURATION

class TimerRobot(
    private val composeTestRule: AndroidComposeTestRule<ActivityScenarioRule<MainActivity>, MainActivity>
) {
    fun timerDurationTextIsPresent() {
        val duration = composeTestRule.activity.getString(R.string.durationLabel, TEST_DURATION)
        composeTestRule.onNodeWithText(duration).assertIsDisplayed()
    }

    fun playButtonIsPresentAndClick() {
        val playButtonDesc = composeTestRule.activity.getString(R.string.playBtnDesc)
        composeTestRule.onNodeWithContentDescription(playButtonDesc)
            .assertIsDisplayed()
            .performClick()
    }

    infix fun verify(block: Verification.() -> Unit): Verification {
        return Verification(composeTestRule).apply(block)
    }

    fun stopButtonIsPresent() {
        val stopBtnDesc = composeTestRule.activity.getString(R.string.stopButtonDesc)
        composeTestRule.onNodeWithContentDescription(stopBtnDesc)
            .assertIsDisplayed()
    }

    fun pauseButtonIsPresentAndClick() {
        val pauseBtnDesc = composeTestRule.activity.getString(R.string.pauseButtonDesc)
        composeTestRule.onNodeWithContentDescription(pauseBtnDesc)
            .assertIsDisplayed()
            .performClick()
    }

    fun setTimerTitleIsDisplayed() {
        val setTimerTitle = composeTestRule.activity.getString(R.string.setTimerTitle)
        composeTestRule.onNodeWithText(setTimerTitle).assertIsDisplayed()
    }

    fun durationInputIsDisplayed() {
        composeTestRule.onNodeWithText("0.0"). assertIsDisplayed()
    }


    fun saveButtonIsDisplayed() {
        val saveLabel = composeTestRule.activity.getString(R.string.saveLabel)
        composeTestRule.onNodeWithText(saveLabel).assertIsDisplayed()
    }

    fun enterDuration() {
        composeTestRule.onNodeWithText("0.0").performTextInput("10")
    }

    fun clickSaveButton() {
        val saveLabel = composeTestRule.activity.getString(R.string.saveLabel)
        composeTestRule.onNodeWithText(saveLabel).performClick()
    }
}