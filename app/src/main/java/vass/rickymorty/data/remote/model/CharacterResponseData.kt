package vass.rickymorty.data.remote.model

data class CharacterResponseData(
    val info: InfoData?,
    val results: List<CharacterData>,
)
