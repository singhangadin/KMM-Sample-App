package com.github.angads25.kmmsampleapp.network

import io.ktor.client.*
import io.ktor.client.features.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.features.logging.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.util.*
import kotlinx.serialization.json.Json as KotlinJson

class NetworkClient {

    @KtorExperimentalAPI
    fun getNetworkClient(): HttpClient {
        return HttpClient {
            expectSuccess = false

            engine {
                threadsCount = 4
                pipelining = true
            }

            install(Logging) {
                logger = Logger.DEFAULT
                level = LogLevel.HEADERS
            }

            install(JsonFeature) {
                serializer = KotlinxSerializer(json =
                KotlinJson {
                    isLenient = true
                    ignoreUnknownKeys = true
                })
            }

            install(RequestRetryFeature) {
                retryCount = 4
            }

            install(PipelineLoggingFeature)

            defaultRequest {
                url {
                    protocol = URLProtocol.HTTPS
                    host = "api.pexels.com"
                }
                headers {
                    append("Authorization","")
                }
            }
        }
    }
}