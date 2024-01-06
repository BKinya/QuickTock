package com.beatrice.quicktock.data.repository.fake

import com.beatrice.quicktock.data.repository.TimerRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FakeTimerRepository: TimerRepository {
  override suspend fun countDown(duration: Int): Int {
    return 47
  }

  override suspend fun setTimer(duration: Int): Flow<Boolean> {
    return flowOf(false)
  }

  override suspend fun getTimer(): Flow<Int> = flowOf(10)
}