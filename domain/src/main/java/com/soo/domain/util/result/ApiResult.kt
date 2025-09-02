package com.soo.domain.util.result

sealed interface ApiResult<out T> {
    data class Success<T>(val body: T): ApiResult<T> // 성공: 유효한 본문(body)만 보유
    data object Empty : ApiResult<Nothing> // 성공: 본문 없음 (예: data = null, 빈 리스트)
    data class Failure(val error: DomainError) : ApiResult<Nothing> // 실패: 오류 정보만 보유

    val isSuccess get() = this is Success<*>
    fun getOrNull(): T? = (this as? Success<T>)?.body
}