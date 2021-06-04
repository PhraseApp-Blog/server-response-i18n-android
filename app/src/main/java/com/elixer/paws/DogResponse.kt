package com.elixer.paws

import com.google.gson.annotations.SerializedName

data class DogResponse(

    @SerializedName("message")
    var message: String,

    @SerializedName("status")
    var status: String,

    @SerializedName("code")
    var code: Int,
)