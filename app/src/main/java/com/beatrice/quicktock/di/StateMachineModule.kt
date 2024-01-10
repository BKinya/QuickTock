package com.beatrice.quicktock.di

import com.beatrice.quicktock.ui.stateMachine.SideEffect
import com.beatrice.quicktock.ui.stateMachine.UiEvent
import com.beatrice.quicktock.ui.stateMachine.UiState
import com.tinder.StateMachine


fun createStateMachine(): StateMachine<UiState, UiEvent, SideEffect> {
    return StateMachine.create {
        initialState(UiState.TimerSet(60))

        state<UiState.Idle> {}

        state<UiState.TimerSet> {
            on<UiEvent.OnStart> { event ->
                transitionTo(
                    UiState.StartCountingDown(event.duration),
                    SideEffect.StartCountDown(duration = event.duration),
                )
            }
        }

        state<UiState.StartCountingDown> {
            on<UiEvent.OnContinueCountDown> { event ->
                transitionTo(
                    UiState.CountingDown(event.timeLeft),
                    SideEffect.ContinueCountDown(timeLeft = event.timeLeft),
                )
            }
        }

        state<UiState.CountingDown> {
            on<UiEvent.OnContinueCountDown> { event ->
                transitionTo(
                    UiState.CountingDown(event.timeLeft),
                    SideEffect.ContinueCountDown(timeLeft = event.timeLeft),
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
}