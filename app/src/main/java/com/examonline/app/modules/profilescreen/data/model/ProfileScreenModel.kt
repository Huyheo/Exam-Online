package com.examonline.app.modules.profilescreen.`data`.model

import com.examonline.app.R
import com.examonline.app.appcomponents.di.MyApp
import kotlin.String

public data class ProfileScreenModel(
  /**
   * TODO Replace with dynamic value
   */
  public var txtHello: String? = null
  ,
  /**
   * TODO Replace with dynamic value
   */
  public var txtProfile: String? = MyApp.getInstance().resources.getString(R.string.lbl_profile)
  ,
  /**
   * TODO Replace with dynamic value
   */
  public var txtResetPassword: String? =
      MyApp.getInstance().resources.getString(R.string.lbl_reset_password)
  ,
  /**
   * TODO Replace with dynamic value
   */
  public var txtFullName: String? = null
  ,
  /**
   * TODO Replace with dynamic value
   */
  public var txtEmail: String? = null,
  /**
   * TODO Replace with dynamic value
   */
  public var txtDateOfBirth: String? = null,
  /**
   * TODO Replace with dynamic value
   */
  public var txtGender: String? = null,
  /**
   * TODO Replace with dynamic value
   */
  public var txtPhoneNumber: String? = null,
  /**
   * TODO Replace with dynamic value
   */
  public var txtAddress: String? = null
)
