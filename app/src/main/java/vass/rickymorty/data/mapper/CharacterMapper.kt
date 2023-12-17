package vass.rickymorty.data.mapper

import vass.rickymorty.data.remote.model.CharacterData
import vass.rickymorty.data.remote.model.CharacterResponseData
import vass.rickymorty.domain.model.CharacterResponse
import vass.rickymorty.domain.model.SerieCharacter

fun CharacterData.mapDtoToDomain(): SerieCharacter = SerieCharacter(
    id,
    name ?: "",
    image ?: "",
    species ?: "",
    status ?: "",
)

fun CharacterResponseData.mapDtoToDomain(): CharacterResponse = CharacterResponse(
    info,
    results.map { dto -> dto.mapDtoToDomain() },
)
