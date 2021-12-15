package com.examonline.app.network.models.createsignup

import com.google.gson.annotations.SerializedName

data class CreateSignupResponse (
    @field:SerializedName("status")
    val status: Status? = null,
    @field:SerializedName("message")
    val message: String? = null
)

data class Status(
    @field:SerializedName("Status") var Status : String? = null,
    @field:SerializedName("Code") var Code: String? = null,
)
