package com.examonline.app.network.models.getclasses

import com.google.gson.annotations.SerializedName

data class GetClassesResponse (
    @field:SerializedName("status")
    val status: Status? = null,
    @field:SerializedName("data")
    val data: List<Data>? = null,
    @field:SerializedName("message")
    val message: String? = null
)

data class Data(
    @field:SerializedName("ClassID") var ClassID : String? = null,
    @field:SerializedName("ClassName") var ClassName: String? = null,
    @field:SerializedName("TotalStudents") var TotalStudents: String? = null,
    @field:SerializedName("TeacherID") var TeacherID: String? = null,
    @field:SerializedName("TeacherFullname") var TeacherFullname: String? = null
)

data class Status(
    @field:SerializedName("Status") var Status : String? = null,
    @field:SerializedName("Code") var Code: String? = null,
)
