package com.example.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.ui.R

@Composable
fun LoadingPartial() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.End
    ) {
        val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.animation))

        LottieAnimation(
            composition = composition,
            modifier = Modifier.wrapContentSize(),
            iterations = LottieConstants.IterateForever,
            isPlaying = true,
        )
    }
}

@Preview
@Composable
private fun LoadingFullScreenPreview() {
    LoadingPartial()
}