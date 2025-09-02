package com.soo.data.remote.error

import com.soo.domain.util.result.DomainError

// NetworkError → DomainError 매핑(도메인으로 올리는 표준 형태)
internal fun NetworkError.toDomain(): DomainError = when (this) {
    is NetworkError.Http -> when (code) {
        401 -> DomainError.Unauthorized
        403 -> DomainError.Forbidden
        404 -> DomainError.NotFound
        429 -> DomainError.RateLimited
        503, 504 -> DomainError.Unavailable
        else -> DomainError.Business(code, message)
    }
    is NetworkError.Io -> DomainError.Network
    is NetworkError.Parse -> DomainError.Parse(cause)
    is NetworkError.Unknown -> DomainError.Unknown(cause)
}