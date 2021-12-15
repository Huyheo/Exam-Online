package com.examonline.app.modules.joinclass.`data`.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.examonline.app.appcomponents.utility.PreferenceHelper
import com.examonline.app.modules.classesscreen.data.model.ClassesScreenRowModel
import com.examonline.app.modules.joinclass.`data`.model.JoinClassModel
import com.examonline.app.modules.login.data.model.LoginModel
import com.examonline.app.network.models.getclasses.GetClassesResponse
import com.examonline.app.network.models.memtoclass.MemToClassRequest
import com.examonline.app.network.models.memtoclass.MemToClassResponse
import com.examonline.app.network.models.repository.NetworkRepository
import com.examonline.app.network.models.resources.Response
import kotlinx.coroutines.launch
import org.koin.core.KoinComponent
import org.koin.core.inject

public class JoinClassVM : ViewModel(),KoinComponent {
  public val joinClassModel: MutableLiveData<JoinClassModel> = MutableLiveData(JoinClassModel())
  public val progressLiveData: MutableLiveData<Boolean> = MutableLiveData<Boolean>()
  public val putMemToClassLiveData: MutableLiveData<Response<MemToClassResponse>> =
    MutableLiveData<Response<MemToClassResponse>>()
  private val networkRepository: NetworkRepository by inject()
  private val prefs: PreferenceHelper by inject()

//  public fun onCreateAddMem(): Unit {
//    viewModelScope.launch {
//      progressLiveData.postValue(true)
//      putMemToClassLiveData.postValue(networkRepository.putMemToClass(
//        authorization ="Bearer "+prefs.getToken(),
//        classID = joinClassModel.value?.txtCode.toString(),
//        memOfClass = MemToClassRequest(prefs.getUserID())
//      ))
//      progressLiveData.postValue(false)
//    }
//  }
  public fun bindAddMemToClassResponse(responseData: MemToClassResponse) {
    val joinClassModelValue = joinClassModel.value?: JoinClassModel()
    joinClassModel.value = joinClassModelValue
  }
}
