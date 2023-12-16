package vass.rickymorty.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import vass.rickymorty.data.mapper.mapDtoToDomain
import vass.rickymorty.data.remote.api.RickAndMortyApiService
import vass.rickymorty.data.source.CharacterPagingSource
import vass.rickymorty.data.util.Constants.MAX_PAGE_SIZE
import vass.rickymorty.domain.model.SerieCharacter
import vass.rickymorty.domain.repository.CharacterRepository
import javax.inject.Inject

class CharacterRepositoryImpl @Inject constructor(
    private val apiService: RickAndMortyApiService,
) : CharacterRepository {

    override suspend fun getCharacters(search: String?): Flow<PagingData<SerieCharacter>> =
        Pager(
            config = PagingConfig(pageSize = MAX_PAGE_SIZE, enablePlaceholders = false),
            pagingSourceFactory = { CharacterPagingSource(apiService, search) },
        ).flow

    override suspend fun getCharacterDetail(characterId: String?): SerieCharacter =
        apiService.getCharacterDetail(characterId).mapDtoToDomain()
}
