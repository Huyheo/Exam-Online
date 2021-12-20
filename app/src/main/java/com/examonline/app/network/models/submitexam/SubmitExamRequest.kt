package com.examonline.app.network.models.submitexam

import com.examonline.app.network.models.getexam.Question
import com.google.gson.annotations.SerializedName
import java.util.*

data class SubmitExamRequest (
    @field:SerializedName("ExamID") var ExamID: Int? = null,
    @field:SerializedName("ExamName") var ExamName: String? = null,
    @field:SerializedName("TimeBegin") var TimeBegin: Date? = null,
    @field:SerializedName("TimeEnd") var TimeEnd: Date? = null,
    @field:SerializedName("Duration") var Duration: Int? = null,
    @field:SerializedName("ClassID") var ClassID: Int? = null,
    @field:SerializedName("DoingTime") var DoingTime: Int? = null,
    @field:SerializedName("ClassName") var ClassName: String? = null,
    @field:SerializedName("Questions") var Questions: List<Question>? = null
)

data class Solution(
    @field:SerializedName("SolutionID") var SolutionID: Int? = null,
    @field:SerializedName("Solution") var Solution: String? = null,
    @field:SerializedName("Correct") var Correct: Int? = null
)
