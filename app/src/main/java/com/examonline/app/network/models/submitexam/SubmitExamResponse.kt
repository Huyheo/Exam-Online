package com.examonline.app.network.models.submitexam

import com.google.gson.annotations.SerializedName
import java.time.LocalDateTime
import java.util.*

data class SubmitExamResponse (
    @field:SerializedName("status")
    val status: Status? = null,
    @field:SerializedName("data")
    val data: Data? = null,
    @field:SerializedName("message")
    val message: String? = null
)

data class Data(
    @field:SerializedName("ExamID") var ExamID: Int? = null,
    @field:SerializedName("UserID") var UserID: Int? = null,
    @field:SerializedName("Mark") var Mark: Float? = null,
    @field:SerializedName("DoingTime") var DoingTime: Int? = null,
    @field:SerializedName("ClassID") var ClassID: Int? = null,
    @field:SerializedName("CorrectNumber") var CorrectNumber: Float? = null,
    @field:SerializedName("TimeSubmit") var TimeSubmit: String? = null
)

data class Status(
    @field:SerializedName("Status") var Status : String? = null,
    @field:SerializedName("Code") var Code: String? = null
)
