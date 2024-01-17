package com.beatrice.quicktock.data.fake

import com.beatrice.quicktock.data.repository.TimerRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf

const val TEST_DURATION = 5
const val TIME_LEFT_ONE = 3
const val TIME_LEFT_TWO = 1
class FakeTimerRepository : TimerRepository {
    var isTimerSet = false
    override fun doCountDown(duration: Int): Flow<Int> = flow {
        for (i in duration downTo 0 step 1) {
            emit(i)
        }
    }

    override suspend fun saveTimer(duration: Int): Flow<Int> = if (duration > 0) flowOf(TEST_DURATION) else flowOf(0)

    override suspend fun getTimer(): Flow<Int> = if (isTimerSet) flowOf(TEST_DURATION) else flowOf(0)
}
