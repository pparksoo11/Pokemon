package com.soo.data.common

import okhttp3.Request
import okio.Timeout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class SafeApiCall<T: Any>(private val call: Call<T>) : Call<ApiResult<T>> {

    override fun enqueue(callback: Callback<ApiResult<T>>) {
        call.enqueue(object : Callback<T> {
            override fun onResponse(call: Call<T>, response: Response<T>) {
                val body = response.body()
                val code = response.code()
                val error = response.errorBody()?.string()

                if (response.isSuccessful) {
                    if (body != null) {
                        callback.onResponse(
                            this@SafeApiCall,
                            Response.success(ApiResult.Success(body))
                        )
                    } else {
                        callback.onResponse(
                            this@SafeApiCall,
                            Response.success(ApiResult.GenericError(code = code,
                                errorInfo = "UnknownError body값이 null로 넘어옴"))
                        )
                    }
                } else {
                    callback.onResponse(
                        this@SafeApiCall,
                        Response.success(ApiResult.GenericError(code = code, errorInfo = error))
                    )
                }
            }

            override fun onFailure(call: Call<T>, t: Throwable) {
                val errorResponse = when (t) {
                    is IOException -> ApiResult.GenericError(errorInfo = "IOException ${t}")
                    else -> ApiResult.GenericError(errorInfo = "onFailure에 진입,IoException 이외의 에러 ${t}")
                }
                callback.onResponse(this@SafeApiCall, Response.success(errorResponse))
            }
        })
    }

    override fun clone(): Call<ApiResult<T>> = SafeApiCall(call.clone())

    override fun execute(): Response<ApiResult<T>> {
        throw UnsupportedOperationException("커스텀한 callAdapter에서는 execute를 사용하지 않습니다 ")
    }

    override fun isExecuted(): Boolean = call.isExecuted

    override fun cancel() = call.cancel()

    override fun isCanceled(): Boolean = call.isCanceled

    override fun request(): Request = call.request()

    override fun timeout(): Timeout = call.timeout()
}