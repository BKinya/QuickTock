package com.beatrice.quicktock.views

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.beatrice.quicktock.MainActivity
import com.beatrice.quicktock.R

fun launchTimerScreen(
    composeTestRule: AndroidComposeTestRule<ActivityScenarioRule<MainActivity>, MainActivity>,
    block: TimerRobot.() -> Unit
): TimerRobot {
    return TimerRobot(
        composeTestRule
    ).apply(block)
}

class TimerRobot(
    private val composeTestRule: AndroidComposeTestRule<ActivityScenarioRule<MainActivity>, MainActivity>
) {
    fun durationTextIsPresent() {
        val duration = composeTestRule.activity.getString(R.string.durationLabel, TEST_DURATION)
        composeTestRule.onNodeWithText(duration).assertIsDisplayed()
    }

    fun playButtonIsPresentAndClick() {
        val playButtonDesc = composeTestRule.activity.getString(R.string.playBtnDesc)
        composeTestRule.onNodeWithContentDescription(playButtonDesc)
            .assertIsDisplayed()
            .performClick()
    }

    infix fun verify(block: StateTransitionVerification.() -> Unit): StateTransitionVerification {
        return StateTransitionVerification(composeTestRule).apply(block)
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
}

class StateTransitionVerification(
    private val composeTestRule: AndroidComposeTestRule<ActivityScenarioRule<MainActivity>, MainActivity>
) {
    fun countingDownScreenIsPresent() {
        val timeLeft = composeTestRule.activity.getString(R.string.durationLabel, TEST_DURATION)
        composeTestRule.onNodeWithText(timeLeft).assertIsDisplayed()
    }

    fun pauseButtonPresent() {
        val pauseBtnDesc = composeTestRule.activity.getString(R.string.pauseButtonDesc)
        composeTestRule.onNodeWithContentDescription(pauseBtnDesc).assertIsDisplayed()
    }

    fun stopButtonIsPresent() {
        val stopBtnDesc = composeTestRule.activity.getString(R.string.stopButtonDesc)
        composeTestRule.onNodeWithContentDescription(stopBtnDesc).assertIsDisplayed()
    }

    fun playButtonNotPresent() {
        val playButtonDesc = composeTestRule.activity.getString(R.string.playBtnDesc)
        composeTestRule.onNodeWithContentDescription(playButtonDesc).assertDoesNotExist()
    }

    fun pauseButtonNotPresent() {
        val pauseBtnDesc = composeTestRule.activity.getString(R.string.pauseButtonDesc)
        composeTestRule.onNodeWithContentDescription(pauseBtnDesc).assertDoesNotExist()
    }

    fun resumeButtonIsPresent() {
        val resumeBtnDesc = composeTestRule.activity.getString(R.string.resumeButtonDesc)
        composeTestRule.onNodeWithContentDescription(resumeBtnDesc).assertIsDisplayed()
    }
}
