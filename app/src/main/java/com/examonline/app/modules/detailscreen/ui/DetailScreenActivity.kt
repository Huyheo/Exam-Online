package com.examonline.app.modules.detailscreen.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import com.examonline.app.R
import com.examonline.app.appcomponents.base.BaseActivity
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
      startActivity(destIntent)

    }
  }

  public override fun onInitialized(): Unit {
    binding.detailScreenVM = viewModel
    viewModel.detailScreenModel.value?.txtNumQuestion=intent.getStringExtra("NumQuestion")
    viewModel.detailScreenModel.value?.txtTotalDuration=intent.getStringExtra("Duration")
    viewModel.detailScreenModel.value?.txtStart=intent.getStringExtra("TimeBegin")
    viewModel.detailScreenModel.value?.txtClose=intent.getStringExtra("TimeEnd")
    viewModel.detailScreenModel.value?.txtMathExam=intent.getStringExtra("ExamName")
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
