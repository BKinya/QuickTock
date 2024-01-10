package com.beatrice.quicktock.viewmodel

import app.cash.turbine.test
import com.beatrice.quicktock.data.repository.fake.FakeTimerRepository
import com.beatrice.quicktock.ui.stateMachine.SideEffect
import com.beatrice.quicktock.ui.stateMachine.TimerViewModel
import com.beatrice.quicktock.ui.stateMachine.UiEvent
import com.beatrice.quicktock.ui.stateMachine.UiState
import com.beatrice.quicktock.util.MainDispatcherExtension
import com.beatrice.quicktock.util.createTestStateMachine
import com.tinder.StateMachine
import kotlin.test.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith


@ExtendWith(MainDispatcherExtension::class)
class TimerViewModelTest{


  private  val stateMachine: StateMachine<UiState, UiEvent, SideEffect>  = createTestStateMachine(UiState.TimerSet(5))
  private  val timerRepository = FakeTimerRepository()
   @OptIn(ExperimentalCoroutinesApi::class)
   private val viewModel = TimerViewModel(
        timerRepository = timerRepository,
        stateMachine = stateMachine,
       dispatcher = UnconfinedTestDispatcher()
    )

    @Test
    fun test1() = runTest{
        viewModel.uiState.test {
            assertEquals(UiState.TimerSet(10), awaitItem())
            viewModel.onStartCountDown(5)
            assertEquals(UiState.CountDownStarted(5), awaitItem())
            assertEquals(UiState.CountingDown(2), awaitItem())

        }

    }
}
