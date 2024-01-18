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
class CountDownViewModelTest {

    private val viewModel = createViewModel(UiState.TimerSet(TEST_DURATION))

    @Test
    fun `when counting down update uiState with appropriate value`() =
        runTest {
            viewModel.uiState.test {
                assertEquals(UiState.TimerSet(TEST_DURATION), awaitItem())
                viewModel.onStartCountDown(TEST_DURATION)
                assertEquals(UiState.CountDownStarted(TEST_DURATION), awaitItem())
                assertEquals(UiState.CountingDown(TIME_LEFT_ONE), awaitItem())
                assertEquals(UiState.Finished, awaitItem())
            }
        }
}
