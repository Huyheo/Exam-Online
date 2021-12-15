package com.examonline.app.modules.reset.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import com.examonline.app.R
import com.examonline.app.appcomponents.base.BaseActivity
import com.examonline.app.databinding.ActivityResetBinding
import com.examonline.app.modules.forgotpasword.ui.ForgotPaswordActivity
import com.examonline.app.modules.login.ui.LoginActivity
import com.examonline.app.modules.reset.`data`.viewmodel.ResetVM
import kotlin.String
import kotlin.Unit

public class ResetActivity : BaseActivity<ActivityResetBinding>(R.layout.activity_reset) {
  private val viewModel: ResetVM by viewModels<ResetVM>()

  public override fun setUpClicks(): Unit {
    binding.image1.setOnClickListener {
      this.finish()
      super.onBackPressed()
    }
    binding.btnSubmit.setOnClickListener {
      val destIntent = Intent(this, LoginActivity::class.java)
      startActivity(destIntent)
      finish()
    }
  }

  public override fun onInitialized(): Unit {
    binding.resetVM = viewModel
  }

  public companion object {
    public const val TAG: String = "RESET_ACTIVITY"

    public fun getIntent(context: Context, bundle: Bundle?): Intent {
      val destIntent = Intent(context, ResetActivity::class.java)
      destIntent.putExtra("bundle", bundle)
      return destIntent
    }
  }
}
