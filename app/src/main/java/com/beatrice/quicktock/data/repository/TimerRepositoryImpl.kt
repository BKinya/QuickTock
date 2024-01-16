package com.beatrice.quicktock.data.repository

import com.beatrice.quicktock.data.datastore.TimerDataStore
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class TimerRepositoryImpl(
    private val dataStore: TimerDataStore
) : TimerRepository {
    override fun doCountDown(duration: Int): Flow<Int> = flow {
        for (i in duration downTo 0 step 1) {
            delay(1000)
            emit(i)
        }
    }

    override suspend fun saveTimer(duration: Int): Flow<Int> = dataStore.saveTimer(duration)

    override suspend fun getTimer(): Flow<Int> = dataStore.getTimer()
}
