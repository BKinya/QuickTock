package com.beatrice.quicktock.ui.stateMachine

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.beatrice.quicktock.data.repository.TimerRepository
import com.tinder.StateMachine
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.launch

/**
 * Todo: Unit test updating the value of _uistate
 * I think now I'm fine to use advanceBy method
 * NOOOOOO good news yet
 */
class TimerViewModel(
    private val timerRepository: TimerRepository,
    private val stateMachine: StateMachine<UiState, UiEvent, SideEffect>,
    private val dispatcher: CoroutineDispatcher,
) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState> = MutableStateFlow(UiState.TimerSet(10))
    val uiState = _uiState.asStateFlow()

    private val transitionSharedFlow = MutableSharedFlow<StateMachine.Transition<UiState, UiEvent, SideEffect>>()

    init {
        observeTransitions()
    }

    fun onStartCountDown(duration: Int) {
        viewModelScope.launch(dispatcher) {
            println("Sending it out $duration")
            val transition =
                stateMachine.transition(UiEvent.OnStart(duration))
            transitionSharedFlow.emit(transition)

        }
    }

    fun onContinueCountingDown(timeLeft: Int) {
        viewModelScope.launch(dispatcher) {
            val transition = stateMachine.transition(UiEvent.OnCountingDown(timeLeft))
            transitionSharedFlow.emit(transition)
        }
    }

    fun onFinishCountingDown() {
        viewModelScope.launch(dispatcher) {
            val transition = stateMachine.transition(UiEvent.OnFinish)
            transitionSharedFlow.emit(transition)
        }

    }

    fun observeTransitions() {
        viewModelScope.launch(dispatcher) {
            transitionSharedFlow.asSharedFlow().collectLatest {

                val validTransition = it as StateMachine.Transition.Valid
                _uiState.value = validTransition.toState
                when (val sideEffect = validTransition.sideEffect) {
                    is SideEffect.DoCountDown -> {
                        countDown(sideEffect.duration)
                    }

                   else -> {}
                }

            }
        }
    }

    private fun countDown(duration: Int) {
        viewModelScope.launch(dispatcher) {
           timerRepository.doCountDown(duration)
               .onCompletion {
                   Log.d("Time_left", "completed ${it?.message}")
               }
               .collectLatest {timeLeft ->
               onContinueCountingDown(timeLeft)
           }


        }
    }
}
