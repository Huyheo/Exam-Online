package com.examonline.app.modules.doexamscreen.`data`.model

import com.examonline.app.R
import com.examonline.app.appcomponents.di.MyApp
import kotlin.String

public data class DoExamScreenModel(
  /**
   * TODO Replace with dynamic value
   */
  public var txtHelloTeacher: String? =
      MyApp.getInstance().resources.getString(R.string.msg_hello_teacher)
  ,
  /**
   * TODO Replace with dynamic value
   */
  public var txtDoExam: String? = MyApp.getInstance().resources.getString(R.string.lbl_do_exam)

)
