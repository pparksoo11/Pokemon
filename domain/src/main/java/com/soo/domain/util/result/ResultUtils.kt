package com.soo.domain.util.result

// ApiResult의 값(data)을 다른 타입으로 변환할 때 사용
// - Success일 경우: body를 f로 변환 후 새 Success로 감싼다
// - Empty/Failure일 경우: 그대로 반환 (상태 보존)
inline fun <A, B> ApiResult<A>.map(f: (A) -> B): ApiResult<B> = when (this) {
    is ApiResult.Success -> ApiResult.Success(f(this.body)) // 데이터 변환
    is ApiResult.Empty -> ApiResult.Empty                 // 빈 값 유지
    is ApiResult.Failure -> this                            // 실패 그대로 전파
}

// ApiResult를 다른 ApiResult로 변환하고 싶을 때 사용 (체이닝)
// - Success일 경우: body를 f에 넣고, f가 반환하는 ApiResult를 그대로 사용
// - Empty/Failure일 경우: 그대로 반환 (전파)
// → 여러 API 호출을 순차적으로 연결할 때 유용
inline fun <A, B> ApiResult<A>.flatMap(f: (A) -> ApiResult<B>): ApiResult<B> = when (this) {
    is ApiResult.Success -> f(this.body) // 결과를 이어붙임
    is ApiResult.Empty -> ApiResult.Empty
    is ApiResult.Failure -> this
}

// ApiResult의 모든 상태를 소비(consume)해서 최종 값 R을 만들 때 사용
// - Success: onSuccess(body)를 실행
// - Empty: onEmpty() 실행
// - Failure: onFailure(error) 실행
// → UI 메시지 변환, 로깅, 최종 데이터 가공 등에 유용
inline fun <T, R> ApiResult<T>.fold(
    onSuccess: (T) -> R,
    onEmpty: () -> R,
    onFailure: (DomainError) -> R
): R = when (this) {
    is ApiResult.Success -> onSuccess(this.body)
    is ApiResult.Empty -> onEmpty()
    is ApiResult.Failure -> onFailure(this.error)
}