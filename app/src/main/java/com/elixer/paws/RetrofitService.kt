package com.elixer.paws

import retrofit2.http.GET
import retrofit2.http.Path


interface RetrofitService {

        @GET("breed/{breed}/images/random")
        suspend fun getDog( @Path("breed") breed :String): DogResponse

}








