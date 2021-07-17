package com.github.angads25.kmmsampleapp.data

class ApiResponse<T> (
    val isSuccess: Boolean,
    val statusCode: Int,
    val data: T?,
    val exception: Throwable?
)