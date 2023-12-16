package vass.rickymorty.data.repository

import android.content.Context
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import vass.rickymorty.R
import vass.rickymorty.data.mapper.mapDtoToDomain
import vass.rickymorty.data.remote.api.RickAndMortyApiService
import vass.rickymorty.data.remote.network.ApiResponse
import vass.rickymorty.data.remote.network.ApiResponseHandler
import vass.rickymorty.data.source.CharacterPagingSource
import vass.rickymorty.data.util.Constants.MAX_PAGE_SIZE
import vass.rickymorty.domain.model.SerieCharacter
import vass.rickymorty.domain.repository.CharacterRepository
import javax.inject.Inject

class CharacterRepositoryImpl @Inject constructor(
    private val apiService: RickAndMortyApiService,
    private val apiResponseHandler: ApiResponseHandler,
    @ApplicationContext private val context: Context,
) : CharacterRepository {

    override suspend fun getCharacters(
        search: String?,
        status: String?,
    ): Flow<PagingData<SerieCharacter>> =
        Pager(
            config = PagingConfig(pageSize = MAX_PAGE_SIZE, enablePlaceholders = false),
            pagingSourceFactory = {
                CharacterPagingSource(
                    apiResponseHandler,
                    apiService,
                    context,
                    search,
                    status,
                )
            },
        ).flow

    override suspend fun getCharacterDetail(characterId: String?): SerieCharacter {
        val response = apiResponseHandler.handleApiCall {
            apiService.getCharacterDetail(characterId)
        }

        when (response) {
            is ApiResponse.Success -> {
                val data = response.data
                val character = data.mapDtoToDomain()
                return character
            }

            is ApiResponse.ResourceNotFound -> {
                return error
            }

            is ApiResponse.Error -> {
                return error
            }

            else -> return error
        }
    }
}
