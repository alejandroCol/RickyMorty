package vass.rickymorty.data.source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import vass.rickymorty.data.mapper.mapDtoToDomain
import vass.rickymorty.data.remote.api.RickAndMortyApiService
import vass.rickymorty.domain.model.SerieCharacter
import java.io.IOException

class CharacterPagingSource(
    private val apiService: RickAndMortyApiService,
    private val searchQuery: String?,
) : PagingSource<Int, SerieCharacter>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, SerieCharacter> {
        try {
            val page = params.key ?: 1 // Página inicial
            val response = apiService.getCharacters(searchQuery, page)
            val characters = response.mapDtoToDomain().results

            val prevKey = if (page == 1) null else page - 1
            val nextKey = if (response.info.next != null) page + 1 else null

            return LoadResult.Page(
                data = characters,
                prevKey = prevKey,
                nextKey = nextKey,
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, SerieCharacter>): Int? {
        return state.anchorPosition
    }
}
