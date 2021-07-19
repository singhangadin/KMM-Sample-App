package com.github.angads25.kmmsampleapp

import com.github.angads25.kmmsampleapp.di.KodeinInjector
import com.github.angads25.kmmsampleapp.repository.PexelImagesUseCase
import org.kodein.di.instance
import kotlin.native.concurrent.ThreadLocal

@ThreadLocal
object UseCaseProvider {
    val pexelImagesUseCase by KodeinInjector.instance<PexelImagesUseCase>()
}