package com.examonline.app.modules.login.`data`.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.examonline.app.appcomponents.utility.PreferenceHelper
import com.examonline.app.modules.login.data.model.LoginModel
import com.examonline.app.network.models.createlogin.CreateLoginRequest
import com.examonline.app.network.models.createlogin.CreateLoginResponse
import com.examonline.app.network.models.repository.NetworkRepository
import com.examonline.app.network.models.resources.Response
import kotlinx.coroutines.launch
import org.koin.core.KoinComponent
import org.koin.core.inject
import java.util.*
import java.util.concurrent.TimeUnit

public class LoginVM : ViewModel(), KoinComponent{
  public val loginModel: MutableLiveData<LoginModel> = MutableLiveData(LoginModel())
  public val progressLiveData: MutableLiveData<Boolean> = MutableLiveData<Boolean>()
  private val networkRepository: NetworkRepository by inject()
  public val createLoginLiveData: MutableLiveData<Response<CreateLoginResponse>> =
    MutableLiveData<Response<CreateLoginResponse>>()
  private val prefs: PreferenceHelper by inject()
  public fun onClickBtnSignIn(): Unit {
    viewModelScope.launch {
      progressLiveData.postValue(true)
      createLoginLiveData.postValue(networkRepository.createLogin(createLoginRequest =
      CreateLoginRequest(
        username = loginModel.value?.etUsername,
        password = loginModel.value?.etPassword)
      ))
      progressLiveData.postValue(false)
    }
  }
  public fun bindCreateLoginResponse(responseData: CreateLoginResponse): Unit {
    val loginModelValue = loginModel.value ?:LoginModel()
    prefs.setToken(responseData.accessToken)
    if (responseData.data?.Avatar!=null){
      prefs.setAvatar(responseData.data.Avatar)
    }
    prefs.setEmail(responseData.data?.Email)
    prefs.setUserID(responseData.data?.UserID)
    prefs.setUserName(responseData.data?.Username)
    prefs.setIsLogin(true)
    if (responseData.data?.RoleID=="3")
      prefs.setIsStudent(true)
    prefs.setTimeExpire(System.currentTimeMillis()+ TimeUnit.DAYS.toMillis(1))
    loginModel.value = loginModelValue
  }
}
