package com.beatrice.quicktock.data.fake

import com.beatrice.quicktock.data.repository.TimerRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf

class FakeTimerRepository : TimerRepository {
    var isTimerSet = false
    override fun doCountDown(duration: Int): Flow<Int> = flow {
        for (i in duration downTo 0 step 3) {
            emit(i)
        }
    }

    override suspend fun saveTimer(duration: Int): Flow<Int> = flowOf(10)

    override suspend fun getTimer(): Flow<Int> = if (isTimerSet) flowOf(10) else flowOf(0)
}
