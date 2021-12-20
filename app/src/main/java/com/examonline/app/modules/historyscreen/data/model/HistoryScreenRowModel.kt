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
  public var txtSubject: String? =
      MyApp.getInstance().resources.getString(R.string.math),
  public var txtExamID: String? = null,
  public var txtTimeBegin: String? = null,
  public var txtTimeEnd: String? = null,
  public var txtTimeSubmit: String? = null,
  public var txtTimeStart: String? = null,
  public var txtMark: String? = null,
  public var txtDoingTime: String? = null,
  public var txtTime: String? = txtTimeSubmit?.split(" ")?.toTypedArray()?.get(0),
  public var txtDate: String? = txtTimeSubmit?.split(" ")?.toTypedArray()?.get(1)

)
