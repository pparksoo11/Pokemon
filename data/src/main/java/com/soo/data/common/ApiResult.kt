package com.soo.data.common

import android.util.Log

sealed class ApiResult<out T: Any> {
    // 응답 성공
    data class Success<T: Any>(val body: T): ApiResult<T>()

    // 에러
    data class GenericError(val code: Int? = null, val errorInfo: String? = null): ApiResult<Nothing>()
}

inline fun <reified T: Any, reified R: Any> ApiResult<T>.mapSuccess(mapper: (T) -> R): ApiResult<R> {
    return when(this) {
        is ApiResult.Success -> {
            Log.d("pys","isSuccessful body $body")
            ApiResult.Success(mapper(body))
        }
        is ApiResult.GenericError -> {
            Log.e("pys", "onFailure 실패 errorMessage: ${this.errorInfo}")
            this
        }
    }
}