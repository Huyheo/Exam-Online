package com.examonline.app.modules.editpassword.`data`.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.examonline.app.appcomponents.utility.PreferenceHelper
import com.examonline.app.modules.editpassword.`data`.model.EditPasswordModel
import com.examonline.app.network.models.repository.NetworkRepository
import com.examonline.app.network.models.resources.Response
import com.examonline.app.network.models.updatepassword.UpdatePasswordRequest
import com.examonline.app.network.models.updatepassword.UpdatePasswordResponse
import kotlinx.coroutines.launch
import org.koin.core.KoinComponent
import org.koin.core.inject

public class EditPasswordVM : ViewModel(), KoinComponent {
  public val editPasswordModel: MutableLiveData<EditPasswordModel> =
      MutableLiveData(EditPasswordModel())
    val progressLiveData: MutableLiveData<Boolean> = MutableLiveData<Boolean>()
    private val networkRepository: NetworkRepository by inject()
    val editPasswordLiveData: MutableLiveData<Response<UpdatePasswordResponse>> =
        MutableLiveData<Response<UpdatePasswordResponse>>()
    private val prefs: PreferenceHelper by inject()

    fun onClickBtnSave(oldPass: String, newPass: String) {
        viewModelScope.launch {
            progressLiveData.postValue(true)
            editPasswordLiveData.postValue(networkRepository.updatePassword(
                authorization ="Bearer "+prefs.getToken(),
                updatePasswordRequest = UpdatePasswordRequest(
                    OldPassword = oldPass,
                    NewPassword = newPass
                )
            )
            )
            progressLiveData.postValue(false)
        }
    }
}
