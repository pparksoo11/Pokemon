package com.soo.data.remote.util

import com.google.gson.JsonSyntaxException
import com.soo.data.remote.model.ApiEnvelope
import com.soo.domain.util.result.ApiResult
import com.soo.domain.util.result.DomainError
import retrofit2.HttpException
import java.io.IOException
import kotlin.coroutines.cancellation.CancellationException

// 공용 안전 실행 유틸: Envelope → ApiResult 표준화
internal suspend fun <T> safeApiCall(
    block: suspend () -> ApiEnvelope<T>
): ApiResult<T> = try {
    val env = block()

    if (env.code == 0) {
        val d = env.data
        when {
            d == null -> ApiResult.Empty // 성공이지만 본문 없음
            (d is Collection<*>) && d.isEmpty() -> ApiResult.Empty // 빈 컬렉션도 Empty
            else -> ApiResult.Success(d) // 정상 성공
        }
    } else {
        // 서버 비즈니스 규칙 오류(코드/메시지 전달)
        ApiResult.Failure(DomainError.Business(env.code, env.message))
    }
} catch (e: CancellationException) {
    // 코루틴 취소는 반드시 다시 던짐
    throw e
} catch (e: HttpException) {
    val body = e.response()?.errorBody()?.string()
    ApiResult.Failure(NetworkError.Http(e.code(), e.message(), body).toDomain())
} catch (e: IOException) {
    ApiResult.Failure(NetworkError.Io(e).toDomain())
} catch (e: JsonSyntaxException) { // Gson 역직렬화 실패
    ApiResult.Failure(NetworkError.Parse(e).toDomain())
} catch (e: IllegalStateException) { // Gson이 던질 수 있음
    ApiResult.Failure(NetworkError.Parse(e).toDomain())
} catch (e: Throwable) {
    ApiResult.Failure(NetworkError.Unknown(e).toDomain())
}

// Envelope 없는 엔드포인트용 (선택)
internal suspend fun <T> safeApiCallRaw(
    block: suspend () -> T
): ApiResult<T> = try {
    val d = block()
    when (d) {
        is Collection<*> -> if (d.isEmpty()) ApiResult.Empty else ApiResult.Success(d)
        else -> ApiResult.Success(d)
    }
} catch (e: CancellationException) {
    throw e
} catch (e: HttpException) {
    val body = e.response()?.errorBody()?.string()
    ApiResult.Failure(NetworkError.Http(e.code(), e.message(), body).toDomain())
} catch (e: IOException) {
    ApiResult.Failure(NetworkError.Io(e).toDomain())
} catch (e: JsonSyntaxException) {
    ApiResult.Failure(NetworkError.Parse(e).toDomain())
} catch (e: IllegalStateException) {
    ApiResult.Failure(NetworkError.Parse(e).toDomain())
} catch (e: Throwable) {
    ApiResult.Failure(NetworkError.Unknown(e).toDomain())
}