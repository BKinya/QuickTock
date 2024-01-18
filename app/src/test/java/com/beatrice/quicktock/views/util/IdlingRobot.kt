package com.beatrice.quicktock.views.util

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.hasContentDescription
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.beatrice.quicktock.MainActivity
import com.beatrice.quicktock.R

@OptIn(ExperimentalTestApi::class)
class IdlingRobot(
    private val composeTestRule: AndroidComposeTestRule<ActivityScenarioRule<MainActivity>, MainActivity>
) {

    fun timeLeftTextIsDisplayed_01() {
        val timeLeft = composeTestRule.activity.getString(R.string.durationLabel, com.beatrice.quicktock.data.fake.TIME_LEFT_ONE)
        composeTestRule.waitUntilExactlyOneExists(hasText(timeLeft), 4000)
    }
    fun pauseButtonIsNotPresent() {
        val pauseBtnDesc = composeTestRule.activity.getString(R.string.pauseButtonDesc)
        composeTestRule.waitUntilDoesNotExist(hasContentDescription(pauseBtnDesc), 4000)
    }

    infix fun verify(block: TimerVerification.() -> Unit): TimerVerification {
        return TimerVerification(composeTestRule).apply(block)
    }

    fun timeLeftTextIsDisplayed_02() {
        val timeLeft = composeTestRule.activity.getString(R.string.durationLabel, com.beatrice.quicktock.data.fake.TIME_LEFT_TWO)
        composeTestRule.waitUntilExactlyOneExists(hasText(timeLeft), 4000)

    }
}
