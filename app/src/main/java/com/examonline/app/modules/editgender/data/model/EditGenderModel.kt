package com.examonline.app.modules.editgender.`data`.model

import com.examonline.app.R
import com.examonline.app.appcomponents.di.MyApp
import kotlin.String

public data class EditGenderModel(
  /**
   * TODO Replace with dynamic value
   */
  public var txtGender: String? = MyApp.getInstance().resources.getString(R.string.lbl_gender)
  ,
  /**
   * TODO Replace with dynamic value
   */
  public var txtGender1: String? = MyApp.getInstance().resources.getString(R.string.lbl_gender)
  ,
  /**
   * TODO Replace with dynamic value
   */
  public var txtMale: String? = MyApp.getInstance().resources.getString(R.string.lbl_male)
  ,
  /**
   * TODO Replace with dynamic value
   */
  public var txtFemale: String? = MyApp.getInstance().resources.getString(R.string.lbl_female)
  ,
  /**
   * TODO Replace with dynamic value
   */
  public var txtOther: String? = MyApp.getInstance().resources.getString(R.string.lbl_other)

)
