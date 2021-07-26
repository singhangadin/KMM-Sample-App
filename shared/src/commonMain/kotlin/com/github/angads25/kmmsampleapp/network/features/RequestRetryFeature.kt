package com.github.angads25.kmmsampleapp.network.features

import io.ktor.client.*
import io.ktor.client.features.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.util.*

class RequestRetryFeature constructor(val config: Config) {

    class Config {
        var retryCount: Int = 0
    }

    private fun handle(config: Config, scope: HttpClient) {
        scope.receivePipeline.intercept(HttpReceivePipeline.Before) { response ->
            if (context.response.status.value in 400..599) {
                var retryCount = config.retryCount

                var newResponse: HttpResponse = context.response
                while (retryCount > 0 && newResponse.status.value in 400..599) {
                    val client = HttpClient(scope.engine)
                    runCatching {
                        val requestBuilder = request {
                            url(context.request.url)
                            headers {
                                for (header in context.request.headers.names()) {
                                    append(header, context.request.headers[header] ?:"")
                                }
                            }
                            method = context.request.method
                            body = context.request.content
                        }
                        client.request(requestBuilder) as HttpResponse
                    }.onSuccess {
                        newResponse = it
                    }.onFailure {
                        it.printStackTrace()
                        newResponse = response
                    }
                    client.close()
                    retryCount--
                }
                if (newResponse == context.response) {
                    proceed()
                } else {
                    proceedWith(newResponse)
                }
            } else {
                proceed()
            }
        }
    }

    companion object : HttpClientFeature<Config, RequestRetryFeature> {
        private val KEY: AttributeKey<RequestRetryFeature> = AttributeKey("RequestRetryFeature")

        override fun prepare(block: Config.() -> Unit): RequestRetryFeature =
            RequestRetryFeature(Config().apply(block))

        override fun install(feature: RequestRetryFeature, scope: HttpClient) {
            feature.handle(feature.config, scope)
        }


        override val key: AttributeKey<RequestRetryFeature>
            get() = KEY
    }
}
