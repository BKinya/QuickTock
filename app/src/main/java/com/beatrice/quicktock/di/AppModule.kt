package com.beatrice.quicktock.di

import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.dataStoreFile
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.emptyPreferences
import androidx.lifecycle.viewModelScope
import com.beatrice.quicktock.data.datastore.TimerDataStore
import com.beatrice.quicktock.data.repository.TimerRepository
import com.beatrice.quicktock.data.repository.TimerRepositoryImpl
import com.beatrice.quicktock.ui.stateMachine.SideEffect
import com.beatrice.quicktock.ui.stateMachine.TimerViewModel
import com.beatrice.quicktock.ui.stateMachine.UiEvent
import com.beatrice.quicktock.ui.stateMachine.UiState
import com.tinder.StateMachine
import kotlin.math.sin
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val dataModule =
    module {
        single {
            PreferenceDataStoreFactory.create(
                corruptionHandler =
                    ReplaceFileCorruptionHandler(
                        produceNewData = { emptyPreferences() },
                    ),
                scope = CoroutineScope(Dispatchers.IO + SupervisorJob()),
                produceFile = { androidContext().dataStoreFile("user_preferences.pn") },
            )
        }
        single {
            TimerDataStore(
                userPreferences = get(),
            )
        }
    }
val repositoryModule =
    module {
        single<TimerRepository> {
            TimerRepositoryImpl(
                dataStore = get(),
            )
        }
    }

val utilModule =
    module {
        single<CoroutineDispatcher> { Dispatchers.Default}
    }
val stateMachineModule = module {
    single {
        createStateMachine()
    }
}
val vieModelModule =
    module {
        viewModel {
            TimerViewModel(
                timerRepository = get(),
                stateMachine = get (),
                dispatcher = get()
            )
        }
    }

val appModule = listOf(dataModule, repositoryModule, utilModule, stateMachineModule, vieModelModule)
