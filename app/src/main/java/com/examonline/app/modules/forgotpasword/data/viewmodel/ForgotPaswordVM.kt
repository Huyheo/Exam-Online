package com.examonline.app.modules.forgotpasword.`data`.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.examonline.app.modules.forgotpasword.`data`.model.ForgotPaswordModel

public class ForgotPaswordVM : ViewModel() {
  public val forgotPaswordModel: MutableLiveData<ForgotPaswordModel> =
      MutableLiveData(ForgotPaswordModel())
}
