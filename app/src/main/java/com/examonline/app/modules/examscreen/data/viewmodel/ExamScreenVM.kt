package com.examonline.app.modules.examscreen.`data`.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.examonline.app.modules.examscreen.`data`.model.ExamScreen1RowModel
import com.examonline.app.modules.examscreen.`data`.model.ExamScreenModel
import kotlin.collections.MutableList

public class ExamScreenVM : ViewModel() {
  public val examScreenModel: MutableLiveData<ExamScreenModel> = MutableLiveData(ExamScreenModel())

//  public val recyclerViewList: MutableLiveData<MutableList<ExamScreen1RowModel>> =
//      MutableLiveData(mutableListOf())
}
