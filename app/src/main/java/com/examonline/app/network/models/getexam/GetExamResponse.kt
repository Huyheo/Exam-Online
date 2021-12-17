package com.examonline.app.network.models.getexam

import com.google.gson.annotations.SerializedName
import java.util.*

data class GetExamResponse (
    @field:SerializedName("status")
    val status: Status? = null,
    @field:SerializedName("data")
    val data: Data? = null,
    @field:SerializedName("message")
    val message: String? = null
)
data class Data(
    @field:SerializedName("ExamID") var ExamID: Int? = null,
    @field:SerializedName("ExamName") var ExamName: String? = null,
    @field:SerializedName("TimeBegin") var TimeBegin: Date? = null,
    @field:SerializedName("TimeEnd") var TimeEnd: Date? = null,
    @field:SerializedName("Duration") var Duration: Int? = null,
    @field:SerializedName("ClassID") var ClassID: Int? = null,
    @field:SerializedName("ClassName") var ClassName: String? = null,
    @field:SerializedName("Questions") var Questions: List<Question>? = null
)

data class Question(
    @field:SerializedName("QuestionID") var QuestionID: Int? = null,
    @field:SerializedName("Question") var Question: String? = null,
    @field:SerializedName("Type") var Type: String? = null,
    @field:SerializedName("Level") var Level: String? = null,
    @field:SerializedName("LibraryFolderID") var LibraryFolderID: Int? = null,
    @field:SerializedName("Solution") var Solution: List<Solution>? = null
)

data class Solution(
    @field:SerializedName("SolutionID") var SolutionID: Int? = null,
    @field:SerializedName("Solution") var Solution: String? = null,
    @field:SerializedName("Correct") var Correct: Int? = null
)

data class Status(
    @field:SerializedName("Status") var Status : String? = null,
    @field:SerializedName("Code") var Code: String? = null
)
