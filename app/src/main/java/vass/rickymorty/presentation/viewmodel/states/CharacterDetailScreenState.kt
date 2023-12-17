package vass.rickymorty.presentation.viewmodel.states

import vass.rickymorty.domain.model.SerieCharacter

sealed class CharacterDetailScreenState {
    object Loading : CharacterDetailScreenState()
    data class Success(val character: SerieCharacter?) : CharacterDetailScreenState()
    data class Error(val errorMessage: String?) : CharacterDetailScreenState()
}
