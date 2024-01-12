package com.beatrice.quicktock.ui.screen

import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.beatrice.quicktock.ui.components.CountDownTimer
import com.beatrice.quicktock.ui.stateMachine.UiState

@Composable
fun TimerScreen(
    uiState: UiState,
    modifier: Modifier = Modifier,
    onPlayButtonClicked: (Int) -> Unit,
    onPauseButtonClicked: (Int) -> Unit,
    onResumeButtonClicked: (Int)-> Unit
) {
    Scaffold(
        modifier = modifier
    ) { _ ->

        when (uiState) {
            is UiState.Idle -> {
            }

            is UiState.CountDownStarted -> { // todo do some playful animation
                CountDownTimer(
                    duration = uiState.duration
                )
            }

            is UiState.CountingDown -> {
                CountDownTimer(
                    onPauseButtonClicked = onPauseButtonClicked,
                    duration = uiState.timeLeft,
                    showPauseButton = true,
                    showStopButton = true
                )
            }

            is UiState.Paused -> {
                CountDownTimer(
                    duration = uiState.timeLeft,
                    showStopButton = true,
                    showResumeButton = true,
                    onResumeButtonClicked = onResumeButtonClicked
                )
            }

            is UiState.Finished -> {
                // Show some fancy animations
                Text("Finished")
            }
            is UiState.TimerSet -> {
                CountDownTimer(
                    onPlayButtonClicked = onPlayButtonClicked,
                    duration = uiState.duration,
                    showPlayButton = true
                )
            }
            else -> {
            }
        }
    }
}
