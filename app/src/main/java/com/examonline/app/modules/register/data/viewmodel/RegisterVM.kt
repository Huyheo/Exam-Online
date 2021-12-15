package com.examonline.app.modules.register.`data`.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.examonline.app.appcomponents.utility.PreferenceHelper
import com.examonline.app.modules.login.data.model.LoginModel
import com.examonline.app.modules.register.`data`.model.RegisterModel
import com.examonline.app.network.models.createlogin.CreateLoginRequest
import com.examonline.app.network.models.createlogin.CreateLoginResponse
import com.examonline.app.network.models.createsignup.CreateSignupRequest
import com.examonline.app.network.models.createsignup.CreateSignupResponse
import com.examonline.app.network.models.repository.NetworkRepository
import com.examonline.app.network.models.resources.Response
import kotlinx.coroutines.launch
import org.koin.core.KoinComponent
import org.koin.core.inject

public class RegisterVM : ViewModel(), KoinComponent {
  public val registerModel: MutableLiveData<RegisterModel> = MutableLiveData(RegisterModel())
  public val progressLiveData: MutableLiveData<Boolean> = MutableLiveData<Boolean>()
  private val networkRepository: NetworkRepository by inject()
  public val createLoginLiveData: MutableLiveData<Response<CreateSignupResponse>> =
    MutableLiveData<Response<CreateSignupResponse>>()
  private val prefs: PreferenceHelper by inject()

  public fun onClickBtnSignUp(): Unit {
    viewModelScope.launch {
      progressLiveData.postValue(true)
      createLoginLiveData.postValue(networkRepository.createSignup(createSignupRequest =
      CreateSignupRequest(
        username = registerModel.value?.etUsername,
        email = registerModel.value?.etEmail,
        password = registerModel.value?.etPassword)
      ))
      progressLiveData.postValue(false)
    }
  }

  public fun bindCreateSignupResponse(responseData: CreateSignupResponse): Unit {
    val registerModelValue = registerModel.value ?: RegisterModel()
    registerModel.value = registerModelValue
  }
}
