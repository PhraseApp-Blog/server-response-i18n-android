package com.elixer.paws.ui

sealed class ResultWrapper<out T> {
    data class Success<out T>(val value: T, val status: String) : ResultWrapper<T>()
    data class GenericError(val status: String) : ResultWrapper<Nothing>()
}