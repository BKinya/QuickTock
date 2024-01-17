package com.beatrice.quicktock.viewmodel

import app.cash.turbine.test
import com.beatrice.quicktock.data.fake.FakeTimerRepository
import com.beatrice.quicktock.data.fake.TEST_DURATION
import com.beatrice.quicktock.di.createStateMachine
import com.beatrice.quicktock.ui.stateMachine.SideEffect
import com.beatrice.quicktock.ui.stateMachine.TimerViewModel
import com.beatrice.quicktock.ui.stateMachine.UiEvent
import com.beatrice.quicktock.ui.stateMachine.UiState
import com.beatrice.quicktock.util.MainDispatcherExtension
import com.tinder.StateMachine
import kotlin.test.assertEquals
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MainDispatcherExtension::class)
class CheckTimerViewModelTest {
   private val stateMachine: StateMachine<UiState, UiEvent, SideEffect> = createStateMachine()

   private val timerRepository = FakeTimerRepository()
   private val timerViewModel =
        TimerViewModel(
            timerRepository = timerRepository,
            stateMachine = stateMachine,
            dispatcher = UnconfinedTestDispatcher()
        )

    @Test
    fun `checkTimer - uiState should be SettingTimer when timer duration has not been set`() = runTest {
        timerViewModel.uiState.test {
            /**
             * verify initial value
             */
            assertEquals(UiState.Idle, awaitItem())

            /**
             * Check if the timer has been set
             */
            timerViewModel.onStart()
            assertEquals(UiState.SettingTimer, awaitItem())
            /**
             * Save timer
             */
            timerViewModel.onSaveTimer(TEST_DURATION)
            assertEquals(UiState.TimerSet(TEST_DURATION), awaitItem())

        }
    }

    @Test
    fun `checkTimer - uiState should be TimerSet when timer duration has been set`() = runTest {
        timerViewModel.uiState.test {
            timerRepository.isTimerSet = true
            assertEquals(UiState.Idle, awaitItem())
            timerViewModel.onStart()
            assertEquals(UiState.TimerSet(TEST_DURATION), awaitItem())
        }
    }

}
