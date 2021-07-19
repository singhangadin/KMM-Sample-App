package com.github.angads25.kmmsampleapp.network.features

import io.ktor.client.*
import io.ktor.client.features.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.util.*

class PipelineLoggingFeature constructor(val config: Config) {

    class Config

    companion object : HttpClientFeature<Config, PipelineLoggingFeature> {
        private val KEY: AttributeKey<PipelineLoggingFeature> = AttributeKey("PipelineLoggingFeature")

        override fun prepare(block: Config.() -> Unit): PipelineLoggingFeature =
            PipelineLoggingFeature(Config().apply(block))

        override fun install(feature: PipelineLoggingFeature, scope: HttpClient) {
            feature.handle(feature.config, scope)
        }


        override val key: AttributeKey<PipelineLoggingFeature>
            get() = KEY
    }

    private fun handle(config: Config, scope: HttpClient) {
        // Request pipeline
        scope.requestPipeline.intercept(HttpRequestPipeline.Before) {
            println("Request Pipeline: Before")
            proceed()
        }

        scope.requestPipeline.intercept(HttpRequestPipeline.State) {
            println("Request Pipeline: State")
            proceed()
        }

        scope.requestPipeline.intercept(HttpRequestPipeline.Transform) {
            println("Request Pipeline: Transform")
            proceed()
        }

        scope.requestPipeline.intercept(HttpRequestPipeline.Render) {
            println("Request Pipeline: Render")
            proceed()
        }

        scope.requestPipeline.intercept(HttpRequestPipeline.Send) {
            println("Request Pipeline: Send")
            proceed()
        }


        // Send Pipeline
        scope.sendPipeline.intercept(HttpSendPipeline.Before) {
            println("Send Pipeline: Before")
            proceed()
        }

        scope.sendPipeline.intercept(HttpSendPipeline.State) {
            println("Send Pipeline: State")
            proceed()
        }

        scope.sendPipeline.intercept(HttpSendPipeline.Monitoring) {
            println("Send Pipeline: Monitoring")
            proceed()
        }

        scope.sendPipeline.intercept(HttpSendPipeline.Engine) {
            println("Send Pipeline: Engine")
            proceed()
        }

        scope.sendPipeline.intercept(HttpSendPipeline.Receive) {
            println("Send Pipeline: Receive")
            proceed()
        }

        // Receive pipeline
        scope.receivePipeline.intercept(HttpReceivePipeline.Before) {
            println("Receive Pipeline: Before")
            proceed()
        }
        scope.receivePipeline.intercept(HttpReceivePipeline.State) {
            println("Receive Pipeline: State")
            proceed()
        }
        scope.receivePipeline.intercept(HttpReceivePipeline.After) {
            println("Receive Pipeline: After")
            proceed()
        }


        // Response pipeline
        scope.responsePipeline.intercept(HttpResponsePipeline.Receive) {
            println("Response Pipeline: Receive")
            proceed()
        }
        scope.responsePipeline.intercept(HttpResponsePipeline.Parse) {
            println("Response Pipeline: Parse")
            proceed()
        }
        scope.responsePipeline.intercept(HttpResponsePipeline.Transform) {
            println("Response Pipeline: Transform")
            proceed()
        }
        scope.responsePipeline.intercept(HttpResponsePipeline.State) {
            println("Response Pipeline: State")
            proceed()
        }
        scope.responsePipeline.intercept(HttpResponsePipeline.After) {
            println("Response Pipeline: After")
            proceed()
        }
    }
}