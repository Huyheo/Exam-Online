package com.examonline.app.modules.otp.`data`.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.examonline.app.modules.otp.`data`.model.OtpModel

public class OtpVM : ViewModel() {
  public val otpModel: MutableLiveData<OtpModel> = MutableLiveData(OtpModel())
}
