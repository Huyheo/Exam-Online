package com.examonline.app.modules.startpage.`data`.model

import com.examonline.app.R
import com.examonline.app.appcomponents.di.MyApp
import kotlin.String

public data class StartPageModel(
  /**
   * TODO Replace with dynamic value
   */
  public var txtOnlineExam: String? =
      MyApp.getInstance().resources.getString(R.string.lbl_online_exam)

)
