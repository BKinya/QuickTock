package com.beatrice.quicktock.data.repository.fake

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

    override suspend fun setTimer(duration: Int): Flow<Boolean> {
        return flowOf(false)
    }

    override suspend fun getTimer(): Flow<Int> = if(isTimerSet) flowOf(10) else flowOf(0)
}
