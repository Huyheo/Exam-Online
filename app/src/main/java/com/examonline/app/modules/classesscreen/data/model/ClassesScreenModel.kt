package com.examonline.app.modules.classesscreen.`data`.model

import com.examonline.app.R
import com.examonline.app.appcomponents.di.MyApp
import kotlin.String

public data class ClassesScreenModel(
  /**
   * TODO Replace with dynamic value
   */
  public var txtHelloTeacher: String? =
      MyApp.getInstance().resources.getString(R.string.msg_hello_teacher)
  ,
  /**
   * TODO Replace with dynamic value
   */
  public var txtClasses: String? = MyApp.getInstance().resources.getString(R.string.lbl_classes)

)
