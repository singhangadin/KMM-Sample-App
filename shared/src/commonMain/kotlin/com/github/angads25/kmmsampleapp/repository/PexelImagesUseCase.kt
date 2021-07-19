package com.github.angads25.kmmsampleapp.repository

import com.github.angads25.kmmsampleapp.AuthTokens
import com.github.angads25.kmmsampleapp.data.ApiResponse
import com.github.angads25.kmmsampleapp.data.PexelImageResponse
import com.github.angads25.kmmsampleapp.network.NetworkClient
import io.ktor.client.features.*
import io.ktor.client.request.*
import io.ktor.http.*

class PexelImagesUseCase(val networkClient: Lazy<NetworkClient>) {

    suspend fun getPexelImages(query: String, page: String): ApiResponse<PexelImageResponse> {
        return try {
            val response = networkClient.value.getNetworkClient().get<PexelImageResponse> {
                url {
                    encodedPath = "/v1/search"
                    parameters.apply {
                        append("query", query)
                        append("page", page)
                    }

                    headers {
                        append("Authorization", AuthTokens.PEXEL_AUTH_TOKEN)
                    }
                }
            }
            ApiResponse(true, HttpStatusCode.OK.value, response, null)
        } catch (exception: ResponseException) {
            ApiResponse(false, exception.response.status.value, null, exception)
        }
    }
}