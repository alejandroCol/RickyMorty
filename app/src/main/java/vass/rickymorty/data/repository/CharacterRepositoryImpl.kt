package vass.rickymorty.data.repository

import android.content.Context
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import vass.rickymorty.R
import vass.rickymorty.data.mapper.mapDtoToDomain
import vass.rickymorty.data.remote.api.RickAndMortyApiService
import vass.rickymorty.data.remote.model.CharacterData
import vass.rickymorty.data.remote.network.ApiResponse
import vass.rickymorty.data.remote.network.ApiResponseHandler
import vass.rickymorty.data.source.CharacterPagingSource
import vass.rickymorty.data.util.Constants.MAX_PAGE_SIZE
import vass.rickymorty.domain.model.SerieCharacter
import vass.rickymorty.domain.repository.CharacterRepository
import vass.rickymorty.domain.repository.ResultRM
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

    override suspend fun getCharacterDetail(characterId: String?): ResultRM<SerieCharacter> {
        val response = apiResponseHandler.handleApiCall {
            apiService.getCharacterDetail(characterId)
        }

        return when (response) {
            is ApiResponse.Success -> {
                handleSuccessResponse(response.data)
            }

            is ApiResponse.ResourceNotFound -> {
                handleError(context.getString(R.string.searching_error_message))
            }

            is ApiResponse.Error -> {
                handleError(context.getString(R.string.network_error_message))
            }

            else -> {
                handleError(context.getString(R.string.default_error_message))
            }
        }
    }

    private fun handleSuccessResponse(data: CharacterData): ResultRM<SerieCharacter> {
        val character = data.mapDtoToDomain()
        return ResultRM.success(character)
    }

    private fun handleError(errorMessage: String): ResultRM<SerieCharacter> {
        return ResultRM.error(errorMessage)
    }
}
