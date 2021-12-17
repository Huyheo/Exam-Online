package com.examonline.app.network.models.repository
import com.examonline.app.R
import com.examonline.app.appcomponents.di.MyApp
import com.examonline.app.extensions.NoInternetConnection
import com.examonline.app.extensions.isOnline
import com.examonline.app.network.RetrofitServices
import com.examonline.app.network.models.createlogin.CreateLoginRequest
import com.examonline.app.network.models.createlogin.CreateLoginResponse
import com.examonline.app.network.models.createsignup.CreateSignupRequest
import com.examonline.app.network.models.createsignup.CreateSignupResponse
import com.examonline.app.network.models.getallofexams.GetAllOfExamsResponse
import com.examonline.app.network.models.getclasses.GetClassesResponse
import com.examonline.app.network.models.getexam.GetExamResponse
import com.examonline.app.network.models.getmemofclass.GetMemOfClassResponse
import com.examonline.app.network.models.getprofile.GetProfileResponse
import com.examonline.app.network.models.resources.ErrorResponse
import com.examonline.app.network.models.resources.Response
import com.examonline.app.network.models.resources.SuccessResponse
import com.examonline.app.network.models.submitexam.SubmitExamRequest
import com.examonline.app.network.models.submitexam.SubmitExamResponse
import com.examonline.app.network.models.updatepassword.UpdatePasswordRequest
import com.examonline.app.network.models.updatepassword.UpdatePasswordResponse
import com.examonline.app.network.models.updateprofile.UpdateProfileRequest
import com.examonline.app.network.models.updateprofile.UpdateProfileResponse
import java.lang.Exception
import kotlin.String
import org.koin.core.KoinComponent
import org.koin.core.inject
public class NetworkRepository : KoinComponent {
    private val retrofitServices: RetrofitServices by inject()
    private val errorMessage: String = "Something went wrong."
    public suspend fun getProfile(authorization: String?):
            Response<GetProfileResponse> = try {
        val isOnline = MyApp.getInstance().isOnline()
        if(isOnline) {
            SuccessResponse(retrofitServices.getProfile(authorization))
        } else {
            val internetException =
                NoInternetConnection(MyApp.getInstance().getString(R.string.no_internet_connection))
            ErrorResponse(internetException.message ?:errorMessage, internetException)
        }
    }
    catch(e:Exception) {
        e.printStackTrace()
        ErrorResponse(e.message ?:errorMessage, e)
    }
    public suspend fun getClasses(authorization: String?):
            Response<GetClassesResponse> = try {
        val isOnline = MyApp.getInstance().isOnline()
        if(isOnline) {
            SuccessResponse(retrofitServices.getClasses(authorization))
        } else {
            val internetException =
                NoInternetConnection(MyApp.getInstance().getString(R.string.no_internet_connection))
            ErrorResponse(internetException.message ?:errorMessage, internetException)
        }
    }
    catch(e:Exception) {
        e.printStackTrace()
        ErrorResponse(e.message ?:errorMessage, e)
    }
    public suspend fun getMemOfClass(authorization: String?,classID: String?):
            Response<GetMemOfClassResponse> = try {
        val isOnline = MyApp.getInstance().isOnline()
        if(isOnline) {
            SuccessResponse(retrofitServices.getMemOfClass(authorization,classID))
        } else {
            val internetException =
                NoInternetConnection(MyApp.getInstance().getString(R.string.no_internet_connection))
            ErrorResponse(internetException.message ?:errorMessage, internetException)
        }
    }
    catch(e:Exception) {
        e.printStackTrace()
        ErrorResponse(e.message ?:errorMessage, e)
    }

    public suspend fun getAllOfExams(authorization: String?):
            Response<GetAllOfExamsResponse> = try {
        val isOnline = MyApp.getInstance().isOnline()
        if(isOnline) {
            SuccessResponse(retrofitServices.getAllOfExams(authorization))
        } else {
            val internetException =
                NoInternetConnection(MyApp.getInstance().getString(R.string.no_internet_connection))
            ErrorResponse(internetException.message ?:errorMessage, internetException)
        }
    }
    catch(e:Exception) {
        e.printStackTrace()
        ErrorResponse(e.message ?:errorMessage, e)
    }

