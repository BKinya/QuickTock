package com.beatrice.quicktock.ui.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Composable
fun TitleComponent(
    modifier: Modifier = Modifier,
    title: String
) {
    Text(
        text = title,
        modifier = modifier,
        style = TextStyle(
            fontFamily = FontFamily.SansSerif,
            fontSize = 27.sp,
            fontWeight = FontWeight.SemiBold
        )
    )
}