package vass.rickymorty.presentation.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberImagePainter
import vass.rickymorty.R
import vass.rickymorty.domain.model.SerieCharacter
import vass.rickymorty.presentation.viewmodel.CharacterDetailViewModel
import vass.rickymorty.presentation.viewmodel.states.CharacterDetailScreenState

@Composable
fun CharacterDetailScreen(characterId: String?) {
    val viewModel: CharacterDetailViewModel = hiltViewModel()
    val characterDetailState by viewModel.characterDetailState.collectAsState()

    LaunchedEffect(key1 = characterId) {
        viewModel.getCharacter(characterId)
    }

    when (characterDetailState) {
        is CharacterDetailScreenState.Loading -> {
            LoadingScreen()
        }

        is CharacterDetailScreenState.Success -> {
            val character = (characterDetailState as CharacterDetailScreenState.Success).character
            ShowCharacterDetail(character)
        }

        is CharacterDetailScreenState.Error -> {
            val error = (characterDetailState as CharacterDetailScreenState.Error).errorMessage
            ShowErrorScreen(error) {
                viewModel.getCharacter(characterId)
            }
        }
    }
}

@Composable
fun LoadingScreen() {
    LoadingIndicator()
}

@Composable
fun ShowCharacterDetail(character: SerieCharacter?) {
    if (character != null) {
        CharacterDetailContent(character)
    }
}

@Composable
fun ShowErrorScreen(errorMessage: String?, onRetry: () -> Unit) {
    val defaultErrorMessage = stringResource(id = R.string.default_error_message)
    ErrorScreen(
        message = errorMessage ?: defaultErrorMessage,
        onClickRetry = { onRetry() },
    )
}

@Composable
fun LoadingIndicator() {
    CircularProgressIndicator(
        modifier = Modifier.size(80.dp).padding(16.dp).wrapContentSize(Alignment.Center),
    )
}

@Composable
fun CharacterDetailContent(character: SerieCharacter) {
    Card(
        modifier = Modifier.padding(16.dp).padding(8.dp).clip(RoundedCornerShape(16.dp))
            .padding(16.dp).height(400.dp),
        elevation = 4.dp,
    ) {
        Box(
            modifier = Modifier.fillMaxSize().background(getBackgroundColor(character.status))
                .padding(16.dp),
            contentAlignment = Alignment.Center,
        ) {
            Column(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                Image(
                    painter = rememberImagePainter(data = character.image),
                    contentDescription = character.name,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.size(200.dp).clip(CircleShape),
                )
                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = character.name,
                    style = MaterialTheme.typography.h6,
                    color = Color.Black,
                    modifier = Modifier.fillMaxWidth(),
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = character.species,
                    style = MaterialTheme.typography.body1,
                    color = Color.Gray,
                    modifier = Modifier.fillMaxWidth(),
                )

                Row(
                    modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    val statusColor = when (character.status.lowercase()) {
                        CharacterStatus.ALIVE.name.lowercase() -> Color.Green
                        CharacterStatus.DEAD.name.lowercase() -> Color.Red
                        CharacterStatus.UNKNOWN.name.lowercase() -> Color.Gray
                        else -> Color.Gray
                    }

                    Box(
                        modifier = Modifier.size(12.dp)
                            .background(shape = CircleShape, color = statusColor),
                    )

                    Text(
                        text = character.status,
                        color = Color.DarkGray,
                        modifier = Modifier.padding(start = 4.dp),
                    )
                }
            }
        }
    }
}

@Composable
private fun getBackgroundColor(status: String): Color {
    return when (status.lowercase()) {
        CharacterStatus.ALIVE.name.lowercase() -> colorResource(id = R.color.green_clear)
        CharacterStatus.DEAD.name.lowercase() -> colorResource(id = R.color.dead_status)
        CharacterStatus.UNKNOWN.name.lowercase() -> colorResource(id = R.color.unknown_status)
        else -> colorResource(id = R.color.unknown_status)
    }
}
