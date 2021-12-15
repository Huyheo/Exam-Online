package com.examonline.app.modules.detailscreen.`data`.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.examonline.app.modules.detailscreen.`data`.model.DetailScreenModel

public class DetailScreenVM : ViewModel() {
  public val detailScreenModel: MutableLiveData<DetailScreenModel> =
      MutableLiveData(DetailScreenModel())
}
