package com.soo.data.common

import retrofit2.Call
import retrofit2.CallAdapter
import java.lang.reflect.Type

class SafeApiCallAdapter<R : Any>(private val responseType: Type): CallAdapter<R, Call<ApiResult<R>>> {
    override fun responseType(): Type = responseType

    override fun adapt(call: Call<R>): Call<ApiResult<R>> = SafeApiCall(call)
}
