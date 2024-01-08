package com.beatrice.quicktock.ui.stateMachine

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.beatrice.quicktock.data.repository.TimerRepository
import com.tinder.StateMachine
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class TimerViewModel(
    private val timerRepository: TimerRepository,
    val dispatcher: CoroutineDispatcher,
) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState> = MutableStateFlow(UiState.TimerSet(60))
    val uiState = _uiState.asStateFlow()

    private lateinit var stateMachine: StateMachine<UiState, UiEvent, SideEffect>

    init {
        initStateMachine()
    }

    private fun initStateMachine() {
        stateMachine =
            StateMachine.create<UiState, UiEvent, SideEffect> {
                initialState(UiState.TimerSet(60))

                state<UiState.Idle> {
                    on<UiEvent.OnStart> {
                        transitionTo(
                            UiState.CountingDown(60),
                            SideEffect.DoCountDown(duration = 60),
                        )
                    }
                }

                state<UiState.TimerSet> {
                    on<UiEvent.OnStart> { event ->
                        transitionTo(
                            UiState.CountingDown(event.duration),
                            SideEffect.DoCountDown(duration = event.duration),
                        )
                    }
                }

                state<UiState.CountingDown> {
                    on<UiEvent.OnContinueCountDown> { event ->
                        transitionTo(
                            UiState.CountingDown(event.timeLeft),
                            SideEffect.DoCountDown(duration = event.timeLeft),
                        )
                    }
                    on<UiEvent.OnPause> {
                        transitionTo(UiState.Paused)
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

                onTransition {
                    // TODO: What to do with the invalid transitions????
                    val validTransition = it as StateMachine.Transition.Valid
                    val sideEffect = validTransition.sideEffect
                    viewModelScope.launch(dispatcher) {
                        _uiState.value = stateMachine.state
                        when (sideEffect) {
                            is SideEffect.DoCountDown -> {
                                val timeLeft = timerRepository.countDown(sideEffect.duration)
                                if (timeLeft > 0) {
                                    sendEvent(UiEvent.OnContinueCountDown(timeLeft))
                                } else {
                                    sendEvent(UiEvent.OnFinish)
                                }
                            }

                            else -> {
                            }
                        }
                    }
                }
            }
    }

    /**
     * Unit test this: What will I be testing
     *
     */

    fun sendEvent(uiEvent: UiEvent) {
        stateMachine.transition(uiEvent)
    }
}
