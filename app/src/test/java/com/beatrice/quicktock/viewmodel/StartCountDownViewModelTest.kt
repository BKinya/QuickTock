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
import com.beatrice.quicktock.views.TIME_LEFT_ONE
import com.beatrice.quicktock.views.TIME_LEFT_TWO
import com.tinder.StateMachine
import kotlin.test.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@OptIn(ExperimentalCoroutinesApi::class)
@ExtendWith(MainDispatcherExtension::class)
class StartCountDownViewModelTest {
    private val stateMachine: StateMachine<UiState, UiEvent, SideEffect> =
        createStateMachine().with {
            initialState(UiState.TimerSet(TEST_DURATION))
        }
    private val timerRepository = FakeTimerRepository()

    private val viewModel =
        TimerViewModel(
            timerRepository = timerRepository,
            stateMachine = stateMachine,
            dispatcher = UnconfinedTestDispatcher()
        )

    @Test
    fun `update value of uiState to CountingDown when the StateMachine transitions to CountingDown state`() =
        runTest {
            viewModel.uiState.test {
                assertEquals(UiState.TimerSet(TEST_DURATION), awaitItem())
                viewModel.onStartCountDown(TEST_DURATION)
                assertEquals(UiState.CountDownStarted(TEST_DURATION), awaitItem())
                assertEquals(UiState.CountingDown(TIME_LEFT_ONE), awaitItem())
                assertEquals(UiState.CountingDown(TIME_LEFT_TWO), awaitItem())
                assertEquals(UiState.Finished, awaitItem())
            }
        }
}
