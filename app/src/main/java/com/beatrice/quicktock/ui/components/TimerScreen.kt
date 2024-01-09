package com.beatrice.quicktock.ui.components

import androidx.compose.material3.Scaffold
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

            is UiState.StartCountingDown->{ // TODO: Update this screen to something fancy
                // some animation would be awesome
                CountDownTimer(
                    onPlayButtonClicked = onPlayButtonClicked,
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
