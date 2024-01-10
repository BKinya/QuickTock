package com.beatrice.quicktock.ui.components

import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.beatrice.quicktock.ui.stateMachine.UiState

@Composable
fun TimerScreen(
    uiState: UiState,
    modifier: Modifier = Modifier,
    onPlayButtonClicked: (Int) -> Unit,
) {
    Scaffold(
        modifier = modifier,
    ) { _ ->

        when (uiState) {
            is UiState.Idle -> {
            }

            is UiState.CountDownStarted -> { // todo do some playful animation
                CountDownTimer(
                    onPlayButtonClicked = {},
                    duration = uiState.duration,
                    showPauseButton = false,
                    showStopButton = false,
                    showPlayButton = false,
                )
            }

            is UiState.CountingDown -> {
                CountDownTimer(
                    onPlayButtonClicked = onPlayButtonClicked,
                    duration = uiState.timeLeft,
                    showPauseButton = true,
                    showStopButton = true,
                    showPlayButton = false,
                )
            }

            is UiState.Paused -> {
            }

            is UiState.Finished -> {
                // Show some fancy animations
                Text("Finished")
            }
            is UiState.TimerSet -> {
                CountDownTimer(
                    onPlayButtonClicked = onPlayButtonClicked,
                    duration = uiState.duration,
                    showPlayButton = true,
                )
            }
            else -> {
            }
        }
    }
}
