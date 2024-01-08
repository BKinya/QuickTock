package com.beatrice.quicktock.data.repository

import com.beatrice.quicktock.data.datastore.TimerDataStore
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow

class TimerRepositoryImpl(
    private val dataStore: TimerDataStore,
) : TimerRepository {
    override suspend fun countDown(duration: Int): Int {
        delay(1000)
        return duration - 1
    }

    override suspend fun setTimer(duration: Int): Flow<Boolean> = dataStore.setTimer(duration)

    override suspend fun getTimer(): Flow<Int> = dataStore.getTimer()
}
