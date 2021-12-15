package com.examonline.app.modules.startpage.`data`.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.examonline.app.modules.startpage.`data`.model.StartPageModel

public class StartPageVM : ViewModel() {
  public val startPageModel: MutableLiveData<StartPageModel> = MutableLiveData(StartPageModel())
}
