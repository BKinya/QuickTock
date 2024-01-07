package com.beatrice.quicktock.views

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.core.app.ActivityScenario
import com.beatrice.quicktock.MainActivity
import com.beatrice.quicktock.R

fun launchTimerScreen(
    /**
     * This state is not doing much right now coz I'm using the state created in the real VM
     * Next thing coz eventually I have to do it is to pass the state machine as a variable
     * and maybe make the transition as infix function or sth similar
     */

    composeTestRule: ComposeContentTestRule,
    block: TimerRobot.() -> Unit,
): TimerRobot{
    return TimerRobot(
        composeTestRule,
    ).apply (block)
}

class TimerRobot(
    private val composeTestRule: ComposeContentTestRule,
){

    private lateinit var activityScenario: ActivityScenario<MainActivity>
    fun startMainActivity(){
      activityScenario =  ActivityScenario.launch(MainActivity::class.java)
    }

    fun timerScreenIsPresent() {
        activityScenario.onActivity {
            val duration = it.getString(R.string.durationLabel, 60)
            composeTestRule.onNodeWithText(duration).assertIsDisplayed()
        }



    }
    fun clickPlayButton(){
        activityScenario.onActivity {
            val playButtonDesc = it.getString(R.string.playBtnDesc)
            composeTestRule.onNodeWithContentDescription(playButtonDesc)
                .assertIsDisplayed()
                .performClick()
        }


    }
    infix fun verify(block: StateTransitionVerification.() -> Unit) : StateTransitionVerification{
        return StateTransitionVerification(composeTestRule, activityScenario).apply(block)
    }


}

class StateTransitionVerification(
    private val composeTestRule: ComposeContentTestRule,
    private val activityScenario: ActivityScenario<MainActivity>
){
    fun countingDownScreenIsPresent() {

        activityScenario.onActivity {
            val timeLeft =it.getString(R.string.durationLabel, 47)
            composeTestRule.onNodeWithText(timeLeft).assertIsDisplayed()
        }
    }

    fun pauseButtonPresent() {
        activityScenario.onActivity {
            val pauseBtnDesc = it.getString(R.string.pauseButtonDesc)
            composeTestRule.onNodeWithContentDescription(pauseBtnDesc).assertIsDisplayed()
        }
    }

    fun stopButtonPresent() {
        activityScenario.onActivity {
            val stopBtnDesc = it.getString(R.string.stopButtonDesc)
            composeTestRule.onNodeWithContentDescription(stopBtnDesc).assertIsDisplayed()
        }
    }

    fun playButtonNotPresent() {
        activityScenario.onActivity {
            val playButtonDesc = it.getString(R.string.playBtnDesc)
            composeTestRule.onNodeWithContentDescription(playButtonDesc).assertDoesNotExist()
        }
    }


}