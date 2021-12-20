package com.examonline.app.modules.detailhistoryscreen.`data`.model

import com.examonline.app.R
import com.examonline.app.appcomponents.di.MyApp
import kotlin.String

public data class DetailHistoryScreenModel(
  /**
   * TODO Replace with dynamic value
   */
  public var txtExamHistory: String? =
      MyApp.getInstance().resources.getString(R.string.lbl_exam_history)
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
  public var txt10Question: String? =
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
  public var txtResultOfExam: String? =
      MyApp.getInstance().resources.getString(R.string.lbl_result_of_exam)
  ,
  /**
   * TODO Replace with dynamic value
   */
  public var etTimeCloseValue: String? = null,

  public var txtTotalDuration: String? = MyApp.getInstance().resources.getString(R.string.lbl_1_hour_15_min),
  public var txtStart: String? = MyApp.getInstance().resources.getString(R.string.msg_14_45_23_11_2),
  public var txtClose:String?=MyApp.getInstance().resources.getString(R.string.msg_16_45_23_11_2),
  public var txtMarkOfTheExa:String?=MyApp.getInstance().resources.getString(R.string.lbl_10_points),
  public var txt10Point:String?=MyApp.getInstance().resources.getString(R.string.msg_mark_of_the_exa),
  public var txtTotalDuration1:String?=MyApp.getInstance().resources.getString(R.string.lbl_1_hour_11_min),
  public var txtFinish:String?=MyApp.getInstance().resources.getString(R.string.msg_16_45_23_11_2),
  public var txtOpen:String?=MyApp.getInstance().resources.getString(R.string.msg_14_45_23_11_2)

)
