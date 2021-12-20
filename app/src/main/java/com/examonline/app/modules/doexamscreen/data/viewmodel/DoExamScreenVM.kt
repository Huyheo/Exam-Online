package com.examonline.app.modules.doexamscreen.`data`.viewmodel

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.examonline.app.appcomponents.utility.PreferenceHelper
import com.examonline.app.modules.doexamscreen.`data`.model.DoExamScreenModel
import com.examonline.app.modules.doexamscreen.data.model.DoExamScreenRowModel
import com.examonline.app.network.models.getallofexams.GetAllOfExamsResponse
import com.examonline.app.network.models.repository.NetworkRepository
import com.examonline.app.network.models.resources.Response
import kotlinx.coroutines.launch
import org.koin.core.KoinComponent
import org.koin.core.inject
import java.text.SimpleDateFormat
import java.util.*

public class DoExamScreenVM : ViewModel(), KoinComponent {
  public val doExamScreenModel: MutableLiveData<DoExamScreenModel> =
      MutableLiveData(DoExamScreenModel())

    public val recyclerViewList: MutableLiveData<MutableList<DoExamScreenRowModel>> =
        MutableLiveData(mutableListOf())

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
    public fun bindGetExamsResponse(responseData: GetAllOfExamsResponse) {
        val recyclerViewListValue = recyclerViewList.value
        recyclerViewList.value?.let { recyclerViewList.value!!.removeAll(it) }
        for (r in responseData.data!!){
            val duration : String
            if (r.Duration!! > 60){
                duration = r.Duration?.div(60).toString() + " Hour " +
                        r.Duration?.rem(60).toString() + " Minute"
            }
            else {
                duration = r.Duration.toString() + " Minute"
            }
            val c = DoExamScreenRowModel(r.ExamName,
                r.TotalQuestions.toString() + " Questions",
                duration,
                r.TimeBegin?.let { convertTime(it) },
                r.ClassName,
                r.TimeEnd?. let { convertTime(it) },
                r.ExamID.toString(),
                r.DoingFlag.toString(),
                r.TimeEnd?.time!! < System.currentTimeMillis(),
                r.Duration.toString()
            )
            recyclerViewListValue?.add(c)
        }
        recyclerViewList.value = recyclerViewListValue
    }

    @SuppressLint("SimpleDateFormat")
    private fun convertTime (time: Date): String? {
        val myFormat = "HH:mm dd/MM/yyyy"
        val sdf = SimpleDateFormat(myFormat)
        sdf.timeZone = TimeZone.getTimeZone("UTC")
        return sdf.format(time.time)
    }
}
