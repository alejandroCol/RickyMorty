package vass.rickymorty.data.repository

import androidx.paging.PagingData
import vass.rickymorty.domain.model.SerieCharacter

sealed class CharacterResult {
    data class Success(val data: PagingData<SerieCharacter>) : CharacterResult()
    data class Error(val message: String) : CharacterResult()
}
