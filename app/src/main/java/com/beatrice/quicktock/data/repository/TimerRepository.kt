package com.beatrice.quicktock.data.repository

import kotlinx.coroutines.flow.Flow

interface TimerRepository {
    suspend fun countDown(duration: Int): Int

    suspend fun setTimer(duration: Int): Flow<Boolean>

    suspend fun getTimer(): Flow<Int>
}