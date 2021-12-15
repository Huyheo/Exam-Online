package com.examonline.app.modules.joinclass.`data`.model

import com.examonline.app.R
import com.examonline.app.appcomponents.di.MyApp
import kotlin.String

public data class JoinClassModel(
  /**
   * TODO Replace with dynamic value
   */
  public var txtJoinClass: String? =
      MyApp.getInstance().resources.getString(R.string.lbl_join_class)
  ,
  /**
   * TODO Replace with dynamic value
   */
  public var txtClassCode: String? =
      MyApp.getInstance().resources.getString(R.string.lbl_class_code)
  ,
  /**
   * TODO Replace with dynamic value
   */
  public var txtCode: String? = MyApp.getInstance().resources.getString(R.string.lbl_code)
  ,
  /**
   * TODO Replace with dynamic value
   */
  public var txtClassDoesNot: String? =
      MyApp.getInstance().resources.getString(R.string.msg_class_does_not)

)
