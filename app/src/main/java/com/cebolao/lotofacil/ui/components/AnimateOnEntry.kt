package com.cebolao.lotofacil.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier

@Composable
fun AnimateOnEntry(
    modifier: Modifier = Modifier,
    delayMillis: Int = 0,
    durationMillis: Int = 400,
    initialOffsetY: Int = 30,
    content: @Composable AnimatedVisibilityScope.() -> Unit
) {
    var isVisible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        isVisible = true
    }

    AnimatedVisibility(
        modifier = modifier,
        visible = isVisible,
        enter = slideInVertically(
            animationSpec = tween(durationMillis, delayMillis),
            initialOffsetY = { initialOffsetY }
        ) + fadeIn(
            animationSpec = tween(durationMillis, delayMillis)
        ),
        exit = slideOutVertically(
            animationSpec = tween(durationMillis = 200),
            targetOffsetY = { 20 }
        ) + fadeOut(
            animationSpec = tween(durationMillis = 200)
        ),
        content = content
    )
}