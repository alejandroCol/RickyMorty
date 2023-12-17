package vass.rickymorty.domain.repository

sealed class ResultRM<out T> {
    data class Success<out T>(val data: T) : ResultRM<T>()
    data class Error(val message: String) : ResultRM<Nothing>()
}