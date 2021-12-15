package com.examonline.app.network.models.getprofile

import com.google.gson.annotations.SerializedName
import java.util.*

data class GetProfileResponse(
    @field:SerializedName("status")
    val status: Status? = null,
    @field:SerializedName("data")
    val data: List<Data>? = null,
    @field:SerializedName("message")
    val message: String? = null
)
data class Data(
    @field:SerializedName("UserID") var UserID : String? = null,
    @field:SerializedName("Username") var Username: String? = null,
    @field:SerializedName("Password") var Password: String? = null,
    @field:SerializedName("Firstname") var Firstname: String? = null,
    @field:SerializedName("Lastname") var Lastname: String? = null,
    @field:SerializedName("Email") var Email: String? = null,
    @field:SerializedName("Gender") var Gender: Boolean? = null,
    @field:SerializedName("DateOfBirth") var DateOfBirth: Date? = null,
    @field:SerializedName("Phone") var Phone: String? = null,
    @field:SerializedName("Avatar") var Avatar: String? = null,
    @field:SerializedName("Address") var Address: String? = null,
    @field:SerializedName("Authentication") var Authentication: String? = null,
    @field:SerializedName("RoleID") var RoleID: String? = null,
    @field:SerializedName("RoleName") var RoleName: String? = null
)

data class Status(
    @field:SerializedName("Status") var Status : String? = null,
    @field:SerializedName("Code") var Code: String? = null,
)
