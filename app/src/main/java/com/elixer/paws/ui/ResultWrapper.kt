package com.elixer.paws.ui

sealed class ResultWrapper<out T> {
//    data class Success<out T>(val value: T, val status: String) : ResultWrapper<T>()
    data class Success<out T>(val value: T,  val status: String? ="", val statusResourceId: Int) : ResultWrapper<T>()
    data class GenericError(val statusResourceId: Int) : ResultWrapper<Nothing>()
}