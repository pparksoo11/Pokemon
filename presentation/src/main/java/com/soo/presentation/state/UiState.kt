package com.soo.presentation.state

sealed class UiState<out T> {
    object Loading : UiState<Nothing>()
    data class Success<T>(val data: T) : UiState<T>()
    data class Error(val type: ErrorType) : UiState<Nothing>()
}

sealed class ErrorType(val message: String) {
    object Network : ErrorType("네트워크 오류가 발생했습니다.")
    object Unknown : ErrorType("알 수 없는 오류가 발생했습니다.")
    // TODO 추후 에러 타입 추가 가능
}