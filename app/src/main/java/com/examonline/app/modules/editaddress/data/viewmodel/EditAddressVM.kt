package com.examonline.app.modules.editaddress.`data`.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.examonline.app.appcomponents.utility.PreferenceHelper
import com.examonline.app.modules.editaddress.data.model.EditAddressModel
import com.examonline.app.network.models.repository.NetworkRepository
import com.examonline.app.network.models.resources.Response
import com.examonline.app.network.models.updateprofile.UpdateProfileRequest
import com.examonline.app.network.models.updateprofile.UpdateProfileResponse
import kotlinx.coroutines.launch
import org.koin.core.KoinComponent
import org.koin.core.inject

public class EditAddressVM : ViewModel(), KoinComponent {
  public val editAddressModel: MutableLiveData<EditAddressModel> = MutableLiveData(EditAddressModel())
  val progressLiveData: MutableLiveData<Boolean> = MutableLiveData<Boolean>()
  private val networkRepository: NetworkRepository by inject()
  val editAddressLiveData: MutableLiveData<Response<UpdateProfileResponse>> =
    MutableLiveData<Response<UpdateProfileResponse>>()
  private val prefs: PreferenceHelper by inject()

  fun onClickBtnSave(updateProfileRequest: UpdateProfileRequest) {
    viewModelScope.launch {
      progressLiveData.postValue(true)
      editAddressLiveData.postValue(networkRepository.updateProfile(
        authorization ="Bearer "+prefs.getToken(),
        updateProfileRequest = updateProfileRequest
      )
      )
      progressLiveData.postValue(false)
    }
  }
}
