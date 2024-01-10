package com.beatrice.quicktock.data.repository.fake

import com.beatrice.quicktock.data.repository.TimerRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf

class FakeTimerRepository : TimerRepository {
    override fun doCountDown(duration: Int): Flow<Int> = flow{
        emit(2)
    }


    override suspend fun setTimer(duration: Int): Flow<Boolean> {
        return flowOf(false)
    }

    override suspend fun getTimer(): Flow<Int> = flowOf(10)
}
