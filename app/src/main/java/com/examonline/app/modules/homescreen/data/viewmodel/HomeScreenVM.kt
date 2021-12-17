package com.examonline.app.modules.homescreen.`data`.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.examonline.app.appcomponents.utility.PreferenceHelper
import com.examonline.app.modules.homescreen.`data`.model.HomeScreenModel
import com.examonline.app.network.models.getallofexams.GetAllOfExamsResponse
import com.examonline.app.network.models.repository.NetworkRepository
import com.examonline.app.network.models.resources.Response
import kotlinx.coroutines.launch
import org.koin.core.KoinComponent
import org.koin.core.inject
import java.text.SimpleDateFormat
import java.util.*

public class HomeScreenVM : ViewModel(), KoinComponent {
  public val homeScreenModel: MutableLiveData<HomeScreenModel> = MutableLiveData(HomeScreenModel())
  public val progressLiveData: MutableLiveData<Boolean> = MutableLiveData<Boolean>()
  public val getExamsLiveData: MutableLiveData<Response<GetAllOfExamsResponse>> =
    MutableLiveData<Response<GetAllOfExamsResponse>>()
  private val networkRepository: NetworkRepository by inject()
  private val prefs: PreferenceHelper by inject()

  public fun onCreateExams(): Unit {
    viewModelScope.launch {
      progressLiveData.postValue(true)
      getExamsLiveData.postValue(networkRepository.getAllOfExams(
        authorization ="Bearer "+prefs.getToken()))
      progressLiveData.postValue(false)
    }
  }

}
