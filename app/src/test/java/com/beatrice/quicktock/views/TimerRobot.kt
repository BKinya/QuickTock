package com.beatrice.quicktock.views

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasContentDescription
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.beatrice.quicktock.MainActivity
import com.beatrice.quicktock.R

fun launchTimerScreen(
    composeTestRule: AndroidComposeTestRule<ActivityScenarioRule<MainActivity>, MainActivity>,
    block: TimerRobot.() -> Unit,
): TimerRobot {
    return TimerRobot(
        composeTestRule,
    ).apply(block)
}

class TimerRobot(
    private val composeTestRule: AndroidComposeTestRule<ActivityScenarioRule<MainActivity>, MainActivity>,
) {
    fun timerScreenIsPresent() {
        val duration = composeTestRule.activity.getString(R.string.durationLabel, 10)
        composeTestRule.onNodeWithText(duration).assertIsDisplayed()
    }

    fun clickPlayButton() {
        val playButtonDesc = composeTestRule.activity.getString(R.string.playBtnDesc)
        composeTestRule.onNodeWithContentDescription(playButtonDesc)
            .assertIsDisplayed()
            .performClick()
    }

    infix fun verify(block: StateTransitionVerification.() -> Unit): StateTransitionVerification {
        return StateTransitionVerification(composeTestRule).apply(block)
    }
}

@OptIn(ExperimentalTestApi::class)
class StateTransitionVerification(
    private val composeTestRule: AndroidComposeTestRule<ActivityScenarioRule<MainActivity>, MainActivity>,
) {
    fun countingDownScreenIsPresent() {
        val timeLeft = composeTestRule.activity.getString(R.string.durationLabel, 10)
        composeTestRule.onNodeWithText(timeLeft).assertIsDisplayed()
    }

    fun pauseButtonPresent() {
        val pauseBtnDesc = composeTestRule.activity.getString(R.string.pauseButtonDesc)
        composeTestRule.onNodeWithContentDescription(pauseBtnDesc).assertIsDisplayed()
    }

    fun stopButtonPresent() {
        val stopBtnDesc = composeTestRule.activity.getString(R.string.stopButtonDesc)
        composeTestRule.onNodeWithContentDescription(stopBtnDesc).assertIsDisplayed()
    }

    fun playButtonNotPresent() {
        val playButtonDesc = composeTestRule.activity.getString(R.string.playBtnDesc)
        composeTestRule.onNodeWithContentDescription(playButtonDesc).assertDoesNotExist()
    }

    fun countDownFinishedScreenPresent() {
        composeTestRule.onNodeWithText("Finished").assertIsDisplayed()
    }

    fun waitUntilNodeExists(matcher: SemanticsMatcher) {
        composeTestRule.waitUntilDoesNotExist(matcher)
    }
}
