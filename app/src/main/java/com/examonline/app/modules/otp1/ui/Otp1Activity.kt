package com.examonline.app.modules.otp1.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import com.examonline.app.R
import com.examonline.app.appcomponents.base.BaseActivity
import com.examonline.app.databinding.ActivityOtp1Binding
import com.examonline.app.modules.otp1.`data`.viewmodel.Otp1VM
import kotlin.String
import kotlin.Unit

public class Otp1Activity : BaseActivity<ActivityOtp1Binding>(R.layout.activity_otp_1) {
  private val viewModel: Otp1VM by viewModels<Otp1VM>()

  public override fun setUpClicks(): Unit {
    binding.image.setOnClickListener {
      finish()
      super.onBackPressed()
    }
  }

  public override fun onInitialized(): Unit {
    binding.otp1VM = viewModel
  }

  public companion object {
    public const val TAG: String = "OTP1ACTIVITY"

    public fun getIntent(context: Context, bundle: Bundle?): Intent {
      val destIntent = Intent(context, Otp1Activity::class.java)
      destIntent.putExtra("bundle", bundle)
      return destIntent
    }
  }
}
