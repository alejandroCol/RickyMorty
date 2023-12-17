package vass.rickymorty.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import vass.rickymorty.domain.model.SerieCharacter
import vass.rickymorty.domain.usecase.GetCharactersUseCase
import vass.rickymorty.presentation.ui.CharacterStatus
import javax.inject.Inject

@HiltViewModel
class CharacterViewModel @Inject constructor(
    private val getCharacters: GetCharactersUseCase,
) :
    ViewModel() {

    private val _characters: MutableStateFlow<PagingData<SerieCharacter>> =
        MutableStateFlow(value = PagingData.empty())
    val characters: MutableStateFlow<PagingData<SerieCharacter>> get() = _characters

    private var searchQuery: String? = ""
    private var status: CharacterStatus? = null
    init {
        onEvent(HomeEvent.LoadCharacters)
    }

    private fun onEvent(event: HomeEvent) {
        viewModelScope.launch {
            when (event) {
                is HomeEvent.LoadCharacters -> {
                    loadCharacters()
                }
            }
        }
    }

    fun searchCharacters(search: String?) {
        searchQuery = search
        onEvent(HomeEvent.LoadCharacters)
    }

    fun onFilterSelected(statusSelected: CharacterStatus) {
        status = statusSelected
        onEvent(HomeEvent.LoadCharacters)
    }

    private suspend fun loadCharacters() {
        getCharacters.invoke(searchQuery, status)
            .distinctUntilChanged()
            .cachedIn(viewModelScope)
            .collect {
                _characters.value = it
            }
    }

    sealed class HomeEvent {
        object LoadCharacters : HomeEvent()
    }
}
