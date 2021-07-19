package com.github.angads25.kmmsampleapp.di

import com.github.angads25.kmmsampleapp.network.NetworkClient
import com.github.angads25.kmmsampleapp.repository.PexelImagesUseCase
import com.github.angads25.kmmsampleapp.repository.PixadayImagesUseCase
import org.kodein.di.DI
import org.kodein.di.bindSingleton

val KodeinInjector = DI {
    val client = lazy {
        NetworkClient()
    }
    bindSingleton { PexelImagesUseCase(client) }
    bindSingleton { PixadayImagesUseCase(client) }
}