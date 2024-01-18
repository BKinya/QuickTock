package com.beatrice.quicktock.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.beatrice.quicktock.R

@Composable
fun CountDownTimerComponent(
    duration: Int,
    modifier: Modifier = Modifier,
    onPlayButtonClicked: (Int) -> Unit = {},
    onPauseButtonClicked: (Int) -> Unit = {},
    onResumeButtonClicked: (Int) -> Unit = {},
    onStopButtonClicked: () -> Unit = {},
    showPlayButton: Boolean = false,
    showPauseButton: Boolean = false,
    showStopButton: Boolean = false,
    showResumeButton: Boolean = false
) {
    Column(
        modifier =
        modifier
            .background(Color.White)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(72.dp))
        TitleComponent(title = stringResource(id = R.string.durationLabel, duration))
        Spacer(modifier = Modifier.height(36.dp))
        Row {
            ActionButton(
                resourceId = R.drawable.ic_play,
                onButtonClicked = {
                    onPlayButtonClicked(duration)
                },
                conteDescription = stringResource(id = R.string.playBtnDesc),
                isVisible = showPlayButton
            )
            ActionButton(
                resourceId = R.drawable.ic_pause,
                onButtonClicked = { onPauseButtonClicked(duration) },
                conteDescription = stringResource(id = R.string.pauseButtonDesc),
                isVisible = showPauseButton
            )

            ActionButton(
                resourceId = R.drawable.ic_resume,
                onButtonClicked = { onResumeButtonClicked(duration) },
                conteDescription = stringResource(id = R.string.resumeButtonDesc),
                isVisible = showResumeButton
            )
            ActionButton(
                resourceId = R.drawable.ic_stop,
                onButtonClicked = onStopButtonClicked,
                conteDescription = stringResource(id = R.string.stopButtonDesc),
                isVisible = showStopButton
            )
        }
    }
}

@Composable
fun ActionButton(
    resourceId: Int,
    onButtonClicked: () -> Unit,
    conteDescription: String,
    modifier: Modifier = Modifier,
    isVisible: Boolean
) {
    AnimatedVisibility(visible = isVisible) {
        TextButton(
            modifier = modifier.padding(16.dp),
            onClick = onButtonClicked
        ) {
            Icon(
                modifier = Modifier.size(64.dp),
                painter = painterResource(id = resourceId),
                contentDescription = conteDescription
            )
        }
    }
}

