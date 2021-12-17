package com.examonline.app.network.models.submitexam

import com.google.gson.annotations.SerializedName
import java.util.*

data class SubmitExamRequest (
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
