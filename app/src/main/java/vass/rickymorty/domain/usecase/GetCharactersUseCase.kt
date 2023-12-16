package vass.rickymorty.domain.usecase

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import vass.rickymorty.domain.model.SerieCharacter
import vass.rickymorty.domain.repository.CharacterRepository
import vass.rickymorty.presentation.ui.CharacterStatus
import javax.inject.Inject

class GetCharactersUseCase @Inject constructor(private val repository: CharacterRepository) {
    suspend operator fun invoke(search: String?, status: CharacterStatus?): Flow<PagingData<SerieCharacter>> {
        return repository.getCharacters(search, status?.name)
    }
}
