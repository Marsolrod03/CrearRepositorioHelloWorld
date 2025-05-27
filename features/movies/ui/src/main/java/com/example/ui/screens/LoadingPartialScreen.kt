package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition

@Composable
fun LoadingPartialScreenLottie() {

    val composition by rememberLottieComposition(
        spec = LottieCompositionSpec.Asset("loadingPartial.json")
    )
    val progress by animateLottieCompositionAsState(
        composition = composition,
        iterations = LottieConstants.IterateForever
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Black),
        horizontalArrangement = Arrangement.End
    ) {
        LottieAnimation(
            composition = composition,
            progress = { progress },
            alignment = Alignment.BottomCenter,
            modifier = Modifier
                .height(150.dp)
                .padding(end = 16.dp)
        )
    }
}

@Composable
@Preview
fun LoadingPartialScreenLottiePreview() {
    LoadingPartialScreenLottie()
}