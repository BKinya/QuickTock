package com.beatrice.quicktock.data.repository

import kotlinx.coroutines.flow.Flow

interface TimerRepository {
    fun doCountDown(duration: Int): Flow<Int>

    suspend fun setTimer(duration: Int): Flow<Boolean>

    suspend fun getTimer(): Flow<Int>
}
