package com.examonline.app.network.models.updateprofile

import com.google.gson.annotations.SerializedName

data class UpdateProfileRequest (
    @field:SerializedName("Firstname")
    val Firstname: String? = null,
    @field:SerializedName("Lastname")
    val Lastname: String? = null,
    @field:SerializedName("Email")
    val Email: String? = null,
    @field:SerializedName("Gender")
    val Gender: Boolean? = null,
    @field:SerializedName("DateOfBirth")
    val DateOfBirth: String? = null,
    @field:SerializedName("Address")
    val Address: String? = null,
    @field:SerializedName("Phone")
    val Phone: String? = null,
    @field:SerializedName("Avatar")
    val Avatar: String? = null
)
