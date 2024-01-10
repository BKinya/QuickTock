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


    /**
     * transition from [Start counting] state to [Counting down ]
     * and finally [Finished] state
     */
    @Test
    fun test1() = runTest{
        // Arrange
        // send the event to my state machine
        // Act

        // Assert
        viewModel.uiState.test {
            assertEquals(UiState.TimerSet(60), awaitItem())
            viewModel.onStartCountDown(5)
            assertEquals(UiState.StartCountingDown(5), awaitItem())

//            advanceTimeBy(2000)
//            assertEquals(UiState.CountingDown(3), awaitItem())
//            advanceTimeBy(3000)
//            assertEquals(UiState.Finished, awaitItem())
//            awaitComplete()

        }

    }
}
