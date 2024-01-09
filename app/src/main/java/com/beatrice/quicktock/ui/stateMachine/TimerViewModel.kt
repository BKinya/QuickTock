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
 * Todo: Unit test updating the value of _uistate
 * I think now I'm fine to use advanceBy method
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
                stateMachine.transition(UiEvent.OnStart(duration))

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
                when (val sideEffect = validTransition.sideEffect) {
                    is SideEffect.StartCountDown -> {
                        countDown(sideEffect.duration) // side effect should generate an event
                    }
                    is SideEffect.ContinueCountDown -> {
                        countDown(sideEffect.timeLeft)
                    }
                    else -> {}
                }
            }
        }
    }

   private fun countDown(duration: Int) {
        viewModelScope.launch {
            val timeLeft = timerRepository.countDown(duration)
//            if (timeLeft > 0) {
//                onContinueCountingDown(timeLeft)
//            } else {
//                onFinishCountingDown()
//            }
        }
    }
}
