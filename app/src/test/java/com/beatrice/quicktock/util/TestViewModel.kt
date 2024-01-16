package com.beatrice.quicktock.util

import com.beatrice.quicktock.data.fake.FakeTimerRepository
import com.beatrice.quicktock.di.createStateMachine
import com.beatrice.quicktock.ui.stateMachine.SideEffect
import com.beatrice.quicktock.ui.stateMachine.TimerViewModel
import com.beatrice.quicktock.ui.stateMachine.UiEvent
import com.beatrice.quicktock.ui.stateMachine.UiState
import com.tinder.StateMachine
import kotlinx.coroutines.test.UnconfinedTestDispatcher

/**
 *  Generate an instance of [TimerViewModel]
 */
fun createViewModel(initialState: UiState): TimerViewModel {
    /**
     * create a state machine and override the initial state with the desired state
     */
    val stateMachine: StateMachine<UiState, UiEvent, SideEffect> =
        createStateMachine().with {
            initialState(initialState)
        }

    val timerRepository = FakeTimerRepository()
    val viewModel =
        TimerViewModel(
            timerRepository = timerRepository,
            stateMachine = stateMachine,
            dispatcher = UnconfinedTestDispatcher()
        )
    return viewModel
}
