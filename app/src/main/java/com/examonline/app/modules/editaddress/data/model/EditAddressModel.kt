package com.examonline.app.modules.editaddress.`data`.model

import com.examonline.app.R
import com.examonline.app.appcomponents.di.MyApp
import kotlin.String

public data class EditAddressModel(
  /**
   * TODO Replace with dynamic value
   */
  public var txtFullName: String? = MyApp.getInstance().resources.getString(R.string.lbl_full_name)
  ,
  /**
   * TODO Replace with dynamic value
   */
  public var editAddress: String? =null
)
