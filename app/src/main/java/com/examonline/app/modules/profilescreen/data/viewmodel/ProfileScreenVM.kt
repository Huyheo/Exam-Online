package com.examonline.app.modules.profilescreen.`data`.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.examonline.app.appcomponents.utility.PreferenceHelper
import com.examonline.app.modules.profilescreen.`data`.model.ProfileScreenModel
import com.examonline.app.network.models.getprofile.GetProfileResponse
import com.examonline.app.network.models.repository.NetworkRepository
import com.examonline.app.network.models.resources.Response
import kotlinx.coroutines.launch
import org.koin.core.KoinComponent
import org.koin.core.inject
import java.text.SimpleDateFormat
import java.util.*


class ProfileScreenVM : ViewModel(), KoinComponent {
    val profileModel: MutableLiveData<ProfileScreenModel> = MutableLiveData(ProfileScreenModel())
    val progressLiveData: MutableLiveData<Boolean> = MutableLiveData<Boolean>()
    private val networkRepository: NetworkRepository by inject()
    private val prefs: PreferenceHelper by inject()
    val getProfileLiveData: MutableLiveData<Response<GetProfileResponse>> =
        MutableLiveData<Response<GetProfileResponse>>()
    fun onClickOnCreate() {
        viewModelScope.launch {
            progressLiveData.postValue(true)
            getProfileLiveData.postValue(networkRepository.getProfile(
                authorization ="Bearer "+prefs.getToken()))
            progressLiveData.postValue(false)
        }
    }
    fun bindCreateUserResponse(responseData: GetProfileResponse) {
        val profileModelValue = profileModel.value ?:ProfileScreenModel()
        profileModelValue.txtEmail = responseData.data?.get(0)?.Email
        if (responseData.data?.get(0)?.Firstname?.isNotEmpty()==true)
            profileModelValue.txtFullName = responseData.data[0].Firstname +" "+ responseData.data[0].Lastname
        if(responseData.data?.get(0)?.Gender == true)
            profileModelValue.txtGender = "Male"
        else if(responseData.data?.get(0)?.Gender == false)
            profileModelValue.txtGender = "Female"
        val date = responseData.data?.get(0)?.DateOfBirth
        if (date!=null){
            val myFormat = "dd/MM/yyyy"
            val sdf = SimpleDateFormat(myFormat, Locale.getDefault())
            profileModelValue.txtDateOfBirth = sdf.format(date.time)
        }
        else profileModelValue.txtDateOfBirth = null
        profileModelValue.txtPhoneNumber = responseData.data?.get(0)?.Phone
        profileModelValue.txtAddress = responseData.data?.get(0)?.Address
        profileModel.value = profileModelValue
    }
}
