package com.examonline.app.modules.examscreen.`data`.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.examonline.app.appcomponents.utility.PreferenceHelper
import com.examonline.app.modules.doexamscreen.data.model.DoExamScreenRowModel
import com.examonline.app.modules.examscreen.`data`.model.ExamScreenModel
import com.examonline.app.network.models.getexam.GetExamResponse
import com.examonline.app.network.models.getexam.Question
import com.examonline.app.network.models.repository.NetworkRepository
import com.examonline.app.network.models.resources.Response
import com.examonline.app.network.models.submitexam.SubmitExamRequest
import com.examonline.app.network.models.submitexam.SubmitExamResponse
import kotlinx.coroutines.launch
import org.koin.core.KoinComponent
import org.koin.core.inject
import java.text.SimpleDateFormat
import java.time.Duration
import java.util.*

public class ExamScreenVM : ViewModel(), KoinComponent {
  public val examScreenModel: MutableLiveData<ExamScreenModel> = MutableLiveData(ExamScreenModel())

  public val QuestionList: MutableLiveData<MutableList<Question>> =
    MutableLiveData(mutableListOf())

  public var Duration : MutableLiveData<Long> = MutableLiveData(0)

  public val progressLiveData: MutableLiveData<Boolean> = MutableLiveData<Boolean>()
  public val getExamLiveData: MutableLiveData<Response<GetExamResponse>> =
    MutableLiveData<Response<GetExamResponse>>()
  public val submitExamLiveData: MutableLiveData<Response<SubmitExamResponse>> =
    MutableLiveData<Response<SubmitExamResponse>>()
  private val networkRepository: NetworkRepository by inject()
  private val prefs: PreferenceHelper by inject()

  public fun onCreateDoExam(examID: String): Unit {
    viewModelScope.launch {
      progressLiveData.postValue(true)
      getExamLiveData.postValue(networkRepository.getExam(
        authorization ="Bearer "+prefs.getToken(),
        examID = examID
      ))
      progressLiveData.postValue(false)
    }
  }
  public fun bindGetExamResponse(responseData: GetExamResponse) {
    Duration.value = (responseData.data?.Duration!!*60*1000).toLong()
    QuestionList.value = responseData.data.Questions as MutableList<Question>?
  }

  public fun onSubmit(submitExamRequest: SubmitExamRequest): Unit {
    viewModelScope.launch {
      progressLiveData.postValue(true)
      submitExamLiveData.postValue(networkRepository.submitExam(
        authorization ="Bearer "+prefs.getToken(),
        submitExamRequest = submitExamRequest
      ))
      progressLiveData.postValue(false)
    }
  }
}
