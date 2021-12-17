package com.examonline.app.modules.examscreen.`data`.model

import com.examonline.app.R
import com.examonline.app.appcomponents.di.MyApp
import kotlin.String

public data class ExamScreenModel(
  /**
   * TODO Replace with dynamic value
   */
  public var txtExamName: String? = null
  ,
  /**
   * TODO Replace with dynamic value
   */
  public var txtCountDown: String? = MyApp.getInstance().resources.getString(R.string.lbl_16_35)


)
