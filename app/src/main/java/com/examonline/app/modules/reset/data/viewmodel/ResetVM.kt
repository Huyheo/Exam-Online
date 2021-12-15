package com.examonline.app.modules.reset.`data`.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.examonline.app.modules.reset.`data`.model.ResetModel

public class ResetVM : ViewModel() {
  public val resetModel: MutableLiveData<ResetModel> = MutableLiveData(ResetModel())
}
