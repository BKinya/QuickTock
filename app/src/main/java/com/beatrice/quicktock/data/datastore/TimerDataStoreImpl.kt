package com.beatrice.quicktock.data.datastore

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map

val TIMER_KEY = intPreferencesKey("timer_key")

class TimerDataStore(
    private val userPreferences: DataStore<Preferences>
) {
    suspend fun getTimer(): Flow<Int> {
        return try {
            userPreferences.data.map { pref ->
                pref[TIMER_KEY] ?: 0
            }
        } catch (e: Exception) {
            Log.d("EXCEPTION", "Reading timer => ${e.message}")
            flowOf(0)
        }
    }

    suspend fun setTimer(duration: Int): Flow<Boolean> {
        return try {
            userPreferences.edit { pref ->
                pref[TIMER_KEY] = duration
            }
            flowOf(true)
        } catch (e: Exception) {
            Log.d("EXCEPTION", "Setting timer => ${e.message}")
            flowOf(false)
        }
    }
}
