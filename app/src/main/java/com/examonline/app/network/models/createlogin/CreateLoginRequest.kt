package com.examonline.app.network.models.createlogin
import com.google.gson.annotations.SerializedName
data class CreateLoginRequest(
    @field:SerializedName("username")
    val username: String? = null,
    @field:SerializedName("password")
    val password: String? = null
)