package com.examonline.app.network.models.updatepassword

import com.google.gson.annotations.SerializedName

data class UpdatePasswordRequest (
    @field:SerializedName("OldPassword")
    val OldPassword: String? = null,
    @field:SerializedName("NewPassword")
    val NewPassword: String? = null
    )
