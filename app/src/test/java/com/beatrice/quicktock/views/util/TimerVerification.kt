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
}