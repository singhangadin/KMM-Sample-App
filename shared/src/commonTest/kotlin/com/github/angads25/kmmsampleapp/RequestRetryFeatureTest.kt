package com.github.angads25.kmmsampleapp

import com.github.angads25.kmmsampleapp.network.features.RequestRetryFeature
import io.ktor.client.*
import io.ktor.client.engine.mock.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.content.*
import io.ktor.http.*
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFails

class RequestRetryFeatureTest {

    private val authToken = "tHisIsAFakeaUthEntiCatiOntOkenIhoPePeoPlewoNtnOticEThis"
    private val baseUrl = "https://singhangad.in"

    private val retryCountRef = 4
    private var requestCount = 0

    private val testRequestBody = "<Some test request body>"

    private val Url.hostWithPortIfRequired: String get() = if (port == protocol.defaultPort) host else hostWithPort
    private val Url.urlWithoutPath: String get() = "${protocol.name}://$hostWithPortIfRequired"
    private val Url.fullUrl: String get() = "${protocol.name}://$hostWithPortIfRequired$fullPath"

    @BeforeTest
    fun onCreate() {
    }

    @Test
    fun testFeature() {
        runBlocking {
            kotlin.runCatching {
                getNetworkClient().get<HttpResponse> {
                    url(baseUrl)
                    headers.append("Authorization", "Bearer $authToken")
                }
            }.onSuccess {
                assertEquals(HttpStatusCode.OK, it.status, "Objects did not match")
                assertEquals(requestCount, retryCountRef + 1)
            }.onFailure {
                assertFails ("Must have not reached here") {}
            }
        }
    }

    @Test
    fun testGetRequest() {
        runBlocking {
            kotlin.runCatching {
                getNetworkClient().get<HttpResponse> {
                    url(baseUrl)
                    headers.append("Authorization", "Bearer $authToken")
                }
            }.onSuccess {
                assertEquals(HttpStatusCode.OK, it.status, "Objects did not match")
            }.onFailure {
                assertFails ("Must have not reached here") {}
            }
        }
    }

    @Test
    fun testPostRequest() {
        runBlocking {
            kotlin.runCatching {
                getNetworkClient().post<HttpResponse> {
                    url(baseUrl)
                    headers.append("Authorization", "Bearer $authToken")
                    body = testRequestBody
                }
            }.onSuccess {
                assertEquals(HttpStatusCode.OK, it.status, "Objects did not match")
            }.onFailure {
                assertFails ("Must have not reached here") {}
            }
        }
    }

    @Test
    fun testHeaders() {
        runBlocking {
            kotlin.runCatching {
                getNetworkClient().get<HttpResponse> {
                    url(baseUrl)
                    headers.append("Authorization", "Bearer $authToken")
                }
            }.onSuccess {
                assertEquals(HttpStatusCode.OK, it.status, "Objects did not match")
            }
        }
    }

    private fun getNetworkClient(): HttpClient {
        return HttpClient(MockEngine) {
            engine {
                addHandler { request ->
                    when (request.url.urlWithoutPath) {
                        baseUrl -> {
                            when(request.method) {
                                HttpMethod.Get -> {
                                    if (request.headers.contains("Authorization")) {
                                        if (requestCount != retryCountRef) {
                                            requestCount++
                                            respond(content = "Hello, world", status = HttpStatusCode.InternalServerError)
                                        } else {
                                            requestCount++
                                            respond(content = "Hello, world", status = HttpStatusCode.OK)
                                        }
                                    } else {
                                        requestCount++
                                        respond(content = "Authorization missing in headers", status = HttpStatusCode.Unauthorized)
                                    }
                                }

                                HttpMethod.Post -> {
                                    if (request.headers.contains("Authorization")) {
                                        if ((request.body as TextContent).text != testRequestBody) {
                                            requestCount++
                                            respond(content = "Request Body is missing", status = HttpStatusCode.BadRequest)
                                        }
                                        else if (requestCount != retryCountRef) {
                                            requestCount++
                                            respond(content = "Request failed due to Server error", status = HttpStatusCode.InternalServerError)
                                        } else {
                                            requestCount++
                                            respond(content = "Request Successful", status = HttpStatusCode.OK)
                                        }
                                    } else {
                                        requestCount++
                                        respond(content = "Authorization missing in headers", status = HttpStatusCode.Unauthorized)
                                    }
                                }

                                else -> error("Unhandled ${request.url.fullUrl}")
                            }
                        }

                        else -> error("Unhandled ${request.url.fullUrl}")
                    }
                }
            }

            install(RequestRetryFeature) {
                retryCount = retryCountRef
            }
        }
    }
}