package com.beatrice.quicktock.views.util

import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.beatrice.quicktock.MainActivity
import com.beatrice.quicktock.R

class ActionRobot(
    private val composeTestRule: AndroidComposeTestRule<ActivityScenarioRule<MainActivity>, MainActivity>
) {

    fun clickResumeButton() {
        val resumeBtnDesc = composeTestRule.activity.getString(R.string.resumeButtonDesc)
        composeTestRule.onNodeWithContentDescription(resumeBtnDesc).performClick()
    }
    infix fun verify(block: TimerVerification.() -> Unit): TimerVerification {
        return TimerVerification(composeTestRule).apply(block)
    }

    fun clickStopButton() {
        val stopBtnDesc = composeTestRule.activity.getString(R.string.stopButtonDesc)
        composeTestRule.onNodeWithContentDescription(stopBtnDesc)
            .performClick()
    }
}
