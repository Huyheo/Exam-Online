package com.examonline.app.modules.historyscreen.`data`.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.examonline.app.modules.doexamscreen.data.model.DoExamScreenRowModel
import com.examonline.app.modules.historyscreen.`data`.model.HistoryScreenModel
import com.examonline.app.modules.historyscreen.data.model.HistoryScreenRowModel

public class HistoryScreenVM : ViewModel() {
    public val historyScreenModel: MutableLiveData<HistoryScreenModel> =
      MutableLiveData(HistoryScreenModel())
    public val recyclerViewList: MutableLiveData<MutableList<HistoryScreenRowModel>> =
        MutableLiveData(mutableListOf())
}
