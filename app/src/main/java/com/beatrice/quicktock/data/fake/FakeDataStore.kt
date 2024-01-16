package com.beatrice.quicktock.data.fake

import com.beatrice.quicktock.data.datastore.TimerDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FakeDataStore: TimerDataStore {
    override fun getTimer(): Flow<Int> = flowOf(0)

    override fun setTimer(duration: Int): Flow<Boolean> = flowOf(true)
}