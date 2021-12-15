package com.examonline.app.modules.otp.`data`.model

import com.examonline.app.R
import com.examonline.app.appcomponents.di.MyApp
import kotlin.String

public data class OtpModel(
  /**
   * TODO Replace with dynamic value
   */
  public var txtVerifyOtp: String? =
      MyApp.getInstance().resources.getString(R.string.lbl_verify_otp)
  ,
  /**
   * TODO Replace with dynamic value
   */
  public var txtPleaseEnterTh: String? =
      MyApp.getInstance().resources.getString(R.string.msg_please_enter_th)
  ,
  /**
   * TODO Replace with dynamic value
   */
  public var txt7: String? = MyApp.getInstance().resources.getString(R.string.lbl_7)
  ,
  /**
   * TODO Replace with dynamic value
   */
  public var txt8: String? = MyApp.getInstance().resources.getString(R.string.lbl_7)
  ,
  /**
   * TODO Replace with dynamic value
   */
  public var txt9: String? = MyApp.getInstance().resources.getString(R.string.lbl_7)
  ,
  /**
   * TODO Replace with dynamic value
   */
  public var txt10: String? = MyApp.getInstance().resources.getString(R.string.lbl_7)
  ,
  /**
   * TODO Replace with dynamic value
   */
  public var txtIfYouDonTRe: String? =
      MyApp.getInstance().resources.getString(R.string.msg_if_you_don_t_re)

)
