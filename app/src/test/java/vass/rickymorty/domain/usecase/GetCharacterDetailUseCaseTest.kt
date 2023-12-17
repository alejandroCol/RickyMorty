package vass.rickymorty.domain.usecase

import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import vass.rickymorty.domain.model.SerieCharacter
import vass.rickymorty.domain.repository.CharacterRepository
import vass.rickymorty.domain.repository.ResultRM

class GetCharacterDetailUseCaseTest {

    private lateinit var repository: CharacterRepository
    private lateinit var useCase: GetCharacterDetailUseCase

    @Before
    fun setUp() {
        repository = mockk()
        useCase = GetCharacterDetailUseCase(repository)
    }

    @Test
    fun `When invoke with valid search, Then should return Success`() = runBlocking {
        // Given
        val search = "characterId"
        val character = SerieCharacter(
            1,
            "Rick",
            "image.jpg",
            "human",
            "alive",
        )
        coEvery { repository.getCharacterDetail(search) } returns ResultRM.success(character)

        // When
        val result = useCase.invoke(search)

        // Then
        assertEquals(ResultRM.success(character).data, result.data)
    }

    @Test
    fun `When invoke with invalid search, Then should return Error`() = runBlocking {
        // Given
        val search = null // Invalid search
        val errorMessage = "Invalid search"
        coEvery { repository.getCharacterDetail(search) } returns ResultRM.error(errorMessage)

        // When
        val result = useCase.invoke(search)

        // Then
        assertEquals(ResultRM.error(errorMessage).error, result.error)
    }
}
