package com.beatrice.quicktock.di

import com.beatrice.quicktock.ui.stateMachine.SideEffect
import com.beatrice.quicktock.ui.stateMachine.UiEvent
import com.beatrice.quicktock.ui.stateMachine.UiState
import com.tinder.StateMachine

fun createStateMachine(): StateMachine<UiState, UiEvent, SideEffect> {
    return StateMachine.create {
        initialState(UiState.Idle)

        state<UiState.Idle> {
            on<UiEvent.OnStart> {
                dontTransition(SideEffect.CheckTimer)
            }

            on<UiEvent.OnTimerSet> { event ->
                transitionTo(UiState.TimerSet(event.duration))
            }

            on<UiEvent.OnSetTimer> {
                transitionTo(UiState.SettingTimer)
            }
        }

        state<UiState.SettingTimer> {
            on<UiEvent.OnSaveTimer> { event ->
                dontTransition(SideEffect.SaveTimer(event.duration))
            }
            on<UiEvent.OnTimerSet> { event ->
                transitionTo(UiState.TimerSet(event.duration))
            }
        }

        state<UiState.TimerSet> {
            on<UiEvent.OnStartCountDown> { event ->
                transitionTo(
                    UiState.CountDownStarted(event.duration),
                    SideEffect.DoCountDown(duration = event.duration)
                )
            }
        }

        state<UiState.CountDownStarted> {
            on<UiEvent.OnContinueCountDown> { event ->
                transitionTo(UiState.CountingDown(event.timeLeft))
            }
        }
        state<UiState.CountingDown> {
            on<UiEvent.OnContinueCountDown> { event ->
                transitionTo(
                    UiState.CountingDown(event.timeLeft)
                )
            }
            on<UiEvent.OnPause> { event ->
                transitionTo(UiState.Paused(event.timeLeft))
            }

            on<UiEvent.OnStop> {
                transitionTo(UiState.Idle)
            }
            on<UiEvent.OnFinish> {
                transitionTo(UiState.Finished)
            }
        }
        state<UiState.Paused> {
            on<UiEvent.OnResume> { event ->
                transitionTo(UiState.CountingDown(event.timeLeft), SideEffect.DoCountDown(event.timeLeft))
            }

            on<UiEvent.OnStop> {
                transitionTo(UiState.Idle)
            }
        }
        state<UiState.Finished> {
            on<UiEvent.OnRestart> {
                transitionTo(UiState.CountingDown(60))
            }

            on<UiEvent.OnStop> {
                transitionTo(UiState.Idle)
            }
        }
    }
}
