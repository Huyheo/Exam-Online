package com.examonline.app.network

import com.examonline.app.network.models.createlogin.CreateLoginRequest
import com.examonline.app.network.models.createlogin.CreateLoginResponse
import com.examonline.app.network.models.createsignup.CreateSignupRequest
import com.examonline.app.network.models.createsignup.CreateSignupResponse
import com.examonline.app.network.models.getallofexams.GetAllOfExamsResponse
import com.examonline.app.network.models.getclasses.GetClassesResponse
import com.examonline.app.network.models.getexam.GetExamResponse
import com.examonline.app.network.models.getmemofclass.GetMemOfClassResponse
import com.examonline.app.network.models.getprofile.GetProfileResponse
import com.examonline.app.network.models.submitexam.SubmitExamRequest
import com.examonline.app.network.models.submitexam.SubmitExamResponse
import com.examonline.app.network.models.updatepassword.UpdatePasswordRequest
import com.examonline.app.network.models.updatepassword.UpdatePasswordResponse
import com.examonline.app.network.models.updateprofile.UpdateProfileRequest
import com.examonline.app.network.models.updateprofile.UpdateProfileResponse
import retrofit2.http.*

interface RetrofitServices {

    @POST("/api/auth/login")
    public suspend fun createLogin(@Body createLoginRequest: CreateLoginRequest?): CreateLoginResponse

    @POST("/api/auth/signup")
    public suspend fun createSignup(@Body createLoginRequest: CreateSignupRequest?): CreateSignupResponse

    @GET("/api/profile")
    public suspend fun getProfile(@Header("Authorization") authorization: String?): GetProfileResponse

    @POST("/api/profile/update")
    public suspend fun updateProfile(@Header("Authorization") authorization: String?,
                                     @Body updateProfileRequest: UpdateProfileRequest?
                                     ): UpdateProfileResponse

    @POST("/api/profile/password")
    public suspend fun updatePassword(@Header("Authorization") authorization: String?,
                                     @Body updateProfileRequest: UpdatePasswordRequest?
                                     ): UpdatePasswordResponse

    @GET("/api/classes/student")
    public suspend fun getClasses(@Header("Authorization") authorization: String?): GetClassesResponse

    @GET("/api/classes/member/{ClassID}")
    public suspend fun getMemOfClass(@Header("Authorization") authorization: String?,
                                     @Path("ClassID") classID:String?): GetMemOfClassResponse

    @GET("/api/exams/student")
    public suspend fun getAllOfExams(@Header("Authorization") authorization: String?): GetAllOfExamsResponse

    @GET("/api/exams/do-exam/{ExamID}")
    public suspend fun getExam(@Header("Authorization") authorization: String?,
                               @Path("ExamID") examID:String?): GetExamResponse

    @POST("/api/exams/do-exam/submit")
    public suspend fun submitExam(@Header("Authorization") authorization: String?,
                                  @Body submitExamRequest: SubmitExamRequest?): SubmitExamResponse



}
public const val BASE_URL: String = "https://onlxam-e.herokuapp.com"