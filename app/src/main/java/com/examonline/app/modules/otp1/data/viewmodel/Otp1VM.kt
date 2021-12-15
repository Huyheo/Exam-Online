package com.examonline.app.modules.otp1.`data`.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.examonline.app.modules.otp1.`data`.model.Otp1Model

public class Otp1VM : ViewModel() {
  public val otp1Model: MutableLiveData<Otp1Model> = MutableLiveData(Otp1Model())
}
