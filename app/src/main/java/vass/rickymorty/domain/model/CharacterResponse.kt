package vass.rickymorty.domain.model

import vass.rickymorty.data.remote.model.InfoData

data class CharacterResponse(
    val info: InfoData,
    val results: List<SerieCharacter>
)
