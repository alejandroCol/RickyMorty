package vass.rickymorty.presentation.viewmodel

import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import vass.rickymorty.domain.model.SerieCharacter
import vass.rickymorty.domain.repository.ResultRM
import vass.rickymorty.domain.usecase.GetCharacterDetailUseCase
import vass.rickymorty.presentation.viewmodel.states.CharacterDetailScreenState
import vass.rickymorty.util.MainDispatcherRule

@ExperimentalCoroutinesApi
class CharacterDetailViewModelTest {

    private lateinit var viewModel: CharacterDetailViewModel

    @MockK
    private lateinit var getCharacterDetailUseCase: GetCharacterDetailUseCase

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Before
    fun setup() {
        MockKAnnotations.init(this, relaxUnitFun = true)

        viewModel = CharacterDetailViewModel(getCharacterDetailUseCase)
    }

    @Test
    fun `When call getCharacter, Then should update state correctly on success`() = runBlocking {
        // Given

        val characterId = "1"

        val characterDetail = SerieCharacter(
            1,
            "Rick",
            "image.jpg",
            "human",
            "alive",
        )

        val successResult: ResultRM<SerieCharacter> = ResultRM.success(characterDetail)

        coEvery { getCharacterDetailUseCase.invoke(characterId) } returns successResult

        // When
        viewModel.getCharacter(characterId)

        // Then

        assert(viewModel.characterDetailState.value is CharacterDetailScreenState.Success)
    }

    @Test
    fun `When call getCharacter, Then should update state correctly on error`() = runBlocking {
        // Given
        val characterId = "1"
        val errorMessage = "Error message"
        coEvery { getCharacterDetailUseCase.invoke(characterId) } returns ResultRM.error(
            errorMessage,
        )

        // When
        viewModel.getCharacter(characterId)

        // Then
        assert(viewModel.characterDetailState.value is CharacterDetailScreenState.Error)
    }
}
