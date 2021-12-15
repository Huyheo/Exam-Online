package com.examonline.app.modules.detailhistoryscreen.`data`.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.examonline.app.modules.detailhistoryscreen.`data`.model.DetailHistoryScreenModel
import kotlin.collections.MutableList

public class DetailHistoryScreenVM : ViewModel() {
  public val detailHistoryScreenModel: MutableLiveData<DetailHistoryScreenModel> =
      MutableLiveData(DetailHistoryScreenModel())
}
