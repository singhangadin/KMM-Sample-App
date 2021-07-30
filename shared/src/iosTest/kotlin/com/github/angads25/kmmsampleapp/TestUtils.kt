package com.github.angads25.kmmsampleapp

actual fun runBlocking(block: suspend () -> Unit) = kotlinx.coroutines.runBlocking { block() }