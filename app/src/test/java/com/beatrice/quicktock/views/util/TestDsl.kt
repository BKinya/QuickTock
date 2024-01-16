package com.beatrice.quicktock.views.util

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.beatrice.quicktock.MainActivity
import com.beatrice.quicktock.R
import com.beatrice.quicktock.views.TEST_DURATION

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



