package com.github.angads25.kmmsampleapp.network

import com.github.angads25.kmmsampleapp.network.features.RequestRetryFeature
import io.ktor.client.*
import io.ktor.client.features.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.features.logging.*
import io.ktor.http.*
import kotlinx.serialization.json.Json as KotlinJson

class NetworkClient {

    fun getNetworkClient(): HttpClient {
        return HttpClient {
            engine {
                threadsCount = 4
                pipelining = true
            }

            install(Logging) {
                logger = Logger.DEFAULT
                level = LogLevel.ALL
            }

            install(JsonFeature) {
                serializer = KotlinxSerializer(json =
                KotlinJson {
                    isLenient = true
                    ignoreUnknownKeys = true
                    useAlternativeNames = false
                })
            }

            install(RequestRetryFeature) {
                retryCount = 4
            }

            defaultRequest {
                url {
                    protocol = URLProtocol.HTTPS
                    host = "pixabay.com"
                }
            }
        }
    }
}