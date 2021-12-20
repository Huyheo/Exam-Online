package com.examonline.app.modules.detailhistoryscreen.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import com.examonline.app.R
import com.examonline.app.appcomponents.base.BaseActivity
import com.examonline.app.appcomponents.di.MyApp
import com.examonline.app.databinding.ActivityDetailHistoryScreenBinding
import com.examonline.app.modules.detailhistoryscreen.`data`.viewmodel.DetailHistoryScreenVM
import kotlin.String
import kotlin.Unit

public class DetailHistoryScreenActivity :
    BaseActivity<ActivityDetailHistoryScreenBinding>(R.layout.activity_detail_history_screen) {
  private val viewModel: DetailHistoryScreenVM by viewModels<DetailHistoryScreenVM>()

  public override fun setUpClicks(): Unit {
    binding.image.setOnClickListener {
      finish();
      super.onBackPressed();
    }
  }

  public override fun onInitialized(): Unit {
    binding.detailHistoryScreenVM = viewModel
    val numQuestion = intent.getStringExtra("NumQuestion").toString().split(" ").toTypedArray()[0]
    viewModel.detailHistoryScreenModel.value?.txt10Question = intent.getStringExtra("NumQuestion")
    val d = intent.getStringExtra("DoingTime")!!.toString().toInt()
    val duration = if (d  > 60){
      d.div(60).toString() + " Hour " +
              d.rem(60).toString() + " Minute"
    }
    else {
      "$d Minute"
    }
    viewModel.detailHistoryScreenModel.value?.txtTotalDuration = intent.getStringExtra("Duration").toString()
    viewModel.detailHistoryScreenModel.value?.txtOpen = intent.getStringExtra("TimeBegin").toString()
    viewModel.detailHistoryScreenModel.value?.txtClose = intent.getStringExtra("TimeEnd").toString()
    viewModel.detailHistoryScreenModel.value?.txtMathExam = intent.getStringExtra("ExamName").toString()
    viewModel.detailHistoryScreenModel.value?.txtMarkOfTheExa = intent.getStringExtra("Mark").toString()
    viewModel.detailHistoryScreenModel.value?.txtTotalDuration1 = duration
    viewModel.detailHistoryScreenModel.value?.txtFinish = intent.getStringExtra("TimeSubmit").toString()
    viewModel.detailHistoryScreenModel.value?.txtStart = intent.getStringExtra("TimeStart").toString()
    viewModel.detailHistoryScreenModel.value?.txt10PointForA = "%.1f".format(10.div(numQuestion.toFloat())) + " " + MyApp.getInstance().resources.getString(R.string.msg_10_point_for_a)
    if (intent.getStringExtra("Title")!=null){
      viewModel.detailHistoryScreenModel.value?.txtExamHistory = intent.getStringExtra("Title").toString()
    }
  }

  public companion object {
    public const val TAG: String = "DETAIL_HISTORY_SCREEN_ACTIVITY"

    public fun getIntent(context: Context, bundle: Bundle?): Intent {
      val destIntent = Intent(context, DetailHistoryScreenActivity::class.java)
      destIntent.putExtra("bundle", bundle)
      return destIntent
    }
  }
}
