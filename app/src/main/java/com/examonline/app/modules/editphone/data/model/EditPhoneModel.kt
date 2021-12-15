package com.examonline.app.modules.editphone.`data`.model

import com.examonline.app.R
import com.examonline.app.appcomponents.di.MyApp
import kotlin.String

public data class EditPhoneModel(
  /**
   * TODO Replace with dynamic value
   */
  public var txtPhoneNumber: String? =
      MyApp.getInstance().resources.getString(R.string.lbl_phone_number)
  ,
  /**
   * TODO Replace with dynamic value
   */
  public var editPhoneNumber: String? =null

)
