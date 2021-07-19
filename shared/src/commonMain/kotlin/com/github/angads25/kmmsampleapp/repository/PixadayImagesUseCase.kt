package com.github.angads25.kmmsampleapp.repository

import com.github.angads25.kmmsampleapp.AuthTokens
import com.github.angads25.kmmsampleapp.data.ApiResponse
import com.github.angads25.kmmsampleapp.data.PixabayImageResponse
import com.github.angads25.kmmsampleapp.network.NetworkClient
import io.ktor.client.features.*
import io.ktor.client.request.*
import io.ktor.http.*

class PixadayImagesUseCase(val networkClient: Lazy<NetworkClient>) {

    suspend fun getPixadayImages(query: String, page: String): ApiResponse<PixabayImageResponse> {
        return try {
            val response = networkClient.value.getNetworkClient().get<PixabayImageResponse> {
                url {
                    encodedPath = "/api/"
                    parameters.apply {
                        append("key", AuthTokens.PIXADAY_AUTH_TOKEN)
                        append("q", query)
                        append("page", page)
                    }
                }
            }
            ApiResponse(true, HttpStatusCode.OK.value, response, null)
        } catch (exception: ResponseException) {
            ApiResponse(false, exception.response.status.value, null, exception)
        }
    }
}