package com.beatrice.quicktock.data.repository

import kotlinx.coroutines.flow.Flow

interface TimerRepository {
    fun doCountDown(duration: Int): Flow<Int>

    suspend fun saveTimer(duration: Int): Flow<Int>

    suspend fun getTimer(): Flow<Int>
}
