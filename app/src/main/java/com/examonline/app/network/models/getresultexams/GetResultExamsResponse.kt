package com.examonline.app.network.models.getresultexams

import com.google.gson.annotations.SerializedName
import java.util.*

data class GetResultExamsResponse (
    @field:SerializedName("status")
    val status: Status? = null,
    @field:SerializedName("data")
    val data: List<Data>? = null,
    @field:SerializedName("message")
    val message: String? = null
)

data class Data(
    @field:SerializedName("ClassID") var ClassID: Int? = null,
    @field:SerializedName("ClassName") var ClassName: String? = null,
    @field:SerializedName("TeacherID") var TeacherID: Int? = null,
    @field:SerializedName("TeacherFullname") var TeacherFullname: String? = null,
    @field:SerializedName("ExamID") var ExamID: Int? = null,
    @field:SerializedName("ExamName") var ExamName: String? = null,
    @field:SerializedName("TimeBegin") var TimeBegin: Date? = null,
    @field:SerializedName("TimeEnd") var TimeEnd: Date? = null,
    @field:SerializedName("Duration") var Duration: Int? = null,
    @field:SerializedName("TotalQuestions") var TotalQuestions: Int? = null,
    @field:SerializedName("TimeSubmit") var TimeSubmit: Date? = null,
    @field:SerializedName("DoingTime") var DoingTime: Int? = null,
    @field:SerializedName("Mark") var Mark: Float? = null,
    @field:SerializedName("Accept") var Accept: Boolean? = null
)

data class Status(
    @field:SerializedName("Status") var Status : String? = null,
    @field:SerializedName("Code") var Code: String? = null
)
