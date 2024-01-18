package com.beatrice.quicktock.views.util

import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.beatrice.quicktock.MainActivity

/**
 * Launch screen and verify the appropriate view
 * is displayed for a given state
 */
fun launchAndVerifyTimerScreen(
    composeTestRule: AndroidComposeTestRule<ActivityScenarioRule<MainActivity>, MainActivity>,
    block: TimerRobot.() -> Unit
): TimerRobot {
    return TimerRobot(
        composeTestRule
    ).apply(block)
}

/**
 * Similar idling resources
 */
fun waitUntilConditionMet(
    composeTestRule: AndroidComposeTestRule<ActivityScenarioRule<MainActivity>, MainActivity>,
    block: IdlingRobot.() -> Unit
): IdlingRobot {
    return IdlingRobot(composeTestRule).apply(block)
}

fun performAction(
    composeTestRule: AndroidComposeTestRule<ActivityScenarioRule<MainActivity>, MainActivity>,
    block: ActionRobot.() -> Unit
): ActionRobot {
    return ActionRobot(composeTestRule).apply(block)
}
