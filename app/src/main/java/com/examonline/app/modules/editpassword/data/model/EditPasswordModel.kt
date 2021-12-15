package com.examonline.app.modules.editpassword.`data`.model

import com.examonline.app.R
import com.examonline.app.appcomponents.di.MyApp
import kotlin.String

public data class EditPasswordModel(
  /**
   * TODO Replace with dynamic value
   */
  public var txtResetPassword: String? =
      MyApp.getInstance().resources.getString(R.string.lbl_reset_password)
  ,
  /**
   * TODO Replace with dynamic value
   */
  public var editCurrentPass: String? = null
  ,
  /**
   * TODO Replace with dynamic value
   */
  public var editNewPass: String? = null
  ,
  /**
   * TODO Replace with dynamic value
   */
  public var editConfirmPass: String? = null
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
  public var txtConfirmPasswor: String? =
      MyApp.getInstance().resources.getString(R.string.msg_confirm_passwor)

)
