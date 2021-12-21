package com.examonline.app.modules.forgotpasword.`data`.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.examonline.app.appcomponents.utility.PreferenceHelper
import com.examonline.app.modules.forgotpasword.`data`.model.ForgotPaswordModel
import com.examonline.app.network.models.createlogin.CreateLoginRequest
import com.examonline.app.network.models.createlogin.CreateLoginResponse
import com.examonline.app.network.models.repository.NetworkRepository
import com.examonline.app.network.models.resetpassword.ResetPasswordRequest
import com.examonline.app.network.models.resetpassword.ResetPasswordResponse
import com.examonline.app.network.models.resources.Response
import kotlinx.coroutines.launch
import org.koin.core.KoinComponent
import org.koin.core.inject

public class ForgotPaswordVM : ViewModel(), KoinComponent {
  public val forgotPaswordModel: MutableLiveData<ForgotPaswordModel> =
      MutableLiveData(ForgotPaswordModel())

    public val progressLiveData: MutableLiveData<Boolean> = MutableLiveData<Boolean>()
    private val networkRepository: NetworkRepository by inject()
    public val resetPasswordLiveData: MutableLiveData<Response<ResetPasswordResponse>> =
        MutableLiveData<Response<ResetPasswordResponse>>()
    private val prefs: PreferenceHelper by inject()
    public fun onClickBtnSubmit(): Unit {
        viewModelScope.launch {
            progressLiveData.postValue(true)
            resetPasswordLiveData.postValue(networkRepository.resetPassword(resetPasswordRequest =
            ResetPasswordRequest(
                Username = forgotPaswordModel.value?.editUsername,
                Email = forgotPaswordModel.value?.editEmail)
            ))
            progressLiveData.postValue(false)
        }
    }
}
