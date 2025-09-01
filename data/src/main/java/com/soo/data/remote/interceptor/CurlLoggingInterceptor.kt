package com.soo.data.remote.interceptor

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response
import okio.Buffer
import java.nio.charset.Charset

class CurlLoggingInterceptor(
    private val isDebug: Boolean = true/*BuildConfig.DEBUG*/
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        if (!isDebug) { // Debug 모드가 아닐 땐 동작하지 않음
            return chain.proceed(chain.request())
        }

        val request = chain.request()

        val curlCmd = StringBuilder("curl -X ${request.method}")

        // URL
        curlCmd.append(" '${request.url}'")

        // Headers
        for (name in request.headers.names()) {
            val value = request.header(name)
            curlCmd.append(" -H \"$name: $value\"")
        }

        // Body (POST, PUT 등)
        request.body?.let { body ->
            val buffer = Buffer()
            body.writeTo(buffer)

            val charset: Charset = body.contentType()?.charset(Charset.forName("UTF-8"))
                ?: Charset.forName("UTF-8")

            val bodyString = buffer.readString(charset)
            if (bodyString.isNotEmpty()) {
                curlCmd.append(" --data '${bodyString}'")
            }
        }

        // 최종 출력
        Log.d("CURL", curlCmd.toString())

        return chain.proceed(request)
    }
}