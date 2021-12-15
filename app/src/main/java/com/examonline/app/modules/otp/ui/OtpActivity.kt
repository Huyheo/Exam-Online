package com.examonline.app.modules.otp.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import com.examonline.app.R
import com.examonline.app.appcomponents.base.BaseActivity
import com.examonline.app.databinding.ActivityOtpBinding
import com.examonline.app.modules.forgotpasword.ui.ForgotPaswordActivity
import com.examonline.app.modules.otp.`data`.viewmodel.OtpVM
import com.examonline.app.modules.reset.ui.ResetActivity
import kotlin.String
import kotlin.Unit

public class OtpActivity : BaseActivity<ActivityOtpBinding>(R.layout.activity_otp) {
  private val viewModel: OtpVM by viewModels<OtpVM>()

  public override fun setUpClicks(): Unit {
    binding.image1.setOnClickListener {

      val destIntent = ForgotPaswordActivity.getIntent(this, null)
      startActivity(destIntent)
      this.finish()
    }

    binding.image.setOnClickListener {

      val destIntent = ResetActivity.getIntent(this, null)
      startActivity(destIntent)
      finish()
    }
  }

  public override fun onInitialized(): Unit {
    binding.otpVM = viewModel
  }

  public companion object {
    public const val TAG: String = "OTP_ACTIVITY"

    public fun getIntent(context: Context, bundle: Bundle?): Intent {
      val destIntent = Intent(context, OtpActivity::class.java)
      destIntent.putExtra("bundle", bundle)
      return destIntent
    }
  }
}
