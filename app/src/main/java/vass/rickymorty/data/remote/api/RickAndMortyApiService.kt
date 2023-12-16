package vass.rickymorty.data.remote.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import vass.rickymorty.data.remote.model.CharacterData
import vass.rickymorty.data.remote.model.CharacterResponseData

interface RickAndMortyApiService {
    @GET("character")
    suspend fun getCharacters(
        @Query("name") searchTerm: String?,
        @Query("status") status: String?,
        @Query("page") page: Int?,
    ): Response<CharacterResponseData>

    @GET("character/{id}")
    suspend fun getCharacterDetail(
        @Path("id") characterId: String?,
    ): Response<CharacterData>
}
