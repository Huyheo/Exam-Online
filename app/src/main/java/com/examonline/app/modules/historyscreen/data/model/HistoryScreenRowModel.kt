package com.examonline.app.modules.historyscreen.data.model

import com.examonline.app.R
import com.examonline.app.appcomponents.di.MyApp
import kotlin.String

public data class HistoryScreenRowModel(
  /**
   * TODO Replace with dynamic value
   */
  public var txtNameOfExam: String? =
      MyApp.getInstance().resources.getString(R.string.lbl_math_for_child)
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
  public var txtDuration: String? =
      MyApp.getInstance().resources.getString(R.string.lbl_1_hour_15_min)
  ,
  /**
   * TODO Replace with dynamic value
   */
  public var txtDateTime: String? =
      MyApp.getInstance().resources.getString(R.string.msg_14_45_23_11_2),
  public var txtSubject: String? =
      MyApp.getInstance().resources.getString(R.string.math),
  public var txtTime: String? = txtDateTime?.split(" ")?.toTypedArray()?.get(0),
  public var txtDate: String? = txtDateTime?.split(" ")?.toTypedArray()?.get(1)

)
