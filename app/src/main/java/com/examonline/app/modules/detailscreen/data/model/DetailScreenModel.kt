package com.examonline.app.modules.detailscreen.`data`.model

import com.examonline.app.R
import com.examonline.app.appcomponents.di.MyApp
import kotlin.String

public data class DetailScreenModel(
  /**
   * TODO Replace with dynamic value
   */
  public var txtDetailExam: String? =
      MyApp.getInstance().resources.getString(R.string.lbl_detail_exam)
  ,
  /**
   * TODO Replace with dynamic value
   */
  public var txtMathExam: String? = MyApp.getInstance().resources.getString(R.string.lbl_math_exam)
  ,
  /**
   * TODO Replace with dynamic value
   */
  public var txtBriefExplanati: String? =
      MyApp.getInstance().resources.getString(R.string.msg_brief_explanati)
  ,
  /**
   * TODO Replace with dynamic value
   */
  public var txtNumQuestion: String? =
      MyApp.getInstance().resources.getString(R.string.lbl_10_question)
  ,
  /**
   * TODO Replace with dynamic value
   */
  public var txt10PointForA: String? =
      MyApp.getInstance().resources.getString(R.string.msg_10_point_for_a)
  ,
  /**
   * TODO Replace with dynamic value
   */
  public var txtPleaseReadThe: String? =
      MyApp.getInstance().resources.getString(R.string.msg_please_read_the)
  ,
  /**
   * TODO Replace with dynamic value
   */
  public var txt10PointAwarde: String? =
      MyApp.getInstance().resources.getString(R.string.msg_10_point_awarde)
  ,
  /**
   * TODO Replace with dynamic value
   */
  public var txtTapOnOptions: String? =
      MyApp.getInstance().resources.getString(R.string.msg_tap_on_options)
  ,
  /**
   * TODO Replace with dynamic value
   */
  public var txtClickSubmitIf: String? =
      MyApp.getInstance().resources.getString(R.string.msg_click_submit_if)

,
  public var txtTotalDuration:String?=MyApp.getInstance().resources.getString(R.string.lbl_1_hour_15_min),
  public var txtStart:String?=MyApp.getInstance().resources.getString(R.string.msg_14_45_23_11_2),
  public var txtClose:String?=MyApp.getInstance().resources.getString(R.string.msg_16_45_23_11_2)



)
