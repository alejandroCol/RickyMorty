package vass.rickymorty.presentation.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
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
            .fillMaxSize(),
    ) {
        LazyColumn(
            modifier = Modifier.padding(16.dp),
        ) {
            item {
                characterDetail?.let { character ->
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        Image(
                            painter = rememberImagePainter(data = character.image),
                            contentDescription = character.name,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(120.dp)
                                .clip(androidx.compose.material.MaterialTheme.shapes.medium),
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(16.dp),
                        ) {
                            androidx.compose.material.Text(
                                text = character.name,
                                style = androidx.compose.material.MaterialTheme.typography.h6,
                                color = Color.Black,
                                modifier = Modifier.fillMaxWidth(),
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            androidx.compose.material.Text(
                                text = character.species,
                                style = androidx.compose.material.MaterialTheme.typography.body1,
                                color = Color.Gray,
                                modifier = Modifier.fillMaxWidth(),
                            )

                            // Row para mostrar el estado con un icono redondo
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 8.dp),
                                verticalAlignment = Alignment.CenterVertically,
                            ) {
                                val statusColor = when (character.status.toLowerCase()) {
                                    CharacterStatus.alive.name -> Color.Green
                                    CharacterStatus.dead.name -> Color.Red
                                    CharacterStatus.unknown.name -> Color.Gray
                                    else -> Color.Gray
                                }

                                // Icono redondo con el color del estado
                                Box(
                                    modifier = Modifier
                                        .size(12.dp)
                                        .background(shape = CircleShape, color = statusColor),
                                )

                                // Texto del estado
                                androidx.compose.material.Text(
                                    text = character.status,
                                    color = Color.Gray,
                                    modifier = Modifier.padding(start = 4.dp),
                                )
                            }
                        }
                    }
                } ?: Text(
                    text = "Ups ocurrio un problema",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onBackground,
                )
            }
        }
    }
}
