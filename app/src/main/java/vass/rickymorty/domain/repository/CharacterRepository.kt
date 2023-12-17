package vass.rickymorty.domain.repository

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import vass.rickymorty.domain.model.SerieCharacter

interface CharacterRepository {
    suspend fun getCharacters(search: String?, status: String?): Flow<PagingData<SerieCharacter>>
    suspend fun getCharacterDetail(characterId: String?): ResultRM<SerieCharacter>
}
