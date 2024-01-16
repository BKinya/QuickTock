package com.beatrice.quicktock.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.beatrice.quicktock.R

@Composable
fun SetTimerComponent(
    modifier: Modifier = Modifier,
    onSaveTimerBtnClicked: (Int) -> Unit
) {
    var duration by rememberSaveable {
        mutableStateOf("")
    }
    Column(
        modifier = modifier.fillMaxWidth().padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp)

    ) {
        TitleComponent(
            title = stringResource(R.string.setTimerTitle)
        )
        DurationInputComponent(
            duration = duration,
            onDurationChanged = { newDuration ->
                duration = newDuration
            }
        )
        Button(
            onClick = {
                onSaveTimerBtnClicked(duration.toInt())
            },
            shape = RoundedCornerShape(12.dp)
        ) {
            Text(stringResource(R.string.saveLabel))
        }
    }
}
