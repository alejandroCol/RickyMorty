package vass.rickymorty.presentation.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.toLowerCase
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.rememberImagePainter
import vass.rickymorty.R
import vass.rickymorty.domain.model.SerieCharacter
import vass.rickymorty.presentation.theme.Green
import vass.rickymorty.presentation.util.ErrorMessage
import vass.rickymorty.presentation.util.LoadingNextPageItem
import vass.rickymorty.presentation.util.PageLoader
import vass.rickymorty.presentation.viewmodel.CharacterViewModel

@Composable
fun CharacterListScreen(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                backgroundColor = Green,
                title = { Text(text = "Rick y Morty", color = Color.White) },
            )
        },
        content = {
            CharacterList(onCharacterSelected = { character ->
                navController.navigate("characterDetail/${character.id}")
            })
        },

//        poner texo horizontal y añadir colores del api

    )
}

@Composable
fun CharacterList(
    onCharacterSelected: (SerieCharacter) -> Unit,
) {
    val viewModel: CharacterViewModel = hiltViewModel()
    val charactersList = viewModel.characters.collectAsLazyPagingItems()
    val loadingState = charactersList.loadState.refresh

    Column {
        SearchBar(onSearch = { query ->
            charactersList.refresh()
            viewModel.searchCharacters(query)
            // Después de la búsqueda, llama a refresh en LazyPagingItems
            // para actualizar la lista
            // Por ejemplo:
        }, onClear = {
            charactersList.refresh()
            viewModel.searchCharacters("")
        })
        // Agrega la fila de opciones seleccionables en la parte superior
        OptionsRow(charactersList)

        when (loadingState) {
            is LoadState.Loading -> {
                PageLoader()
            }

            is LoadState.Error -> {
                val error = charactersList.loadState.refresh as LoadState.Error
                ErrorMessage(
                    message = error.error.message
                        ?: stringResource(id = R.string.default_error_message),
                    onClickRetry = { charactersList.retry() },
                )
            }

            else -> {
                LazyColumn {
                    items(charactersList.itemCount) { index ->
                        charactersList[index]?.let {
                            CharacterItem(
                                character = it,
                                onCharacterSelected = onCharacterSelected,
                            )
                        }
                    }
                    charactersList.apply {
                        when {
                            loadState.append is LoadState.Loading -> {
                                item { LoadingNextPageItem(modifier = Modifier) }
                            }

                            loadState.append is LoadState.Error -> {
                                val error = charactersList.loadState.append as LoadState.Error
                                item {
                                    ErrorMessage(
                                        message = stringResource(id = R.string.network_error_message),
                                        onClickRetry = { retry() }
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CharacterItem(
    character: SerieCharacter,
    onCharacterSelected: (SerieCharacter) -> Unit,
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onCharacterSelected(character) },
        elevation = 4.dp,
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
        ) {
            Image(
                painter = rememberImagePainter(data = character.image),
                contentDescription = character.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(120.dp)
                    .clip(MaterialTheme.shapes.medium),
            )
            Spacer(modifier = Modifier.height(16.dp))
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
            ) {
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
                    Text(
                        text = character.status,
                        color = Color.Gray,
                        modifier = Modifier.padding(start = 4.dp),
                    )
                }
            }
        }
    }
}

@Composable
fun OptionsRow(charactersList: LazyPagingItems<SerieCharacter>) {
    val viewModel: CharacterViewModel = hiltViewModel()
    var selectedOption by remember { mutableStateOf<CharacterStatus?>(null) }

    BoxWithLayout {
        Row(Modifier.weight(30f)) {
            FilterBox(
                text = stringResource(id = R.string.status_alive),
                isSelected = selectedOption == CharacterStatus.alive,
                onClick = {
                    selectedOption = CharacterStatus.alive
                    charactersList.refresh()
                    viewModel.onFilterSelected(CharacterStatus.alive)
                },
            )
            FilterBox(
                text = stringResource(id = R.string.status_dead),
                isSelected = selectedOption == CharacterStatus.dead,
                onClick = {
                    selectedOption = CharacterStatus.dead
                    charactersList.refresh()
                    viewModel.onFilterSelected(CharacterStatus.dead)
                },
            )
            FilterBox(
                text = stringResource(id = R.string.status_unknown),
                isSelected = selectedOption == CharacterStatus.unknown,
                onClick = {
                    selectedOption = CharacterStatus.unknown
                    charactersList.refresh()
                    viewModel.onFilterSelected(CharacterStatus.unknown)
                },
            )
        }
    }
}

@Composable
fun FilterBox(text: String, isSelected: Boolean, onClick: () -> Unit) {
    val backgroundColor =
        if (isSelected) colorResource(id = R.color.green_clear) else Color.Transparent
    val contentColor = if (isSelected) Color.DarkGray else Color.LightGray
    val borderColor = if (isSelected) Color.Transparent else Color.LightGray

    Box(
        modifier = Modifier
            .width(100.dp)
            .height(40.dp)
            .padding(horizontal = 5.dp)
            .border(
                width = 1.dp,
                color = borderColor,
                shape = RoundedCornerShape(10.dp),
            )
            .background(
                color = backgroundColor,
                shape = RoundedCornerShape(10.dp),
            )
            .clickable { onClick() }, // Ejecuta la acción al hacer clic,
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = text,
            color = contentColor,
            fontSize = 10.sp,
            textAlign = TextAlign.Center,
        )
    }
}

@Composable
fun BoxWithLayout(content: @Composable RowScope.() -> Unit) {
    Row {
        content()
    }
}

enum class CharacterStatus {
    alive,
    dead,
    unknown,
}
