package com.beatrice.quicktock.data.datastore

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map

val TIMER_KEY = intPreferencesKey("timer_key")

class TimerDataStoreImpl(
    private val userPreferences: DataStore<Preferences>
): TimerDataStore {
    override   fun getTimer(): Flow<Int> {
      return   try {
           userPreferences.data.map { pref ->
                pref[TIMER_KEY] ?: 0
            }

        } catch (e: Exception) {
            Log.d("EXCEPTION", "Reading timer => ${e.message}")
            flowOf(0)
        }
    }

    override fun setTimer(duration: Int): Flow<Boolean>  = flow{
        try {
            userPreferences.edit { pref ->
                pref[TIMER_KEY] = duration
            }
            emit(true)
        } catch (e: Exception) {
            Log.d("EXCEPTION", "Setting timer => ${e.message}")
           emit(false)
        }
    }
}
