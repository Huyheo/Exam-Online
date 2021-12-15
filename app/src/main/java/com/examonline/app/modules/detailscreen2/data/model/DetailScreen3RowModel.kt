package com.examonline.app.modules.detailscreen2.`data`.model

import com.examonline.app.R
import com.examonline.app.appcomponents.di.MyApp
import kotlin.String

public data class DetailScreen3RowModel(
  /**
   * TODO Replace with dynamic value
   */
  public var txtStudentName: String? = MyApp.getInstance().resources.getString(R.string.lbl_student_1)
  ,
  /**
   * TODO Replace with dynamic value
   */
  public var txtStudentGmail: String? =
      MyApp.getInstance().resources.getString(R.string.msg_student1_gmail)
  ,
  /**
   * TODO Replace with dynamic value
   */
  public var txtStudentPhone: String? =
      MyApp.getInstance().resources.getString(R.string.lbl_0938605304),
  public var avaurl: String? = null

)
