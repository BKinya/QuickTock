package com.beatrice.quicktock.ui

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.beatrice.quicktock.MainActivity
import com.beatrice.quicktock.ui.stateMachine.UiState
import org.junit.Rule
import org.junit.Test


class TimerScreenTest {
    @get: Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun testStartCountingDown(){
        launchTimerScreen(UiState.TimerSet(5), composeTestRule = composeTestRule){
            timerScreenIsPresent()
            clickPlayButton()
        }verify{
            countingDownScreenIsPresent()
            playButtonNotPresent()
            pauseButtonPresent()
            stopButtonPresent()
        }

    }



}