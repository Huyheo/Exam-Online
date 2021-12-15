package com.examonline.app.modules.homescreen.`data`.model

import com.examonline.app.R
import com.examonline.app.appcomponents.di.MyApp
import kotlin.String

public data class HomeScreenModel(
  /**
   * TODO Replace with dynamic value
   */
  public var txtHelloTeacher: String? =
      MyApp.getInstance().resources.getString(R.string.msg_hello_teacher)
  ,
  /**
   * TODO Replace with dynamic value
   */
  public var txtUpcomingExam: String? =
      MyApp.getInstance().resources.getString(R.string.lbl_upcoming_exam)

)
