package com.example.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition

@Composable
fun LoadingFullScreenLottie() {

    val composition by rememberLottieComposition(
        spec = LottieCompositionSpec.Asset("loadingAnimation.json")
    )
    val progress by animateLottieCompositionAsState(
        composition = composition,
        iterations = LottieConstants.IterateForever
        )

    LottieAnimation(
        composition = composition,
        progress = {progress},
    )
}

@Composable
@Preview(showBackground = false)
fun LoadingFullScreenLottiePreview(){
    LoadingFullScreenLottie()
}