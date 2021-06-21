package com.elixer.paws.interactors

import com.elixer.paws.DogResponse
import com.elixer.paws.StatusMessages
import com.elixer.paws.StatusMessages.Companion.INTERNET_CONNECTION_ERROR
import com.elixer.paws.RetrofitService
import com.elixer.paws.ui.ResultWrapper
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class GetDogs(private val retrofitService: RetrofitService) {

  fun execute(breed: String): Flow<ResultWrapper<String>> =
    flow {
      try {
        val dogs = getDogsFromNetwork(breed)
        //Get resource id for a 200 success response
        emit(
          ResultWrapper.Success(
            dogs.message,
            statusResourceId = StatusMessages.resourceIdFor(
              200
            )
          )
        )
      } catch (throwable: Throwable) {
        when (throwable) {
          is IOException -> emit(
            //Get resource id for no internet connection
            ResultWrapper.GenericError(
              statusResourceId = StatusMessages.resourceIdFor(
                INTERNET_CONNECTION_ERROR
              )
            )
          )
          is HttpException -> {
            val errorResponse = convertErrorBody(throwable)
            emit(errorResponse)
          }
          else -> {
            returnGenericStatusError()
          }
        }
      }
    }

  private fun convertErrorBody(throwable: HttpException): ResultWrapper.GenericError {
    return try {
      throwable.response()?.errorBody()?.let {
        val errorResponse = Gson().fromJson(
          it.charStream(),
          DogResponse::class.java
        )
        //Get resource id for the "code" value in error Response from the server
        return ResultWrapper.GenericError(
          StatusMessages.resourceIdFor(
            errorResponse.code
          )
        )
      } ?: returnGenericStatusError()
    } catch (exception: Exception) {
      return returnGenericStatusError()
    }
  }

  private fun returnGenericStatusError() =
    ResultWrapper.GenericError(StatusMessages.resourceIdFor(0))

  private suspend fun getDogsFromNetwork(breed: String): DogResponse {
    val dogArray = retrofitService.getDog(breed)
    return dogArray
  }
}