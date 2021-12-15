package com.examonline.app.modules.otp1.`data`.model

import com.examonline.app.R
import com.examonline.app.appcomponents.di.MyApp
import kotlin.String

public data class Otp1Model(
  /**
   * TODO Replace with dynamic value
   */
  public var txtHeader: String? =
      MyApp.getInstance().resources.getString(R.string.lbl_verify_otp)
)
