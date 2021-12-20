package com.examonline.app.modules.doexamscreen.data.model

import com.examonline.app.R
import com.examonline.app.appcomponents.di.MyApp
import kotlin.String

public data class DoExamScreenRowModel(
    /**
     * TODO Replace with dynamic value
     */
    public var txtNameOfExam: String? =
        MyApp.getInstance().resources.getString(R.string.lbl_math_for_child)
    ,
    /**
     * TODO Replace with dynamic value
     */
    public var txtNumQuestion: String? =
        MyApp.getInstance().resources.getString(R.string.lbl_10_question)
    ,
    /**
     * TODO Replace with dynamic value
     */
    public var txtDuration: String? =
        MyApp.getInstance().resources.getString(R.string.lbl_1_hour_15_min),
    public var txtDateTime: String? =
        MyApp.getInstance().resources.getString(R.string.msg_14_45_23_11_2),
    public var txtSubject: String? =
        MyApp.getInstance().resources.getString(R.string.math),
    public var txtTimeEnd: String? = null,
    public var txtExamID: String? = null,
    public var DoingFlag: String? = null,
    public var Expired: Boolean? = null,
    public var Duration: String? = null,
    public var txtTime: String? = txtDateTime?.split(" ")?.toTypedArray()?.get(0),
    public var txtDate: String? = txtDateTime?.split(" ")?.toTypedArray()?.get(1)

)
