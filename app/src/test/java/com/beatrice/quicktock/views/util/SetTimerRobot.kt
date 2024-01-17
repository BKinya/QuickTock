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
import com.beatrice.quicktock.views.TEST_DURATION

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

    //  Remove everything after this

    fun timerDurationTextIsPresent() {
        val duration = composeTestRule.activity.getString(R.string.durationLabel, TEST_DURATION)
        composeTestRule.onNodeWithText(duration).assertIsDisplayed()
    }

    fun playButtonIsPresentAndClick() {
        val playButtonDesc = composeTestRule.activity.getString(R.string.playBtnDesc)
        composeTestRule.onNodeWithContentDescription(playButtonDesc)
            .assertIsDisplayed()
            .performClick()
    }

    infix fun verify(block: SetTimerVerification.() -> Unit): SetTimerVerification {
        return SetTimerVerification(composeTestRule).apply(block)
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

@OptIn(ExperimentalTestApi::class)
class SetTimerVerification(
    private val composeTestRule: AndroidComposeTestRule<ActivityScenarioRule<MainActivity>, MainActivity>
) {
    fun timerDurationTextIsPresent() {
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

    fun waitForTimeLeftTextToUpdate() {
        val timeLeft2 = composeTestRule.activity.getString(R.string.durationLabel, 9)
        composeTestRule.waitUntilExactlyOneExists(hasText(timeLeft2), timeoutMillis = 3000)
    }

    fun waitUntilFinishTextIsDispalyed() {
        val finishedText = composeTestRule.activity.getString(R.string.finished)
        composeTestRule.waitUntilExactlyOneExists(hasText(finishedText))
    }

    fun stopButtonNotPresent() {
        val stopBtnDesc = composeTestRule.activity.getString(R.string.stopButtonDesc)
        composeTestRule.onNodeWithContentDescription(stopBtnDesc).assertDoesNotExist()
    }

    fun resumeButtonIsNotPresent() {
        val resumeBtnDesc = composeTestRule.activity.getString(R.string.resumeButtonDesc)
        composeTestRule.onNodeWithContentDescription(resumeBtnDesc).assertDoesNotExist()
    }

    fun playButtonIsDisplayed() {
        val playButtonDesc = composeTestRule.activity.getString(R.string.playBtnDesc)
        composeTestRule.onNodeWithContentDescription(playButtonDesc).assertIsDisplayed()
    }
}

