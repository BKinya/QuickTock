package com.beatrice.quicktock.ui.stateMachine

/**
 * I'll first read  and write the value from datastore
 * Then Implement setting timer functionality and make sure I could count down till the end
 * Then I'll try the configuration changes and process death shenanigans
 * and see how it goes.
 *
 *
 */
sealed interface UiState {
    data object Idle : UiState

    data object SettingTimer : UiState

    @JvmInline value class TimerSet(val duration: Int) : UiState

    @JvmInline value class CountDownStarted(val duration: Int) : UiState

    @JvmInline value class CountingDown(val timeLeft: Int) : UiState

    @JvmInline value class Paused(val timeLeft: Int) : UiState

    data object Finished : UiState
}

sealed interface UiEvent {

    data object OnStart : UiEvent
    data object OnSetTimer : UiEvent

    @JvmInline value class OnSaveTimer(val duration: Int) : UiEvent

    @JvmInline value class OnTimerSet(val duration: Int) : UiEvent

    @JvmInline value class OnStartCountDown(val duration: Int) : UiEvent

    @JvmInline value class OnContinueCountDown(val timeLeft: Int) : UiEvent

    data object OnFinish : UiEvent

    @JvmInline value class OnPause(val timeLeft: Int) : UiEvent

    data object OnStop : UiEvent

    data object OnRestart : UiEvent

    @JvmInline value class OnResume(val timeLeft: Int) : UiEvent
}

sealed interface SideEffect {
    data object CheckTimer : SideEffect

    @JvmInline value class SaveTimer(val duration: Int) : SideEffect

    @JvmInline value class DoCountDown(val duration: Int) : SideEffect

    data object Restarting : SideEffect

    data object Reset : SideEffect
}
