package vass.rickymorty.presentation.ui.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import vass.rickymorty.R

@Composable
fun LottieLoadingAnimation() {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.loading_animation))
    LottieAnimation(
        composition,
        iterations = LottieConstants.IterateForever,
    )
}
