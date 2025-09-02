package com.soo.data.remote.error

import java.io.IOException

// NetworkError → DomainError 매핑(도메인으로 올리는 표준 형태)
sealed interface NetworkError {
    data class Http(val code: Int, val message: String?, val rawBody: String?) : NetworkError
    data class Io(val cause: IOException) : NetworkError
    data class Parse(val cause: Throwable) : NetworkError
    data class Unknown(val cause: Throwable) : NetworkError
}