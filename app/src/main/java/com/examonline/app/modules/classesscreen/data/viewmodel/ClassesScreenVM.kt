package com.examonline.app.modules.classesscreen.`data`.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.examonline.app.appcomponents.utility.PreferenceHelper
import com.examonline.app.modules.classesscreen.`data`.model.ClassesScreenModel
import com.examonline.app.modules.classesscreen.data.model.ClassesScreenRowModel
import com.examonline.app.modules.login.data.model.LoginModel
import com.examonline.app.network.models.createlogin.CreateLoginRequest
import com.examonline.app.network.models.createlogin.CreateLoginResponse
import com.examonline.app.network.models.getclasses.GetClassesResponse
import com.examonline.app.network.models.getprofile.GetProfileResponse
import com.examonline.app.network.models.repository.NetworkRepository
import com.examonline.app.network.models.resources.Response
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.launch
import org.koin.core.KoinComponent
import org.koin.core.inject

public class ClassesScreenVM : ViewModel(),KoinComponent {
  public val classesScreenModel: MutableLiveData<ClassesScreenModel> =
      MutableLiveData(ClassesScreenModel())
  public val recyclerViewList: MutableLiveData<MutableList<ClassesScreenRowModel>> =
  MutableLiveData(mutableListOf())
  public val progressLiveData: MutableLiveData<Boolean> = MutableLiveData<Boolean>()
  public val getClassesLiveData: MutableLiveData<Response<GetClassesResponse>> =
    MutableLiveData<Response<GetClassesResponse>>()
  private val networkRepository: NetworkRepository by inject()
  private val prefs: PreferenceHelper by inject()

  public fun onCreateClasses(): Unit {
    viewModelScope.launch {
      progressLiveData.postValue(true)
      getClassesLiveData.postValue(networkRepository.getClasses(
        authorization ="Bearer "+prefs.getToken()))
      progressLiveData.postValue(false)
    }
  }
  public fun bindGetClassesResponse(responseData: GetClassesResponse) {
    val recyclerViewListValue = recyclerViewList.value
    recyclerViewList.value?.let { recyclerViewList.value!!.removeAll(it) }
    for (r in responseData.data!!){
      val c = ClassesScreenRowModel(r.ClassName,r.TotalStudents,r.TeacherFullname,r.ClassID)
      recyclerViewListValue?.add(c)
    }
    recyclerViewList.value = recyclerViewListValue
  }
}
