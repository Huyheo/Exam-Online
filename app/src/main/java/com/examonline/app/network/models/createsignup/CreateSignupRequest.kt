package com.examonline.app.network.models.createsignup

import com.google.gson.annotations.SerializedName

data class CreateSignupRequest (
    @field:SerializedName("username")
    val username: String? = null,
    @field:SerializedName("password")
    val password: String? = null,
    @field:SerializedName("email")
    val email: String? = null
)
