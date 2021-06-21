package com.elixer.paws

internal class StatusMessages {

  companion object {
    const val INTERNET_CONNECTION_ERROR = -1

    fun resourceIdFor(statusCode: Int = 0): Int {
      return when (statusCode) {
        200 -> R.string.success_status
        404 -> R.string.error_breed_not_found

        //Custom error code for no internet connection
        INTERNET_CONNECTION_ERROR -> R.string.error_no_internet
        else -> R.string.error_generic
      }
    }
  }
}