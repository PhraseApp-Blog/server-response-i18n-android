package com.elixer.paws.interacters

import com.elixer.paws.DogResponse
import com.elixer.paws.RetrofitService
import com.elixer.paws.ui.ResultWrapper
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class GetDogs(private val retrofitService: RetrofitService) {

    fun execute(breed: String): Flow<ResultWrapper<String>> = flow {
        try {
            val dogs = getDogsFromNetwork(breed)
            emit(ResultWrapper.Success(dogs.message, dogs.status))
        } catch (throwable: Throwable) {
            when (throwable) {
                is IOException -> emit(ResultWrapper.GenericError("No internet connection. Please try again"))
                is HttpException -> {
                    val errorResponse = convertErrorBody(throwable)
                    emit(errorResponse)
                }
                else -> {
                    ResultWrapper.GenericError("Something went wrong")
                }
            }
        }
    }

    private fun convertErrorBody(throwable: HttpException): ResultWrapper.GenericError {
        return try {
            throwable.response()?.errorBody()?.let {
                val errorResponse = Gson().fromJson(it.charStream(), DogResponse::class.java)
                return ResultWrapper.GenericError(errorResponse.message)
            } ?: ResultWrapper.GenericError("Something went wrong")
        } catch (exception: Exception) {
            return ResultWrapper.GenericError("Something went wrong")
        }
    }

    private suspend fun getDogsFromNetwork(breed: String): DogResponse {
        val dogArray = retrofitService.getDog(breed)
        return dogArray
    }
}