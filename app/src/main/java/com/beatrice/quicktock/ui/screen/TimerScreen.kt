package com.beatrice.quicktock.ui.screen

import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.beatrice.quicktock.ui.components.CountDownTimerComponent
import com.beatrice.quicktock.ui.components.SetTimerComponent
import com.beatrice.quicktock.ui.stateMachine.UiState

@Composable
fun TimerScreen(
    uiState: UiState,
    modifier: Modifier = Modifier,
    onStart: () -> Unit,
    onPlayButtonClicked: (Int) -> Unit,
    onPauseButtonClicked: (Int) -> Unit,
    onResumeButtonClicked: (Int) -> Unit,
    onSaveTimerBtnClicked: (Int) -> Unit,
    onStopButtonClicked: () -> Unit
) {
    Scaffold(
        modifier = modifier
    ) { _ ->
        println("state => $uiState")

        when (uiState) {
            is UiState.Idle -> {
                // show splash screen or something similar
                onStart()
            }

            is UiState.SettingTimer -> {
                SetTimerComponent(
                    onSaveTimerBtnClicked = onSaveTimerBtnClicked
                )
            }

            is UiState.TimerSet -> {
                CountDownTimerComponent(
                    onPlayButtonClicked = onPlayButtonClicked,
                    duration = uiState.duration,
                    showPlayButton = true
                )
            }

            is UiState.CountDownStarted -> { // todo do some playful animation
                CountDownTimerComponent(
                    duration = uiState.duration
                )
            }

            is UiState.CountingDown -> {
                CountDownTimerComponent(
                    onPauseButtonClicked = onPauseButtonClicked,
                    onStopButtonClicked = onStopButtonClicked,
                    duration = uiState.timeLeft,
                    showPauseButton = true,
                    showStopButton = true
                )
            }

            is UiState.Paused -> {
                CountDownTimerComponent(
                    duration = uiState.timeLeft,
                    showStopButton = true,
                    showResumeButton = true,
                    onResumeButtonClicked = onResumeButtonClicked,
                    onStopButtonClicked = onStopButtonClicked
                )
            }

            is UiState.Finished -> {
                // Show some fancy animations
                Text("Finished")
            }
        }
    }
}
