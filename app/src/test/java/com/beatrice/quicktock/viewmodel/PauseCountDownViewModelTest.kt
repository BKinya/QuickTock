package com.beatrice.quicktock.viewmodel

import app.cash.turbine.test
import com.beatrice.quicktock.ui.stateMachine.UiState
import com.beatrice.quicktock.util.MainDispatcherExtension
import com.beatrice.quicktock.util.createViewModel
import com.beatrice.quicktock.views.TEST_DURATION
import kotlin.test.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MainDispatcherExtension::class)
class PauseCountDownViewModelTest {

    private val viewModel = createViewModel(initialState = UiState.CountingDown(TEST_DURATION))

    @Test
    fun `update value of uiState to Paused when the StateMachine transitions to Paused state`() = runTest {
        viewModel.uiState.test {
            assertEquals(UiState.CountingDown(TEST_DURATION), awaitItem()) // verify initial value
            viewModel.onPauseCountingDown(TEST_DURATION)
            assertEquals(UiState.Paused(TEST_DURATION), awaitItem())
        }
    }

    // TODO: Test stopped state
}
