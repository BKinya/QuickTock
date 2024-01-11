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
    private val dispatcher: CoroutineDispatcher,
) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState> = MutableStateFlow(UiState.TimerSet(10))
    val uiState = _uiState.asStateFlow()

    private val transitionSharedFlow = MutableSharedFlow<StateMachine.Transition<UiState, UiEvent, SideEffect>>(10)

    private lateinit var countDownJob: Job


    init {
        observeTransitions()
    }


    fun onStartCountDown(duration: Int) {
        viewModelScope.launch(dispatcher) {
            val transition =
                stateMachine.transition(UiEvent.OnStart(duration))
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

    fun onPauseCountingDown(timeLeft: Int){
        viewModelScope.launch(dispatcher) {

            if (::countDownJob.isInitialized){
                countDownJob.cancelAndJoin()

            }

            val transition = stateMachine.transition(UiEvent.OnPause(timeLeft))
            transitionSharedFlow.emit(transition)


        }
    }

    fun observeTransitions() {
        viewModelScope.launch(dispatcher) {
            transitionSharedFlow.asSharedFlow().collectLatest { transition ->
                if (transition is StateMachine.Transition.Valid) {
                    _uiState.value = transition.toState
                    when (val sideEffect = transition.sideEffect) {
                        is SideEffect.DoCountDown -> {
                            countDown(sideEffect.duration)
                        }

                        else -> {}
                    }
                }

            }
        }
    }

    private fun countDown(duration: Int) {
       countDownJob = viewModelScope.launch(dispatcher) {
            timerRepository.doCountDown(duration)
                .onCompletion {
                    onFinishCountingDown()
                }
                .collectLatest { timeLeft ->
                    onContinueCountingDown(timeLeft)
                }
        }
    }

    fun updateUiState(uiState: UiState){
        _uiState.value = uiState
    }

}
