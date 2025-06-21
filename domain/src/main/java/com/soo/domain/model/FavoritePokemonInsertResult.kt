package com.soo.domain.model

/**
 * 즐겨찾기 포켓몬 저장 결과를 나타내는 sealed class
 *
 * useCase에서 insert 후 결과를 ViewModel에 전달할 때 사용
 * */
sealed class FavoritePokemonInsertResult {
    object Success : FavoritePokemonInsertResult()
    object OverLimit : FavoritePokemonInsertResult()
    object AlreadyExists : FavoritePokemonInsertResult()
    data class Failure(val message: String? = null) : FavoritePokemonInsertResult()
}