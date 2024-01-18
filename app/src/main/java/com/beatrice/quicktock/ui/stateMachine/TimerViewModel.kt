package com.beatrice.quicktock.ui.stateMachine

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.beatrice.quicktock.data.repository.TimerRepository
import com.tinder.StateMachine
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.launch

class TimerViewModel(
    private val timerRepository: TimerRepository,
    private val stateMachine: StateMachine<UiState, UiEvent, SideEffect>,
    private val dispatcher: CoroutineDispatcher
) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState> = MutableStateFlow(stateMachine.state)
    val uiState = _uiState.asStateFlow()

    private val transitionSharedFlow =
        MutableSharedFlow<StateMachine.Transition<UiState, UiEvent, SideEffect>>(10)

    private lateinit var countDownJob: Job

    init {
        observeTransitions()
    }

    fun onStart() {
        viewModelScope.launch(dispatcher) {
            val transition = stateMachine.transition(UiEvent.OnStart)
            transitionSharedFlow.emit(transition)
        }
    }

    fun onStartCountDown(duration: Int) {
        viewModelScope.launch(dispatcher) {
            val transition =
                stateMachine.transition(UiEvent.OnStartCountDown(duration))
            transitionSharedFlow.emit(transition)
        }
    }

    fun onContinueCountingDown(timeLeft: Int) {
        viewModelScope.launch(dispatcher) {
            val transition = stateMachine.transition(UiEvent.OnContinueCountDown(timeLeft))
            transitionSharedFlow.emit(transition)
        }
    }

    fun onFinishCountingDown() {
        viewModelScope.launch(dispatcher) {
            val transition = stateMachine.transition(UiEvent.OnFinish)
            transitionSharedFlow.emit(transition)
        }
    }

    fun onPauseCountingDown(timeLeft: Int) {
        viewModelScope.launch(dispatcher) {
            val transition = stateMachine.transition(UiEvent.OnPause(timeLeft))
            transitionSharedFlow.emit(transition)

            // Pause the counting down

            /***
             * This is a little fragile because changing the order makes test to fail
             * FIXME: Can it be improved?
             */
            if (::countDownJob.isInitialized) {
                countDownJob.cancelAndJoin()
            }
        }
    }

    fun onResumeCountingDown(timeLeft: Int) {
        viewModelScope.launch(dispatcher) {
            val transition = stateMachine.transition(UiEvent.OnResume(timeLeft))
            transitionSharedFlow.emit(transition)
        }
    }

    fun onStopCountDown() {
        viewModelScope.launch(dispatcher) {
            val transition = stateMachine.transition(UiEvent.OnStop)
            transitionSharedFlow.emit(transition)

            if (::countDownJob.isInitialized) {
                countDownJob.cancelAndJoin()
            }
        }
    }

    private fun observeTransitions() {
        viewModelScope.launch(dispatcher) {
            transitionSharedFlow.asSharedFlow().collectLatest { transition ->
                if (transition is StateMachine.Transition.Valid) {
                    _uiState.value = transition.toState
                    when (val sideEffect = transition.sideEffect) {
                        is SideEffect.DoCountDown -> {
                            countDown(sideEffect.duration)
                        }

                        is SideEffect.CheckTimer -> {
                            checkTimer()
                        }
                        is SideEffect.SaveTimer -> {
                            saveTimer(sideEffect.duration)
                        }

                        else -> {}
                    }
                }
            }
        }
    }

    private fun checkTimer() {
        viewModelScope.launch(dispatcher) {
            timerRepository.getTimer().collectLatest { duration ->
                if (duration > 0) {
                    onTimerSet(duration)
                } else {
                    onSetTimer()
                }
            }
        }
    }

    private fun saveTimer(duration: Int) {
        viewModelScope.launch(dispatcher) {
            timerRepository.saveTimer(duration).collectLatest { duration ->
                if (duration > 0) {
                    onTimerSet(duration)
                } else {
                    onSetTimer() // TODO: Better error reporting
                }
            }
        }
    }

    fun onTimerSet(duration: Int) {
        viewModelScope.launch(dispatcher) {
            val transition = stateMachine.transition(UiEvent.OnTimerSet(duration))
            transitionSharedFlow.emit(transition)
        }
    }

    fun onSetTimer() {
        viewModelScope.launch(dispatcher) {
            val transition = stateMachine.transition(UiEvent.OnSetTimer)
            transitionSharedFlow.emit(transition)
        }
    }

    fun onSaveTimer(duration: Int) {
        viewModelScope.launch(dispatcher) {
            val transition = stateMachine.transition(UiEvent.OnSaveTimer(duration))
            transitionSharedFlow.emit(transition)
        }
    }

    private fun countDown(duration: Int) {
        countDownJob =
            viewModelScope.launch(dispatcher) {
                timerRepository.doCountDown(duration)
                    .onCompletion {
                        onFinishCountingDown()
                    }
                    .collectLatest { timeLeft ->
                        onContinueCountingDown(timeLeft)
                    }
            }
    }
}
