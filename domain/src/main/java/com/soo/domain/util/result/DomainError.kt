package com.soo.domain.util.result

interface DomainError {
    data class Business(val code: Int, val message: String?) : DomainError
    data object Unauthorized : DomainError
    data object Forbidden : DomainError
    data object NotFound : DomainError
    data object RateLimited : DomainError
    data object Network : DomainError
    data object Unavailable : DomainError
    data class Parse(val cause: Throwable) : DomainError
    data class Unknown(val cause: Throwable) : DomainError
}