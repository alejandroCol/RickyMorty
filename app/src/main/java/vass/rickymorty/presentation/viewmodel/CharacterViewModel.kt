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
import vass.rickymorty.presentation.ui.Option
import javax.inject.Inject

@HiltViewModel
class CharacterViewModel @Inject constructor(
    private val getCharacters: GetCharactersUseCase,
) :
    ViewModel() {

    private val _characters: MutableStateFlow<PagingData<SerieCharacter>> =
        MutableStateFlow(value = PagingData.empty())
    val characters: MutableStateFlow<PagingData<SerieCharacter>> get() = _characters

    init {
        onEvent(HomeEvent.LoadCharacters())
    }

    private fun onEvent(event: HomeEvent) {
        viewModelScope.launch {
            when (event) {
                is HomeEvent.LoadCharacters -> {
                    loadCharacters(event.search)
                }
            }
        }
    }

    fun searchCharacters(search: String?) {
        onEvent(HomeEvent.LoadCharacters(search))
    }

    fun onFilterSelected(option: Option) {
       // onEvent(HomeEvent.LoadCharacters(search))
    }

    private suspend fun loadCharacters(searchTerm: String? = "") {
        getCharacters.invoke(searchTerm)
            .distinctUntilChanged()
            .cachedIn(viewModelScope)
            .collect {
                _characters.value = it
            }
    }

    sealed class HomeEvent {
        class LoadCharacters(val search: String? = "") : HomeEvent()
    }
}
