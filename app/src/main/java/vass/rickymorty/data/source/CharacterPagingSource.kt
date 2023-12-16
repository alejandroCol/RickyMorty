package vass.rickymorty.data.source

import android.content.Context
import androidx.paging.PagingSource
import androidx.paging.PagingState
import vass.rickymorty.R
import vass.rickymorty.data.mapper.mapDtoToDomain
import vass.rickymorty.data.remote.api.RickAndMortyApiService
import vass.rickymorty.data.remote.network.ApiResponse
import vass.rickymorty.data.remote.network.ApiResponseHandler
import vass.rickymorty.domain.model.SerieCharacter
import java.io.IOException

class CharacterPagingSource(
    private val apiResponseHandler: ApiResponseHandler,
    private val apiService: RickAndMortyApiService,
    private val context: Context,
    private val searchQuery: String?,
    private val status: String?,
) : PagingSource<Int, SerieCharacter>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, SerieCharacter> {
        try {
            val page = params.key ?: 1

            val response = apiResponseHandler.handleApiCall {
                apiService.getCharacters(searchQuery, status, page)
            }

            when (response) {
                is ApiResponse.Success -> {
                    val data = response.data
                    val characters = data.mapDtoToDomain().results

                    val prevKey = if (page == 1) null else page - 1
                    val nextKey = if (data.info?.next != null) page + 1 else null

                    return LoadResult.Page(
                        data = characters,
                        prevKey = prevKey,
                        nextKey = nextKey,
                    )
                }

                is ApiResponse.ResourceNotFound -> {
                    return LoadResult.Error(Exception(context.getString(R.string.searching_error_message)))
                }

                is ApiResponse.Error -> {
                    return LoadResult.Error(Exception(context.getString(R.string.network_error_message)))
                }

                else -> return LoadResult.Error(Exception(context.getString(R.string.default_error_message)))
            }
        } catch (exception: IOException) {
            return LoadResult.Error(Exception(context.getString(R.string.network_error_message)))
        }
    }

    override fun getRefreshKey(state: PagingState<Int, SerieCharacter>): Int? {
        return state.anchorPosition
    }
}
