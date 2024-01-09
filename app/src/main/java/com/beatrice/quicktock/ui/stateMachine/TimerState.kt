package com.beatrice.quicktock.ui.stateMachine

sealed interface UiState {
    data object Idle : UiState

    @JvmInline value class TimerSet(val duration: Int) : UiState

    @JvmInline value class StartCountingDown(val duration: Int): UiState
    @JvmInline value class CountingDown(val timeLeft: Int) : UiState

    @JvmInline value class Paused(val timeLeft: Int) : UiState

    data object Finished : UiState
}

sealed interface UiEvent {
    @JvmInline value class OnStart(val duration: Int) : UiEvent

    @JvmInline value class OnContinueCountDown(val timeLeft: Int) : UiEvent

    data object OnFinish : UiEvent

    data object OnPause : UiEvent

    data object OnDismiss : UiEvent

    data object OnRestart : UiEvent

    data object OnResume : UiEvent
}

sealed interface SideEffect {

    @JvmInline value class StartCountDown(val duration: Int): SideEffect
    @JvmInline value class ContinueCountDown(val timeLeft: Int) : SideEffect

    data object Restarting : SideEffect

    data object Pause : SideEffect

    data object Stop : SideEffect

    data object Reset : SideEffect
}
