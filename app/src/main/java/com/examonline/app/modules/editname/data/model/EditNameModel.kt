package com.examonline.app.modules.editname.`data`.model

import com.examonline.app.R
import com.examonline.app.appcomponents.di.MyApp
import kotlin.String

public data class EditNameModel(
  /**
   * TODO Replace with dynamic value
   */
  public var txtFullName: String? = MyApp.getInstance().resources.getString(R.string.lbl_full_name)
  ,
  /**
   * TODO Replace with dynamic value
   */
  public var txtFullName1: String? = MyApp.getInstance().resources.getString(R.string.lbl_full_name)
  ,
  /**
   * TODO Replace with dynamic value
   */
  public var txtTranQuangHuy: String? =
      MyApp.getInstance().resources.getString(R.string.lbl_tran_quang_huy)
  ,
  /**
   * TODO Replace with dynamic value
   */
  public var txtFullNameWith: String? =
      MyApp.getInstance().resources.getString(R.string.msg_full_name_with)
  ,
  /**
   * TODO Replace with dynamic value
   */
  public var editFirstName: String? =null
  ,
  /**
   * TODO Replace with dynamic value
   */
  public var editLastName: String? =null

)
