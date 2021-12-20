package com.examonline.app.modules.historyscreen.`data`.viewmodel

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.examonline.app.appcomponents.utility.PreferenceHelper
import com.examonline.app.modules.doexamscreen.data.model.DoExamScreenRowModel
import com.examonline.app.modules.historyscreen.`data`.model.HistoryScreenModel
import com.examonline.app.modules.historyscreen.data.model.HistoryScreenRowModel
import com.examonline.app.network.models.getallofexams.GetAllOfExamsResponse
import com.examonline.app.network.models.getresultexams.GetResultExamsResponse
import com.examonline.app.network.models.repository.NetworkRepository
import com.examonline.app.network.models.resources.Response
import kotlinx.coroutines.launch
import org.koin.core.KoinComponent
import org.koin.core.inject
import java.text.SimpleDateFormat
import java.util.*

public class HistoryScreenVM : ViewModel(), KoinComponent {
    public val historyScreenModel: MutableLiveData<HistoryScreenModel> =
      MutableLiveData(HistoryScreenModel())
    public val recyclerViewList: MutableLiveData<MutableList<HistoryScreenRowModel>> =
        MutableLiveData(mutableListOf())

    public val progressLiveData: MutableLiveData<Boolean> = MutableLiveData<Boolean>()
    public val getExamsLiveData: MutableLiveData<Response<GetResultExamsResponse>> =
        MutableLiveData<Response<GetResultExamsResponse>>()
    private val networkRepository: NetworkRepository by inject()
    private val prefs: PreferenceHelper by inject()

    public fun onCreateExams(): Unit {
        viewModelScope.launch {
            progressLiveData.postValue(true)
            getExamsLiveData.postValue(networkRepository.getResultExams(
                authorization ="Bearer "+prefs.getToken()))
            progressLiveData.postValue(false)
        }
    }

    public fun bindGetExamsResponse(responseData: GetResultExamsResponse) {
        val recyclerViewListValue = recyclerViewList.value
        recyclerViewList.value?.let { recyclerViewList.value!!.removeAll(it) }
        for (r in responseData.data!!){
            if (r.Accept == true) {
                val duration = if (r.Duration!! > 60) {
                    r.Duration?.div(60).toString() + " Hour " +
                            r.Duration?.rem(60).toString() + " Minute"
                } else {
                    r.Duration.toString() + " Minute"
                }
                val c = HistoryScreenRowModel(
                    r.ExamName,
                    r.TotalQuestions.toString() + " Questions",
                    duration,
                    r.ClassName,
                    r.ExamID.toString(),
                    r.TimeBegin?.let { convertTime(it.time) },
                    r.TimeEnd?.let { convertTime(it.time) },
                    r.TimeSubmit?.let { convertTime(it.time) },
                    r.TimeSubmit?.time?.minus(r.DoingTime!! * 60000)?.let { convertTime(it) },
                    r.Mark.toString(),
                    r.DoingTime.toString()
                )
                recyclerViewListValue?.add(c)
            }
        }
        recyclerViewList.value = recyclerViewListValue
    }

    @SuppressLint("SimpleDateFormat")
    private fun convertTime (time: Long): String? {
        val myFormat = "HH:mm dd/MM/yyyy"
        val sdf = SimpleDateFormat(myFormat)
        sdf.timeZone = TimeZone.getTimeZone("UTC")
        return sdf.format(time)
    }
}
