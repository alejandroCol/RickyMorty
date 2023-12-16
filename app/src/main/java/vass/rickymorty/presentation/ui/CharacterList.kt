package vass.rickymorty.presentation.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import coil.compose.rememberImagePainter
import vass.rickymorty.R
import vass.rickymorty.domain.model.SerieCharacter
import vass.rickymorty.presentation.util.ErrorMessage
import vass.rickymorty.presentation.util.LoadingNextPageItem
import vass.rickymorty.presentation.util.PageLoader
import vass.rickymorty.presentation.viewmodel.CharacterViewModel

@Composable
fun CharacterList(
    charactersList: LazyPagingItems<SerieCharacter>,
    onCharacterSelected: (SerieCharacter) -> Unit,
) {
    LazyColumn {
        item {
            // Agrega la fila de opciones seleccionables en la parte superior
            OptionsRow()
        }
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
                loadState.refresh is LoadState.Loading -> {
                    item { PageLoader(modifier = Modifier.fillParentMaxSize()) }
                }

                loadState.refresh is LoadState.Error -> {
                    val error = charactersList.loadState.refresh as LoadState.Error
                    item {
                        ErrorMessage(
                            modifier = Modifier.fillParentMaxSize(),
                            message = stringResource(id = R.string.network_error_message),
                            onClickRetry = { retry() },
                        )
                    }
                }

                loadState.append is LoadState.Loading -> {
                    item { LoadingNextPageItem(modifier = Modifier) }
                }

                loadState.append is LoadState.Error -> {
                    val error = charactersList.loadState.append as LoadState.Error
                    item {
                        ErrorMessage(
                            modifier = Modifier,
                            message = stringResource(id = R.string.network_error_message),
                            onClickRetry = { retry() },
                        )
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
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
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
            Text(
                text = character.name,
                style = MaterialTheme.typography.h6,
                color = Color.Black,
                modifier = Modifier.fillMaxWidth(),
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = character.description,
                style = MaterialTheme.typography.body1,
                color = Color.Gray,
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
}

@Composable
fun OptionsRow() {
    val viewModel: CharacterViewModel = hiltViewModel()
    // Utiliza remember para mantener el estado de la opción seleccionada
    var selectedOption by remember { mutableStateOf<Option?>(null) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        OptionItem(
            text = "Opción 1",
            isSelected = selectedOption == Option.Option1,
            onClick = {
                selectedOption = Option.Option1
                viewModel.onFilterSelected(Option.Option1)
            },
        )
        OptionItem(
            text = "Opción 2",
            isSelected = selectedOption == Option.Option2,
            onClick = {
                selectedOption = Option.Option2
                viewModel.onFilterSelected(Option.Option2)
            },
        )
        OptionItem(
            text = "Opción 3",
            isSelected = selectedOption == Option.Option3,
            onClick = {
                selectedOption = Option.Option3
                viewModel.onFilterSelected(Option.Option3)
            },
        )
    }
}

@Composable
fun OptionItem(text: String, isSelected: Boolean, onClick: () -> Unit) {
    Surface(
        modifier = Modifier
            .padding(4.dp)
            .height(40.dp)
            .padding(8.dp)
            .background(
                color = if (isSelected) Color.Gray else Color.LightGray,
                shape = RoundedCornerShape(20.dp),
            )
            .clickable { onClick() }, // Ejecuta la acción al hacer clic
    ) {
        Text(
            text = text,
            color = if (isSelected) Color.White else Color.Black,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
        )
    }
}

enum class Option {
    Option1,
    Option2,
    Option3,
}
