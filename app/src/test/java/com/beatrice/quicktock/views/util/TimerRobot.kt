package com.beatrice.quicktock.views.util

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.beatrice.quicktock.MainActivity
import com.beatrice.quicktock.R
import com.beatrice.quicktock.data.fake.TEST_DURATION

class TimerRobot(
    private val composeTestRule: AndroidComposeTestRule<ActivityScenarioRule<MainActivity>, MainActivity>
) {
    fun setTimerTitleIsDisplayed() {
        val setTimerTitle = composeTestRule.activity.getString(R.string.setTimerTitle)
        composeTestRule.onNodeWithText(setTimerTitle).assertIsDisplayed()
    }

    fun enterTimerDuration() {
        val durationTag = composeTestRule.activity.getString(R.string.durationTag)
        composeTestRule.onNodeWithTag(durationTag).assertIsDisplayed().performTextInput(TEST_DURATION.toString())
    }

    fun clickSaveBtn() {
        val saveLabel = composeTestRule.activity.getString(R.string.saveLabel)
        composeTestRule.onNodeWithText(saveLabel).assertIsDisplayed().performClick()
    }

    fun timerDurationTextIsDisplayed() {
        val duration = composeTestRule.activity.getString(R.string.durationLabel, TEST_DURATION)
        composeTestRule.onNodeWithText(duration).assertIsDisplayed()
    }

    fun playButtonIsDisplayedAndClick() {
        val playButtonDesc = composeTestRule.activity.getString(R.string.playBtnDesc)
        composeTestRule.onNodeWithContentDescription(playButtonDesc)
            .assertIsDisplayed()
            .performClick()
    }

    fun stopButtonIsDisplayed() {
        val stopBtnDesc = composeTestRule.activity.getString(R.string.stopButtonDesc)
        composeTestRule.onNodeWithContentDescription(stopBtnDesc).assertIsDisplayed()
    }

    fun pauseButtonIsDisplayedAndClick() {
        val pauseBtnDesc = composeTestRule.activity.getString(R.string.pauseButtonDesc)
        composeTestRule.onNodeWithContentDescription(pauseBtnDesc).assertIsDisplayed().performClick()
    }

    infix fun verify(block: TimerVerification.() -> Unit): TimerVerification {
        return TimerVerification(composeTestRule).apply(block)
    }
}
