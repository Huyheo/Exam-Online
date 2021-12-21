package com.examonline.app.network.models.resetpassword

import com.google.gson.annotations.SerializedName

data class ResetPasswordRequest (
    @field:SerializedName("Username")
    val Username: String? = null,
    @field:SerializedName("Email")
    val Email: String? = null
)
