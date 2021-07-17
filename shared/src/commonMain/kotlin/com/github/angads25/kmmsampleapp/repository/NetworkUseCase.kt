package com.github.angads25.kmmsampleapp.repository

import com.github.angads25.kmmsampleapp.data.ApiResponse
import com.github.angads25.kmmsampleapp.data.PexelImageResponse
import com.github.angads25.kmmsampleapp.network.NetworkClient
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import kotlin.coroutines.cancellation.CancellationException

class NetworkUseCase(val networkClient: Lazy<NetworkClient>) {

    suspend fun getNetworkData(query: String, page: String): ApiResponse<PexelImageResponse> {
        val response = networkClient.value.getNetworkClient().get<HttpResponse> {
            url {
                encodedPath = "/v1/search"
                parameters.apply {
                    append("query", query)
                    append("page", page)
                }
            }
        }

        return if (response.status == HttpStatusCode.OK) {
            val data = Json.decodeFromString<PexelImageResponse>(response.readText())
            ApiResponse(true, response.status.value, data, null)
        } else {
            ApiResponse(false, response.status.value, null, CancellationException(response.readText()))
        }
    }
}