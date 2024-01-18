package com.beatrice.quicktock.di.test

import com.beatrice.quicktock.data.datastore.TimerDataStore
import com.beatrice.quicktock.data.fake.FakeDataStore
import org.koin.dsl.module

val testDataModule = module {
    single<TimerDataStore> { FakeDataStore() }
}
