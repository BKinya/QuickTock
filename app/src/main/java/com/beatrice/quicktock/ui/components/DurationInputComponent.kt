package com.beatrice.quicktock.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.beatrice.quicktock.R

@Composable
fun DurationInputComponent(
    modifier: Modifier = Modifier,
    duration: String,
    onDurationChanged: (String) -> Unit
){
    Row(modifier = modifier.fillMaxWidth()) {
        TextField(
            value = duration,
            onValueChange = onDurationChanged,
        )
        Spacer(modifier = Modifier.width(12.dp))

        Text(stringResource(R.string.secondsLabel),
            style = TextStyle(
                fontFamily = FontFamily.Serif,
                fontSize = 17.sp,
            )
        )
    }
}