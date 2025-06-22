package com.soo.presentation.state

sealed class UiState<out T> {
    object Loading : UiState<Nothing>()
    data class Success<T>(val data: T) : UiState<T>()
    data class Error(val type: ErrorType) : UiState<Nothing>()
}

sealed class ErrorType {
    object Network : ErrorType()
    object Unknown : ErrorType()
    // TODO 추후 에러 타입 추가 가능
}