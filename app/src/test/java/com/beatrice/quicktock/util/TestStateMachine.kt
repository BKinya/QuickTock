package com.beatrice.quicktock.util

import com.beatrice.quicktock.ui.stateMachine.SideEffect
import com.beatrice.quicktock.ui.stateMachine.UiEvent
import com.beatrice.quicktock.ui.stateMachine.UiState
import com.tinder.StateMachine

fun createTestStateMachine(): StateMachine<UiState, UiEvent, SideEffect> =
    StateMachine.create {
        initialState(UiState.TimerSet(5))

        state<UiState.Idle> {}

        state<UiState.TimerSet> {
            on<UiEvent.OnStart> { event ->
                transitionTo(
                    UiState.CountDownStarted(event.duration),
                    SideEffect.DoCountDown(duration = event.duration),
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
                    UiState.CountingDown(event.timeLeft),
                )
            }
            on<UiEvent.OnPause> {
                transitionTo(UiState.Paused(this.timeLeft))
            }

            on<UiEvent.OnDismiss> {
                transitionTo(UiState.Idle)
            }
            on<UiEvent.OnFinish> {
                transitionTo(UiState.Finished)
            }
        }
        state<UiState.Paused> {
            on<UiEvent.OnResume> {
                transitionTo(UiState.CountingDown(0))
            }

            on<UiEvent.OnDismiss> {
                transitionTo(UiState.Idle)
            }
        }
        state<UiState.Finished> {
            on<UiEvent.OnRestart> {
                transitionTo(UiState.CountingDown(60))
            }

            on<UiEvent.OnDismiss> {
                transitionTo(UiState.Idle)
                // TODO: Update shared preferences
            }
        }
    }
