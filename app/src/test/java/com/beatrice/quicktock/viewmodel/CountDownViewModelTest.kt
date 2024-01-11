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
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
@ExtendWith(MainDispatcherExtension::class)
class CountDownViewModelTest {
    private val stateMachine: StateMachine<UiState, UiEvent, SideEffect> =
        createStateMachine().with {
            initialState(UiState.TimerSet(TEST_DURATION))
        }
    private val timerRepository = FakeTimerRepository()

    private val viewModel =
        TimerViewModel(
            timerRepository = timerRepository,
            stateMachine = stateMachine,
            dispatcher = UnconfinedTestDispatcher(),
        )

    @Test
    fun `update value of uiState to CountingDown when the StateMachine transitions to CountingDown state`() =
        runTest {
            viewModel.uiState.test {
                assertEquals(UiState.TimerSet(10), awaitItem())
                viewModel.onStartCountDown(5)
                assertEquals(UiState.CountDownStarted(5), awaitItem())
                assertEquals(UiState.CountingDown(2), awaitItem())
                assertEquals(UiState.Finished, awaitItem())
            }
        }
}
