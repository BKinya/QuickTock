package com.beatrice.quicktock.data.datastore

import kotlinx.coroutines.flow.Flow


interface TimerDataStore {
    fun getTimer(): Flow<Int>

    fun setTimer(duration: Int): Flow<Boolean>
}