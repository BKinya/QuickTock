package com.beatrice.quicktock.viewmodel

import app.cash.turbine.test
import com.beatrice.quicktock.data.fake.FakeTimerRepository
import com.beatrice.quicktock.di.createStateMachine
import com.beatrice.quicktock.ui.stateMachine.SideEffect
import com.beatrice.quicktock.ui.stateMachine.TimerViewModel
import com.beatrice.quicktock.ui.stateMachine.UiEvent
import com.beatrice.quicktock.ui.stateMachine.UiState
import com.beatrice.quicktock.util.MainDispatcherExtension
import com.beatrice.quicktock.views.TEST_DURATION
import com.tinder.StateMachine
import kotlin.test.assertEquals
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MainDispatcherExtension::class)
class CheckIfTimerIsSetViewModelTest {
    val stateMachine: StateMachine<UiState, UiEvent, SideEffect> =
        createStateMachine().with {
            initialState(UiState.Idle)
        }

    val timerRepository = FakeTimerRepository()
    val timerViewModel =
        TimerViewModel(
            timerRepository = timerRepository,
            stateMachine = stateMachine,
            dispatcher = UnconfinedTestDispatcher()
        )

    @Test
    fun `test update value of uiState to SettingTimer when timer is not set`()= runTest{
        timerViewModel.uiState.test {
            assertEquals(UiState.Idle, awaitItem())
            timerViewModel.onLaunchTheApp()
            assertEquals(UiState.SettingTimer, awaitItem())

        }
    }

    @Test
    fun `test update value of uiState to TimerSet when timer has been set` () = runTest {
        timerViewModel.uiState.test {
            timerRepository.isTimerSet = true
            assertEquals(UiState.Idle, awaitItem())
            timerViewModel.onLaunchTheApp()
            assertEquals(UiState.TimerSet(TEST_DURATION), awaitItem())

        }
    }
}