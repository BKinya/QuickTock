package com.beatrice.quicktock.ui.components

import android.util.Log
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
    Log.d("LATEST_STAAAATEC", " is $uiState")
    Scaffold(
        modifier = modifier,
    ) { _ ->

        when (uiState) {
            is UiState.Idle -> {
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
                Log.d("Latest_left", "Okay! Done")
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
