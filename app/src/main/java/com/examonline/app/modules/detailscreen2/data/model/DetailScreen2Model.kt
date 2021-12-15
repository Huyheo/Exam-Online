package com.examonline.app.modules.detailscreen2.`data`.model

import com.examonline.app.R
import com.examonline.app.appcomponents.di.MyApp
import kotlin.String

public data class DetailScreen2Model(
  /**
   * TODO Replace with dynamic value
   */
  public var txtDetailClass: String? =
      MyApp.getInstance().resources.getString(R.string.lbl_detail_class)
  ,
  /**
   * TODO Replace with dynamic value
   */
  public var txtClassName: String? =null
  ,
  /**
   * TODO Replace with dynamic value
   */
  public var txtTeacherName: String? =null
  ,
  public var txtNumStudent: String? =null
  ,
  /**
   * TODO Replace with dynamic value
   */
  public var etSearchValue: String? = null
)
