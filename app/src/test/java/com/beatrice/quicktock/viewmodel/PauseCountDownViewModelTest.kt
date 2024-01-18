package com.beatrice.quicktock.viewmodel

import app.cash.turbine.test
import com.beatrice.quicktock.data.fake.TEST_DURATION
import com.beatrice.quicktock.ui.stateMachine.UiState
import com.beatrice.quicktock.util.MainDispatcherExtension
import com.beatrice.quicktock.util.createViewModel
import kotlin.test.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MainDispatcherExtension::class)
class PauseCountDownViewModelTest {

    private val viewModel = createViewModel(initialState = UiState.CountingDown(TEST_DURATION))

    @Test
    fun `when the state machine  receives onPause event update the value of uiState to Paused`() = runTest {
        viewModel.uiState.test {
            assertEquals(UiState.CountingDown(TEST_DURATION), awaitItem()) // verify initial value
            viewModel.onPauseCountingDown(TEST_DURATION)
            assertEquals(UiState.Paused(TEST_DURATION), awaitItem())
        }
    }

    @Test
    fun `when the state machine receives onStop event update the value of UiState to Idle`() = runTest{
        viewModel.uiState.test {
            assertEquals(UiState.CountingDown(TEST_DURATION), awaitItem()) // verify initial value
            viewModel.onStopCountDown()
            assertEquals(UiState.Idle, awaitItem())
         }
    }
}
