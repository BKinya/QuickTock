package com.beatrice.quicktock.di.test

import com.beatrice.quicktock.data.datastore.TimerDataStore
import com.beatrice.quicktock.data.fake.FakeDataStore
import com.beatrice.quicktock.data.repository.TimerRepository
import com.beatrice.quicktock.data.fake.FakeTimerRepository
import org.koin.dsl.module

val testRepositoryModule = module {
    single<TimerRepository> { FakeTimerRepository() }
}

val testDataModule = module {
    single<TimerDataStore> { FakeDataStore() }
}

val testModule = listOf(testDataModule)