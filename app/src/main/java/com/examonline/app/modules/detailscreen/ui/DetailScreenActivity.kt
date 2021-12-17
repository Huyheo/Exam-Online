package com.examonline.app.modules.detailscreen.ui

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import com.examonline.app.R
import com.examonline.app.appcomponents.base.BaseActivity
import com.examonline.app.appcomponents.di.MyApp
import com.examonline.app.databinding.ActivityDetailScreenBinding
import com.examonline.app.modules.detailscreen.`data`.viewmodel.DetailScreenVM
import com.examonline.app.modules.examscreen.ui.ExamScreenActivity
import kotlin.String
import kotlin.Unit

public class DetailScreenActivity :
    BaseActivity<ActivityDetailScreenBinding>(R.layout.activity_detail_screen) {
  private val viewModel: DetailScreenVM by viewModels<DetailScreenVM>()

  public override fun setUpClicks(): Unit {
    binding.image.setOnClickListener {
      finish();
      super.onBackPressed();
    }
    binding.btnStartExam.setOnClickListener {
      val destIntent = Intent(this,ExamScreenActivity::class.java)
      destIntent.putExtra("NumQuestion", binding.txtNumQuestion.text.toString().split(" ").toTypedArray()[0])
      destIntent.putExtra("ExamID", intent.getStringExtra("ExamID"))
      destIntent.putExtra("ExamName", intent.getStringExtra("ExamName"))
      startActivity(destIntent)
    }
  }

  public override fun onInitialized(): Unit {
    val numQuestion = intent.getStringExtra("NumQuestion").toString().split(" ").toTypedArray()[0]
    binding.detailScreenVM = viewModel
    viewModel.detailScreenModel.value?.txtNumQuestion = intent.getStringExtra("NumQuestion")
    viewModel.detailScreenModel.value?.txtTotalDuration = intent.getStringExtra("Duration")
    viewModel.detailScreenModel.value?.txtStart = intent.getStringExtra("TimeBegin")
    viewModel.detailScreenModel.value?.txtClose = intent.getStringExtra("TimeEnd")
    viewModel.detailScreenModel.value?.txtMathExam = intent.getStringExtra("ExamName")
    viewModel.detailScreenModel.value?.txt10PointAwarde = 10.div(numQuestion.toFloat()).toString() + " " + MyApp.getInstance().resources.getString(R.string.msg_10_point_awarde)
    viewModel.detailScreenModel.value?.txt10PointForA = 10.div(numQuestion.toFloat()).toString() + " " + MyApp.getInstance().resources.getString(R.string.msg_10_point_for_a)
    if (intent.getStringExtra("DoingFlag").equals("Done")) {
      binding.btnStartExam.text = "Done!"
      binding.btnStartExam.setTextColor(Color.GREEN)
      binding.btnStartExam.isClickable = false
    }
  }

  public companion object {
    public const val TAG: String = "DETAIL_SCREEN_ACTIVITY"

    public fun getIntent(context: Context, bundle: Bundle?): Intent {
      val destIntent = Intent(context, DetailScreenActivity::class.java)
      destIntent.putExtra("bundle", bundle)
      return destIntent
    }
  }
}
