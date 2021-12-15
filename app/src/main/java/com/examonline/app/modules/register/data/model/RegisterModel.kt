package com.examonline.app.modules.register.`data`.model

import com.examonline.app.R
import com.examonline.app.appcomponents.di.MyApp
import kotlin.String

public data class RegisterModel(
  /**
   * TODO Replace with dynamic value
   */
  public var txtRegister: String? = MyApp.getInstance().resources.getString(R.string.lbl_register)
  ,
  /**
   * TODO Replace with dynamic value
   */
  public var txtConfirmPassword: String? = MyApp.getInstance().resources.getString(R.string.msg_re_enter_new_pa)
  ,
  /**
   * TODO Replace with dynamic value
   */
  public var txtUserName: String? = MyApp.getInstance().resources.getString(R.string.lbl_user_name)
  ,
  /**
   * TODO Replace with dynamic value
   */
  public var txtEmail: String? = MyApp.getInstance().resources.getString(R.string.lbl_email)
  ,
  /**
   * TODO Replace with dynamic value
   */
  public var txtPassword: String? = MyApp.getInstance().resources.getString(R.string.lbl_password)
  ,
  /**
   * TODO Replace with dynamic value
   */
  public var txtAlreadyMember: String? =
      MyApp.getInstance().resources.getString(R.string.msg_already_member),
  public var etUsername: String? = null,
  public var etPassword: String? = null,
  public var etConfirmpass: String? = null,
  public var etEmail: String? = null


)
