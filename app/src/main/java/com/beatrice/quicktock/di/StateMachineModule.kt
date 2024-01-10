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
                    UiState.CountDownStarted(event.duration),
                    SideEffect.DoCountDown(duration = event.duration),
                )
            }
        }

        state<UiState.CountDownStarted> {
            on<UiEvent.OnCountingDown> { event ->
                transitionTo(UiState.CountingDown(event.timeLeft))
            }
        }
        state<UiState.CountingDown> {
            on<UiEvent.OnCountingDown> { event ->
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
}
