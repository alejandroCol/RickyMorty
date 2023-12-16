package vass.rickymorty.presentation.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.paging.compose.collectAsLazyPagingItems
import dagger.hilt.android.AndroidEntryPoint
import vass.rickymorty.presentation.theme.RickyMortyTheme
import vass.rickymorty.presentation.viewmodel.CharacterViewModel
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContent {
            RickyMortyTheme {
                // Crear el NavController
                val navController = rememberNavController()

                NavHost(navController = navController, startDestination = "characterList") {
                    composable("characterList") {
                        CharacterListScreen(navController)
                    }
                    composable(
                        "characterDetail/{characterId}",
                        arguments =
                        listOf(navArgument("characterId") { type = NavType.StringType }),
                    ) { backStackEntry ->
                        // Obtiene el argumento de la ruta
                        val characterId = backStackEntry.arguments?.getString("characterId")
                        // Aquí, necesitarás alguna manera de obtener el objeto SerieCharacter a partir del ID

                        CharacterDetailScreen(characterId)
                    }
                }
            }
        }
    }
}

