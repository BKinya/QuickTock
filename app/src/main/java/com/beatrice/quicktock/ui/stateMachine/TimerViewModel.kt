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
import kotlinx.coroutines.launch

/**
 * TODO: How will I test this state machine ... think about it when done with the implementaion
 */
class TimerViewModel(
    private val timerRepository: TimerRepository,
    private val dispatcher: CoroutineDispatcher,
    private val stateMachine: StateMachine<UiState, UiEvent, SideEffect>
) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState> = MutableStateFlow(UiState.TimerSet(60))
    val uiState = _uiState.asStateFlow()

    private val transitionSharedFlow = MutableSharedFlow<StateMachine.Transition<UiState, UiEvent, SideEffect>>()

    init {
        observeTransitions()
    }

    fun onStartCountDown(duration: Int) {
        viewModelScope.launch {
            val transition =
                stateMachine.transition(UiEvent.OnStart(duration))// This method returns and instance of Transition
            transitionSharedFlow.emit(transition)

        }
        /**
         * Only from the transition instance could I get latest state, and any associated side effects
         *
         *
         * Maybe make the transition observable...
         * here do observer it
         */
    }

    fun onContinueCountingDown(timeLeft: Int) {
        viewModelScope.launch {
            val transition = stateMachine.transition(UiEvent.OnContinueCountDown(timeLeft))
            transitionSharedFlow.emit(transition)
        }
    }

    fun onFinishCountingDown() {
        viewModelScope.launch {
            val transition = stateMachine.transition(UiEvent.OnFinish)
            transitionSharedFlow.emit(transition)
        }

    }

    fun observeTransitions() {
        viewModelScope.launch {
            transitionSharedFlow.asSharedFlow().collectLatest {
                val validTransition = it as StateMachine.Transition.Valid
                _uiState.value = validTransition.toState
                val sideEffect = validTransition.sideEffect
                Log.d("LATEST_STAAAATE", " is $it aamd ${_uiState.value}")


                when (sideEffect) {
                    is SideEffect.DoCountDown -> {
                        countDown(sideEffect.duration)
                    }
                    else -> {}
                }
            }
        }
    }

    fun countDown(duration: Int) {
        viewModelScope.launch {
            val timeLeft = timerRepository.countDown(duration)
            if (timeLeft > 0) {
                onContinueCountingDown(timeLeft)
            } else {
                onFinishCountingDown()
            }
        }
    }

}
