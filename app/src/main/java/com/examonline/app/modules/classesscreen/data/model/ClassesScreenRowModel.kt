package com.examonline.app.modules.classesscreen.data.model


import com.examonline.app.R
import com.examonline.app.appcomponents.di.MyApp
import kotlin.String

public data class ClassesScreenRowModel(
  /**
   * TODO Replace with dynamic value
   */
  public var txtClassName: String? =
      MyApp.getInstance().resources.getString(R.string.lbl_math_for_child)
  ,
  /**
   * TODO Replace with dynamic value
   */
  public var txtNumStudent: String? =
      MyApp.getInstance().resources.getString(R.string.lbl_10_student)
  ,
  /**
   * TODO Replace with dynamic value
   */
  public var txtTeacherName: String? =
      MyApp.getInstance().resources.getString(R.string.lbl_teacher_name),
  public var classID: String?=null

)
