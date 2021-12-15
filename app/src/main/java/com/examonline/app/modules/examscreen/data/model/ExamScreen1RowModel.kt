package com.examonline.app.modules.examscreen.`data`.model

import com.examonline.app.R
import com.examonline.app.appcomponents.di.MyApp
import kotlin.String

public data class ExamScreen1RowModel(
  /**
   * TODO Replace with dynamic value
   */
  public var txt4: String? = MyApp.getInstance().resources.getString(R.string.lbl_4)

)
