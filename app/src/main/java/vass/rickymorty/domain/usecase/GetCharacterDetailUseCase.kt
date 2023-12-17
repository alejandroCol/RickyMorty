package vass.rickymorty.domain.usecase

import vass.rickymorty.domain.model.SerieCharacter
import vass.rickymorty.domain.repository.CharacterRepository
import vass.rickymorty.domain.repository.ResultRM
import javax.inject.Inject

class GetCharacterDetailUseCase @Inject constructor(private val repository: CharacterRepository) {
    suspend operator fun invoke(search: String?): ResultRM<SerieCharacter> {
        return repository.getCharacterDetail(search)
    }
}
