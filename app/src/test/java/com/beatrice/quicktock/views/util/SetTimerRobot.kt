package com.beatrice.quicktock.views.util

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasText
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

fun launchTimerScreen(
    composeTestRule: AndroidComposeTestRule<ActivityScenarioRule<MainActivity>, MainActivity>,
    block: SetTimerRobot.() -> Unit
): SetTimerRobot {
    return SetTimerRobot(
        composeTestRule
    ).apply(block)
}

fun sendUiEvent(// TODO: I'll not need this. So I should remove it
    composeTestRule: AndroidComposeTestRule<ActivityScenarioRule<MainActivity>, MainActivity>,
    block: SetTimerRobot.() -> Unit
): SetTimerRobot {
    return SetTimerRobot(composeTestRule).apply(block)
}

class SetTimerRobot(
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

    infix fun verify(block: SetTimerVerification.() -> Unit): SetTimerVerification {
        return SetTimerVerification(composeTestRule).apply(block)
    }

}


class SetTimerVerification(
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
}

