package com.soo.data.remote.model

// API 응답의 공통 envelope (예: { "status": ..., "data": ... })
data class ApiEnvelope<T> (
    val status: Int,
    val code: Int,
    val message: String? = null,
    val data: T? = null,
)