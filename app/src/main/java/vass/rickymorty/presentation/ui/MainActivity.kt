package vass.rickymorty.presentation.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import dagger.hilt.android.AndroidEntryPoint
import vass.rickymorty.presentation.theme.RickyMortyTheme
import vass.rickymorty.presentation.ui.screen.AppNavigation

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContent {
            RickyMortyTheme {
                AppNavigation()
            }
        }
    }
}
