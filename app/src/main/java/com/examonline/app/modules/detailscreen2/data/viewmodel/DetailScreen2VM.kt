package com.examonline.app.modules.detailscreen2.`data`.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.examonline.app.appcomponents.utility.PreferenceHelper
import com.examonline.app.modules.classesscreen.data.model.ClassesScreenRowModel
import com.examonline.app.modules.detailscreen2.`data`.model.DetailScreen2Model
import com.examonline.app.modules.detailscreen2.`data`.model.DetailScreen3RowModel
import com.examonline.app.modules.profilescreen.data.model.ProfileScreenModel
import com.examonline.app.network.models.createlogin.CreateLoginRequest
import com.examonline.app.network.models.getclasses.GetClassesResponse
import com.examonline.app.network.models.getmemofclass.GetMemOfClassResponse
import com.examonline.app.network.models.repository.NetworkRepository
import com.examonline.app.network.models.resources.Response
import kotlinx.coroutines.launch
import org.koin.core.KoinComponent
import org.koin.core.inject
import kotlin.collections.MutableList

public class DetailScreen2VM : ViewModel(),KoinComponent {
  public val detailScreen2Model: MutableLiveData<DetailScreen2Model> =
      MutableLiveData(DetailScreen2Model())

  public val recyclerContentList: MutableLiveData<MutableList<DetailScreen3RowModel>> =
      MutableLiveData(mutableListOf())
    public val progressLiveData: MutableLiveData<Boolean> = MutableLiveData<Boolean>()
    public val getMemOfClassLiveData: MutableLiveData<Response<GetMemOfClassResponse>> =
        MutableLiveData<Response<GetMemOfClassResponse>>()
    private val networkRepository: NetworkRepository by inject()
    private val prefs: PreferenceHelper by inject()


    public fun onCreateMemOfClass(classID: String): Unit {
        viewModelScope.launch {
            progressLiveData.postValue(true)
            getMemOfClassLiveData.postValue(networkRepository.getMemOfClass(
                authorization ="Bearer "+prefs.getToken(),
                classID = classID
            ))
            progressLiveData.postValue(false)
        }
    }
    public fun bindGetMemOfClassResponse(responseData: GetMemOfClassResponse) {
        val recyclerContentListValue = recyclerContentList.value
        for (r in responseData.data!!){
            val c = DetailScreen3RowModel(r.Lastname,r.Email,r.Phone,r.Avatar)
            recyclerContentListValue?.add(c)
        }
        recyclerContentList.value = recyclerContentListValue
    }
}
