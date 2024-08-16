package com.lucianogiardino.mercadolivre.presentation

sealed class StateUI<out T> {
    object Waiting : StateUI<Nothing>()

    object Loading : StateUI<Nothing>()
    data class Success<out T>(val data: T) : StateUI<T>()
    data class Error(val exception: Throwable) : StateUI<Nothing>()
}