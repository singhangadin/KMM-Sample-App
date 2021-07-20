package com.github.angads25.kmmsampleapp.data

sealed class State {
    object empty: State()
    object loading: State()
    class result(val data: List<Hits>): State()
    class error(val message: String): State()
}