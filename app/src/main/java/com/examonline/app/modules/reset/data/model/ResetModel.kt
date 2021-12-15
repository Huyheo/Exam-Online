package com.examonline.app.modules.reset.`data`.model

import com.examonline.app.R
import com.examonline.app.appcomponents.di.MyApp
import kotlin.String

public data class ResetModel(
  /**
   * TODO Replace with dynamic value
   */
  public var txtForgotPassword: String? =
      MyApp.getInstance().resources.getString(R.string.lbl_forgot_password)
  ,
  /**
   * TODO Replace with dynamic value
   */
  public var txtNewPassword: String? =
      MyApp.getInstance().resources.getString(R.string.lbl_new_password)
  ,
  /**
   * TODO Replace with dynamic value
   */
  public var txtNewPasswordMu: String? =
      MyApp.getInstance().resources.getString(R.string.msg_new_password_mu)
  ,
  /**
   * TODO Replace with dynamic value
   */
  public var txtReEnterNewPa: String? =
      MyApp.getInstance().resources.getString(R.string.msg_re_enter_new_pa)

)
