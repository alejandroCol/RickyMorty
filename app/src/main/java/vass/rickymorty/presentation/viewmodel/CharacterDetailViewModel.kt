package vass.rickymorty.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import vass.rickymorty.domain.repository.ResultRM
import vass.rickymorty.domain.usecase.GetCharacterDetailUseCase
import javax.inject.Inject

@HiltViewModel
class CharacterDetailViewModel @Inject constructor(
    private val getCharacterDetail: GetCharacterDetailUseCase,
) :
    ViewModel() {

    private val _characterDetailState = MutableStateFlow<CharacterDetailScreenState>(CharacterDetailScreenState.Loading)
    val characterDetailState = _characterDetailState.asStateFlow()

    fun getCharacter(characterId: String?) {
        viewModelScope.launch {
            _characterDetailState.value = CharacterDetailScreenState.Loading
            when (val result = getCharacterDetail.invoke(characterId)) {
                is ResultRM.Success -> {
                    _characterDetailState.value = CharacterDetailScreenState.Success(result.data)
                }
                is ResultRM.Error -> {
                    _characterDetailState.value = CharacterDetailScreenState.Error(result.message)
                }
            }
        }
    }
}
