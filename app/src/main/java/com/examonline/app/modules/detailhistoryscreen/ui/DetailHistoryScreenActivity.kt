package com.examonline.app.modules.detailhistoryscreen.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import com.examonline.app.R
import com.examonline.app.appcomponents.base.BaseActivity
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
