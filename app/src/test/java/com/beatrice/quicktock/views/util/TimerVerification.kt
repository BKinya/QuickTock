package com.beatrice.quicktock.views.util

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.beatrice.quicktock.MainActivity
import com.beatrice.quicktock.R
import com.beatrice.quicktock.data.fake.TEST_DURATION

class TimerVerification(
    private val composeTestRule: AndroidComposeTestRule<ActivityScenarioRule<MainActivity>, MainActivity>
) {
    fun timerDurationTextIsPresent() {
        val timeLeft = composeTestRule.activity.getString(R.string.durationLabel, TEST_DURATION)
        composeTestRule.onNodeWithText(timeLeft).assertIsDisplayed()
    }

    fun playButtonIsDisplayed() {
        val playButtonDesc = composeTestRule.activity.getString(R.string.playBtnDesc)
        composeTestRule.onNodeWithContentDescription(playButtonDesc).assertIsDisplayed()
    }

    fun playButtonNotPresent() {
        val playButtonDesc = composeTestRule.activity.getString(R.string.playBtnDesc)
        composeTestRule.onNodeWithContentDescription(playButtonDesc).assertDoesNotExist()
    }

    fun timeLeftTextIsDisplayed() {
        val timeLeft = composeTestRule.activity.getString(R.string.durationLabel, com.beatrice.quicktock.data.fake.TIME_LEFT_ONE)
        composeTestRule.onNodeWithText(timeLeft).assertIsDisplayed()
    }

    fun pauseButtonIsDisplayed() {
        val pauseBtnDesc = composeTestRule.activity.getString(R.string.pauseButtonDesc)
        composeTestRule.onNodeWithContentDescription(pauseBtnDesc)
            .assertIsDisplayed()
    }

    fun stopButtonIsDisplayed() {
        val stopBtnDesc = composeTestRule.activity.getString(R.string.stopButtonDesc)
        composeTestRule.onNodeWithContentDescription(stopBtnDesc)
            .assertIsDisplayed()
    }

    fun finishedTextIsDispalayed() {
        val finishedText = composeTestRule.activity.getString(R.string.finished)
        composeTestRule.onNodeWithText(finishedText).assertIsDisplayed()
    }

    fun pauseButtonIsNotPresent() {
        val pauseBtnDesc = composeTestRule.activity.getString(R.string.pauseButtonDesc)
        composeTestRule.onNodeWithContentDescription(pauseBtnDesc).assertDoesNotExist()
    }

    fun resumeButtonIsDisplayed() {
        val resumeBtnDesc = composeTestRule.activity.getString(R.string.resumeButtonDesc)
        composeTestRule.onNodeWithContentDescription(resumeBtnDesc).assertIsDisplayed()
    }

    fun resumeButtonNotPresent() {
        val resumeBtnDesc = composeTestRule.activity.getString(R.string.resumeButtonDesc)
        composeTestRule.onNodeWithContentDescription(resumeBtnDesc).assertDoesNotExist()
    }
}
