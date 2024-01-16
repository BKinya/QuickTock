package com.beatrice.quicktock.views.util

import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.beatrice.quicktock.MainActivity

fun launchTimerScreen(
    composeTestRule: AndroidComposeTestRule<ActivityScenarioRule<MainActivity>, MainActivity>,
    block: TimerRobot.() -> Unit
): TimerRobot {
    return TimerRobot(
        composeTestRule
    ).apply(block)
}

fun sendUiEvent(
    composeTestRule: AndroidComposeTestRule<ActivityScenarioRule<MainActivity>, MainActivity>,
    block: TimerRobot.() -> Unit
): TimerRobot {
    return TimerRobot(composeTestRule).apply(block)
}
