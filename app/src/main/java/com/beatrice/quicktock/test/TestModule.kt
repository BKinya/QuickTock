package com.beatrice.quicktock.test

import com.beatrice.quicktock.data.repository.TimerRepository
import com.beatrice.quicktock.data.repository.fake.FakeTimerRepository
import org.koin.dsl.module

val testRepositoryModule =
    module {
        single<TimerRepository> {
            FakeTimerRepository()
        }
    }

val testModule = listOf(testRepositoryModule)
