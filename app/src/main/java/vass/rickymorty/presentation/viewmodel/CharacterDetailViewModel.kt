package vass.rickymorty.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import vass.rickymorty.domain.model.SerieCharacter
import vass.rickymorty.domain.usecase.GetCharacterDetailUseCase
import javax.inject.Inject

@HiltViewModel
class CharacterDetailViewModel @Inject constructor(
    private val getCharacterDetail: GetCharacterDetailUseCase,
) :
    ViewModel() {

    private val _characterDetail = MutableStateFlow<SerieCharacter?>(null)
    val characterDetail = _characterDetail.asStateFlow()

    private fun onEvent(event: DetailEvent) {
        viewModelScope.launch {
            when (event) {
                is DetailEvent.LoadCharacterDetail -> {
                    loadCharacters(event.characterId)
                }
            }
        }
    }

    fun getCharacter(characterId: String?) {
        onEvent(DetailEvent.LoadCharacterDetail(characterId))
    }

    private suspend fun loadCharacters(searchTerm: String? = "") {
        getCharacterDetail.invoke(searchTerm).let {
            _characterDetail.value = it
        }
    }

    sealed class DetailEvent {
        class LoadCharacterDetail(val characterId: String? = "") : DetailEvent()
    }

    // falta manejo de errores aqui si
}