    public suspend fun getExam(authorization: String?, examID: String?):
            Response<GetExamResponse> = try {
        val isOnline = MyApp.getInstance().isOnline()
        if(isOnline) {
            SuccessResponse(retrofitServices.getExam(authorization, examID))
        } else {
            val internetException =
                NoInternetConnection(MyApp.getInstance().getString(R.string.no_internet_connection))
            ErrorResponse(internetException.message ?:errorMessage, internetException)
        }
    }
    catch(e:Exception) {
        e.printStackTrace()
        ErrorResponse(e.message ?:errorMessage, e)
    }

    public suspend fun submitExam(authorization: String?, submitExamRequest: SubmitExamRequest?):
            Response<SubmitExamResponse> = try {
        val isOnline = MyApp.getInstance().isOnline()
        if(isOnline) {
            SuccessResponse(retrofitServices.submitExam(authorization, submitExamRequest))
        } else {
            val internetException =
                NoInternetConnection(MyApp.getInstance().getString(R.string.no_internet_connection))
            ErrorResponse(internetException.message ?:errorMessage, internetException)
        }
    }
    catch(e:Exception) {
        e.printStackTrace()
        ErrorResponse(e.message ?:errorMessage, e)
    }

    public suspend fun updateProfile(authorization: String?,updateProfileRequest: UpdateProfileRequest?):
            Response<UpdateProfileResponse> = try {
        val isOnline = MyApp.getInstance().isOnline()
        if(isOnline) {
            SuccessResponse(retrofitServices.updateProfile(authorization, updateProfileRequest))
        } else {
            val internetException =
                NoInternetConnection(MyApp.getInstance().getString(R.string.no_internet_connection))
            ErrorResponse(internetException.message ?:errorMessage, internetException)
        }
    }
    catch(e:Exception) {
        e.printStackTrace()
        ErrorResponse(e.message ?:errorMessage, e)
    }

    public suspend fun updatePassword(authorization: String?,updatePasswordRequest: UpdatePasswordRequest?):
            Response<UpdatePasswordResponse> = try {
        val isOnline = MyApp.getInstance().isOnline()
        if(isOnline) {
            SuccessResponse(retrofitServices.updatePassword(authorization, updatePasswordRequest))
        } else {
            val internetException =
                NoInternetConnection(MyApp.getInstance().getString(R.string.no_internet_connection))
            ErrorResponse(internetException.message ?:errorMessage, internetException)
        }
    }
    catch(e:Exception) {
        e.printStackTrace()
        ErrorResponse(e.message ?:errorMessage, e)
    }

    public suspend fun createLogin(createLoginRequest: CreateLoginRequest?):
            Response<CreateLoginResponse> = try {
        val isOnline = MyApp.getInstance().isOnline()
        if(isOnline) {
            SuccessResponse(retrofitServices.createLogin(createLoginRequest))
        } else {
            val internetException =
                NoInternetConnection(MyApp.getInstance().getString(R.string.no_internet_connection))
            ErrorResponse(internetException.message ?:errorMessage, internetException)
        }
    }
    catch(e:Exception) {
        e.printStackTrace()
        ErrorResponse(e.message ?:errorMessage, e)
    }

    public suspend fun createSignup(createSignupRequest: CreateSignupRequest?):
            Response<CreateSignupResponse> = try {
        val isOnline = MyApp.getInstance().isOnline()
        if(isOnline) {
            SuccessResponse(retrofitServices.createSignup(createSignupRequest))
        } else {
            val internetException =
                NoInternetConnection(MyApp.getInstance().getString(R.string.no_internet_connection))
            ErrorResponse(internetException.message ?:errorMessage, internetException)
        }
    }
    catch(e:Exception) {
        e.printStackTrace()
        ErrorResponse(e.message ?:errorMessage, e)
    }
}