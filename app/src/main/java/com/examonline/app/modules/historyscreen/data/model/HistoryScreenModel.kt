package com.examonline.app.modules.historyscreen.`data`.model

import com.examonline.app.R
import com.examonline.app.appcomponents.di.MyApp
import kotlin.String

public data class HistoryScreenModel(
  /**
   * TODO Replace with dynamic value
   */
  public var txtHelloTeacher: String? =
      MyApp.getInstance().resources.getString(R.string.msg_hello_teacher)
  ,
  /**
   * TODO Replace with dynamic value
   */
  public var txtExamTakingHis: String? =
      MyApp.getInstance().resources.getString(R.string.msg_exam_taking_his)

)
