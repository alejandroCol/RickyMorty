package vass.rickymorty.presentation.ui
/*
import androidx.paging.PagingData
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import vass.rickymorty.domain.model.SerieCharacter
import vass.rickymorty.domain.usecase.GetCharactersUseCase
import vass.rickymorty.util.MainDispatcherRule

@ExperimentalCoroutinesApi
class CharacterViewModelTest {

    private lateinit var viewModel: CharacterViewModel
    private lateinit var getCharactersUseCase: GetCharactersUseCase

    @Before
    fun setUp() {
        getCharactersUseCase = mockk(relaxed = true)

        viewModel = CharacterViewModel(getCharactersUseCase)
    }

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Test
    fun `when searchCharacters is called with a search query, it should invoke usecase`() =
        runBlocking {
            val searchQuery = "Rick"
            val pagingData = PagingData.from(
                listOf(
                    SerieCharacter(1, "Rick", "image1.jpg", "Description 1"),
                ),
            )

            coEvery { getCharactersUseCase.invoke(any()) } returns flowOf(pagingData)

            viewModel.searchCharacters(searchQuery)

            // Verificar que GetCharactersUseCase fue llamado
            coVerify { getCharactersUseCase.invoke(searchQuery) }
        }
}
*/