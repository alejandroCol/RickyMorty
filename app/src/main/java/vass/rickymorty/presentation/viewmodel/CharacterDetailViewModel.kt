package vass.rickymorty.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import vass.rickymorty.domain.usecase.GetCharacterDetailUseCase
import vass.rickymorty.presentation.viewmodel.states.CharacterDetailScreenState
import javax.inject.Inject

@HiltViewModel
class CharacterDetailViewModel @Inject constructor(
    private val getCharacterDetail: GetCharacterDetailUseCase,
) :
    ViewModel() {

    private val _characterDetailState =
        MutableStateFlow<CharacterDetailScreenState>(CharacterDetailScreenState.Loading)
    val characterDetailState = _characterDetailState.asStateFlow()

    fun getCharacter(characterId: String?) {
        viewModelScope.launch {
            _characterDetailState.value = CharacterDetailScreenState.Loading

            val result = getCharacterDetail.invoke(characterId)
            if (result.isSuccess()) {
                _characterDetailState.value = CharacterDetailScreenState.Success(result.data)
            } else {
                _characterDetailState.value = CharacterDetailScreenState.Error(result.error)
            }
        }
    }
}
