package com.soo.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import com.soo.domain.model.FavoritePokemonInsertResult
import com.soo.domain.usecase.DeleteFavoritePokemonUseCase
import com.soo.domain.usecase.GetFavoritePokemonUseCase
import com.soo.domain.usecase.GetPokemonInfoUseCase
import com.soo.domain.usecase.InsertFavoritePokemonUseCase
import com.soo.presentation.base.BaseViewModel
import com.soo.presentation.mapper.toDetailUiModel
import com.soo.presentation.mapper.toDomain
import com.soo.presentation.model.PokemonInfoUiModel
import com.soo.presentation.state.ErrorType
import com.soo.presentation.state.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class PokemonInfoViewModel @Inject constructor(
    private val getPokemonInfoUseCase: GetPokemonInfoUseCase,
    private val insertFavoritePokemonUseCase: InsertFavoritePokemonUseCase,
    private val getFavoritePokemonUseCase: GetFavoritePokemonUseCase,
    private val deleteFavoritePokemonUseCase: DeleteFavoritePokemonUseCase
): BaseViewModel() {

    private val _pokemonInfoState = MutableStateFlow<UiState<PokemonInfoUiModel>>(UiState.Loading)
    val pokemonInfoState: StateFlow<UiState<PokemonInfoUiModel>> = _pokemonInfoState

    fun getPokemonInfo(name: String) = viewModelScope.launch {
        getPokemonInfoUseCase(name).onStart {
            _pokemonInfoState.value = UiState.Loading
        }.catch { e ->
            val errorType = when(e) {
                is IOException -> ErrorType.Network
                //TODO 다른 에러처리 추가 가능
                else -> ErrorType.Unknown
            }
            _pokemonInfoState.value = UiState.Error(errorType)
        }.collect {
            _pokemonInfoState.value = UiState.Success(it.toDetailUiModel())
        }
    }

    fun insertFavoritePokemon(pokemonInfo: PokemonInfoUiModel) = viewModelScope.launch {
        val domainPokemonInfo = pokemonInfo.toDomain()
        when(insertFavoritePokemonUseCase(domainPokemonInfo)) {
            is FavoritePokemonInsertResult.Success -> showToast("저장 성공")
            is FavoritePokemonInsertResult.AlreadyExists -> showToast("이미 저장된 포켓몬입니다")
            is FavoritePokemonInsertResult.OverLimit -> showToast("최대 10마리까지 저장 가능합니다")
            is FavoritePokemonInsertResult.Failure -> showToast("저장 실패")
        }
    }

    fun getFavoritePokemon(id: Int) = viewModelScope.launch {
        getFavoritePokemonUseCase(id).onStart {
            _pokemonInfoState.value = UiState.Loading
        }.catch { e ->
            val errorType = when(e) {
                is IOException -> ErrorType.Network
                //TODO 다른 에러처리 추가 가능
                else -> ErrorType.Unknown
            }
            _pokemonInfoState.value = UiState.Error(errorType)
        }.collect { result ->
            if (result != null) {
                _pokemonInfoState.value = UiState.Success(result.toDetailUiModel())
            } else {
                _pokemonInfoState.value = UiState.Error(ErrorType.Unknown)
            }
        }
    }

    fun deleteFavoritePokemon(id: Int) = viewModelScope.launch {
        val isDeleted = deleteFavoritePokemonUseCase(id)
        if (isDeleted) {
            showToast("삭제 성공")
        } else {
            showToast("존재하지 않는 데이터")
        }
    }
}