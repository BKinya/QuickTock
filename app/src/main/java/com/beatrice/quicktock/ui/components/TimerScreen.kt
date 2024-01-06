package com.beatrice.quicktock.ui.components

import android.util.Log
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.beatrice.quicktock.ui.stateMachine.UiEvent
import com.beatrice.quicktock.ui.stateMachine.UiState


@Composable
fun TimerScreen(
    uiState: UiState,
    modifier: Modifier = Modifier,
    sendUiEvent:(UiEvent) -> Unit
){
    Scaffold(
        modifier = modifier
    ) {_ ->

        Log.d("Current_State", "is ${uiState}")
        when (uiState) {
            is UiState.Idle -> {

            }

            is UiState.CountingDown -> {
                CountDownTimer(
                    sendUiEvent = sendUiEvent,
                    duration = uiState.timeLeft,
                    showPauseButton = true,
                    showStopButton =  true,
                    showPlayButton = false
                )
            }

            is UiState.Paused -> {

            }

            is UiState.Finished -> {
                // Show some fancy animations
                Log.d("Latest_left", "Okay! Done")
            }
            is UiState.TimerSet -> {
                CountDownTimer(
                    sendUiEvent = sendUiEvent,
                    duration = uiState.duration,
                    showPlayButton = true
                )
            }
            else -> {

            }
        }
    }

}