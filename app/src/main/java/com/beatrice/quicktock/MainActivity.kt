package com.beatrice.quicktock

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.beatrice.quicktock.ui.components.TimerScreen
import com.beatrice.quicktock.ui.stateMachine.TimerViewModel
import com.beatrice.quicktock.ui.theme.QuickTockTheme
import org.koin.android.ext.android.inject

class MainActivity : ComponentActivity() {
    private val timerViewModel: TimerViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val state = timerViewModel.uiState.collectAsStateWithLifecycle().value
            QuickTockTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                ) {
                    TimerScreen(
                        uiState = state,
                        sendUiEvent = {
                            timerViewModel.sendEvent(it)
                        },
                    )
                }
            }
        }
    }
}
