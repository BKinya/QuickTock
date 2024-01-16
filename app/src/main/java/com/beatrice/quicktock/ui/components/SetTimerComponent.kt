package com.beatrice.quicktock.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.beatrice.quicktock.R


@Composable
fun SetTimerComponent(
    modifier: Modifier = Modifier,
) {
    var duration by rememberSaveable{
        mutableStateOf("0.0")
    }
    Column(
        modifier = modifier
    ) {
        TitleComponent(
            title = stringResource(R.string.setTimerTitle)
        )
        DurationInputComponent(
            duration = duration,
            onDurationChanged = {newDuration ->
                duration = newDuration

            }
        )
        // Button
        Button(
                onClick = {
                    // later
                }
        ){
            Text(stringResource(R.string.saveLabel))
        }
    }
}

