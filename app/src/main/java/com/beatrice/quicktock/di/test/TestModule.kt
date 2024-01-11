package com.beatrice.quicktock.di.test

import com.beatrice.quicktock.data.repository.TimerRepository
import com.beatrice.quicktock.data.repository.fake.FakeTimerRepository
import com.beatrice.quicktock.ui.stateMachine.UiState
import org.koin.dsl.module

val testStateMachineModule =
    module {
        single { createTestStateMachine(UiState.CountingDown(10)) }
    }
val testRepositoryModule =
    module {
        single<TimerRepository> {
            FakeTimerRepository()
        }
    }

val testModule = listOf(testRepositoryModule, testStateMachineModule)
