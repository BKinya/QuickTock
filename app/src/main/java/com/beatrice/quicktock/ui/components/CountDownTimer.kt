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
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.beatrice.quicktock.R
import com.beatrice.quicktock.ui.stateMachine.UiEvent

@Composable
fun CountDownTimer(
    sendUiEvent: (UiEvent) -> Unit,
    duration: Int,
    modifier: Modifier = Modifier,
    showPlayButton: Boolean = false,
    showPauseButton: Boolean = false,
    showStopButton: Boolean = false,
    showResumeButton: Boolean = false,
) {
    Column(
        modifier =
            modifier
                .background(Color.White)
                .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.height(72.dp))
        Text(
            text = stringResource(id = R.string.durationLabel, duration),
            style =
                TextStyle(
                    fontFamily = FontFamily.Serif,
                    fontSize = 27.sp,
                    fontWeight = FontWeight.SemiBold,
                ),
        )
        Spacer(modifier = Modifier.height(36.dp))
        Row {
            /**
             * When you click play button what should happen
             * Start reducing it and send the new values to the UI
             */
            ActionButton(
                resourceId = R.drawable.ic_play,
                onButtonClicked = { sendUiEvent(UiEvent.OnStart(duration)) },
                conteDescription = stringResource(id = R.string.playBtnDesc),
                isVisible = showPlayButton,
            )
            ActionButton(
                resourceId = R.drawable.ic_stop,
                onButtonClicked = { /*TODO*/ },
                conteDescription = stringResource(id = R.string.stopButtonDesc),
                isVisible = showStopButton,
            )
            ActionButton(
                resourceId = R.drawable.ic_pause,
                onButtonClicked = { /*TODO*/ },
                conteDescription = stringResource(id = R.string.pauseButtonDesc),
                isVisible = showPauseButton,
            )
            ActionButton(
                resourceId = R.drawable.ic_resume,
                onButtonClicked = { /*TODO*/ },
                conteDescription = stringResource(id = R.string.resumeButtonDesc),
                isVisible = showResumeButton,
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
    isVisible: Boolean,
) {
    AnimatedVisibility(visible = isVisible) {
        TextButton(
            modifier = modifier.padding(16.dp),
            onClick = onButtonClicked,
        ) {
            Icon(
                modifier = Modifier.size(64.dp),
                painter = painterResource(id = resourceId),
                contentDescription = conteDescription,
            )
        }
    }
}

@Composable
@Preview
fun ActionButtonPreview() {
    ActionButton(
        resourceId = R.drawable.ic_play,
        onButtonClicked = {},
        conteDescription = "",
        isVisible = true,
        modifier =
            Modifier
                .background(Color.White)
                .fillMaxSize(),
    )
}
