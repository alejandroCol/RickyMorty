package vass.rickymorty.presentation.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberImagePainter
import vass.rickymorty.presentation.viewmodel.CharacterDetailViewModel

@Composable
fun CharacterDetailScreen(characterId: String?) {
    val viewModel: CharacterDetailViewModel = hiltViewModel()
    val characterDetail by viewModel.characterDetail.collectAsState()
    viewModel.getCharacter(characterId)

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
    ) {
        LazyColumn(
            modifier = Modifier.padding(16.dp),
        ) {
            item {
                characterDetail?.let {
                    // Imagen principal del personaje
                    Image(
                        painter = rememberImagePainter(data = it.image),
                        contentDescription = it.name,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(250.dp),
                        contentScale = ContentScale.Crop,
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    // Nombre del personaje
                    Text(
                        text = it.name,
                        style = MaterialTheme.typography.headlineLarge,
                        color = MaterialTheme.colorScheme.onBackground,
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    // Nombre del episodio principal
                    Text(
                        text = it.description,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onBackground,
                    )
                } ?: Text(
                    text = "Ups ocurrio un problema",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onBackground,
                )
            }
        }
    }
}
