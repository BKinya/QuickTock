package com.beatrice.quicktock.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.beatrice.quicktock.R

@Composable

fun DurationInputComponent(
    modifier: Modifier = Modifier,
    duration: String,
    onDurationChanged: (String) -> Unit = {}
){
    Row(modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center) {
        OutlinedTextField(
            value = duration,
            onValueChange = onDurationChanged,
            modifier = Modifier.size(
                width = 60.dp,
                height = 50.dp
            ).testTag(stringResource(R.string.durationTag)),
            textStyle = TextStyle(
                textAlign = TextAlign.Center
            ),

        )
        Spacer(modifier = Modifier.width(12.dp))

        Text(stringResource(R.string.secondsLabel),
            style = TextStyle(
                fontFamily = FontFamily.Serif,
                fontSize = 25.sp,
            )
        )
    }
}