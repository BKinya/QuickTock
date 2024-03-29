package com.beatrice.quicktock.viewmodel

import app.cash.turbine.test
import com.beatrice.quicktock.data.fake.TEST_DURATION
import com.beatrice.quicktock.data.fake.TIME_LEFT_ONE
import com.beatrice.quicktock.ui.stateMachine.UiState
import com.beatrice.quicktock.util.MainDispatcherExtension
import com.beatrice.quicktock.util.createViewModel
import kotlin.test.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MainDispatcherExtension::class)
class ResumeCountDownViewModelTest {
    private val viewModel = createViewModel(UiState.Paused(TEST_DURATION))

    @Test
    fun `update value of uiState to Resumed state when the StateMachine transitions to Resume state`() = runTest {
        viewModel.uiState.test {
            assertEquals(UiState.Paused(TEST_DURATION), awaitItem()) // verify initial value
            viewModel.onResumeCountingDown(TEST_DURATION)
            assertEquals(UiState.CountingDown(TEST_DURATION), awaitItem())
            assertEquals(UiState.CountingDown(TIME_LEFT_ONE), awaitItem())
            assertEquals(UiState.Finished, awaitItem())
        }
    }
}
