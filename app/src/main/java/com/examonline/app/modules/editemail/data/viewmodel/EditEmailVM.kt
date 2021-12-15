package com.examonline.app.modules.editemail.`data`.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.examonline.app.appcomponents.utility.PreferenceHelper
import com.examonline.app.modules.editemail.`data`.model.EditEmailModel
import com.examonline.app.network.models.repository.NetworkRepository
import com.examonline.app.network.models.resources.Response
import com.examonline.app.network.models.updateprofile.UpdateProfileRequest
import com.examonline.app.network.models.updateprofile.UpdateProfileResponse
import kotlinx.coroutines.launch
import org.koin.core.KoinComponent
import org.koin.core.inject

public class EditEmailVM : ViewModel(), KoinComponent {
  public val editEmailModel: MutableLiveData<EditEmailModel> = MutableLiveData(EditEmailModel())
  val progressLiveData: MutableLiveData<Boolean> = MutableLiveData<Boolean>()
  private val networkRepository: NetworkRepository by inject()
  val editEmailLiveData: MutableLiveData<Response<UpdateProfileResponse>> =
    MutableLiveData<Response<UpdateProfileResponse>>()
  private val prefs: PreferenceHelper by inject()

  fun onClickBtnSave(updateProfileRequest: UpdateProfileRequest) {
    viewModelScope.launch {
      progressLiveData.postValue(true)
      editEmailLiveData.postValue(networkRepository.updateProfile(
        authorization ="Bearer "+prefs.getToken(),
        updateProfileRequest = updateProfileRequest
      )
      )
      progressLiveData.postValue(false)
    }
  }
}
