package com.beatrice.quicktock.viewmodel

import app.cash.turbine.test
import com.beatrice.quicktock.data.repository.fake.FakeTimerRepository
import com.beatrice.quicktock.di.createStateMachine
import com.beatrice.quicktock.ui.stateMachine.SideEffect
import com.beatrice.quicktock.ui.stateMachine.TimerViewModel
import com.beatrice.quicktock.ui.stateMachine.UiEvent
import com.beatrice.quicktock.ui.stateMachine.UiState
import com.beatrice.quicktock.util.MainDispatcherExtension
import com.beatrice.quicktock.views.TEST_DURATION
import com.tinder.StateMachine
import kotlin.test.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@OptIn(ExperimentalCoroutinesApi::class)
@ExtendWith(MainDispatcherExtension::class)
class PauseCountDownViewModelTest {
    private val stateMachine: StateMachine<UiState, UiEvent, SideEffect> = createStateMachine().with {
        initialState(UiState.CountingDown(TEST_DURATION))
    }
    private val timerRepository = FakeTimerRepository()

    private val viewModel =
        TimerViewModel(
            timerRepository = timerRepository,
            stateMachine = stateMachine,
            dispatcher = UnconfinedTestDispatcher(),
        )

    @Test
    fun test1() = runTest{
        viewModel.uiState.test {
            assertEquals(UiState.TimerSet(10), awaitItem())// verify initial value
            viewModel.onPauseCountingDown(TEST_DURATION)
            assertEquals(UiState.Paused(TEST_DURATION), awaitItem())
        }
    }

    // TODO: Test stopped state
}