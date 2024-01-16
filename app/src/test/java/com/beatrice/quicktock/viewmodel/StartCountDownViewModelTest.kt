package com.beatrice.quicktock.viewmodel

import app.cash.turbine.test
import com.beatrice.quicktock.ui.stateMachine.UiState
import com.beatrice.quicktock.util.MainDispatcherExtension
import com.beatrice.quicktock.util.createViewModel
import com.beatrice.quicktock.views.TEST_DURATION
import com.beatrice.quicktock.views.TIME_LEFT_ONE
import com.beatrice.quicktock.views.TIME_LEFT_TWO
import kotlin.test.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@OptIn(ExperimentalCoroutinesApi::class)
@ExtendWith(MainDispatcherExtension::class)
class StartCountDownViewModelTest {

    private val viewModel = createViewModel(UiState.TimerSet(TEST_DURATION))

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
