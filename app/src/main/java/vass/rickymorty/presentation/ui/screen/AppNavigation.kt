package vass.rickymorty.presentation.ui.screen

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

@Composable
fun AppNavigation() {
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
            val characterId = backStackEntry.arguments?.getString("characterId")
            CharacterDetailScreen(characterId)
        }
    }
}
