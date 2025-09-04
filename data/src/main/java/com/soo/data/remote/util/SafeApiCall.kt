package com.soo.data.remote.util

import com.google.gson.JsonSyntaxException
import com.soo.data.remote.error.NetworkError
import com.soo.data.remote.error.toDomain
import com.soo.domain.util.result.ApiResult
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import kotlin.coroutines.cancellation.CancellationException

// 공용 안전 실행 유틸: Envelope → ApiResult 표준화
internal suspend fun <T> safeApiCall(
    apiCall: suspend () -> Response<T>
): ApiResult<T> = try {
    val response = apiCall()

    if (response.isSuccessful) {
        val body = response.body()
        if(body != null) {
            ApiResult.Success(body)
        } else {
            ApiResult.Empty
        }
    } else {
        val code = response.code()
        val msg  = response.message()
        val err  = response.errorBody()?.string()
        ApiResult.Failure(NetworkError.Http(code, msg, err).toDomain())
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