package com.examonline.app.modules.forgotpasword.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import androidx.activity.viewModels
import com.examonline.app.R
import com.examonline.app.appcomponents.base.BaseActivity
import com.examonline.app.appcomponents.di.MyApp
import com.examonline.app.databinding.ActivityForgotPaswordBinding
import com.examonline.app.extensions.alert
import com.examonline.app.extensions.isEmail
import com.examonline.app.extensions.neutralButton
import com.examonline.app.modules.forgotpasword.`data`.viewmodel.ForgotPaswordVM
import com.examonline.app.modules.login.ui.LoginActivity
import com.examonline.app.modules.otp.ui.OtpActivity
import kotlin.String
import kotlin.Unit

public class ForgotPaswordActivity :
    BaseActivity<ActivityForgotPaswordBinding>(R.layout.activity_forgot_pasword) {
  private val viewModel: ForgotPaswordVM by viewModels<ForgotPaswordVM>()

  public override fun setUpClicks(): Unit {
    binding.image1.setOnClickListener {

      val destIntent = LoginActivity.getIntent(this, null)
      startActivity(destIntent)
      this.finish()

    }
    binding.btnSubmit.setOnClickListener {
      if (validateEmail()) {
        val destIntent = LoginActivity.getIntent(this,null)
        this@ForgotPaswordActivity.alert(
          MyApp.getInstance().getString(R.string.lbl_verify_email),
          MyApp.getInstance().getString(R.string.msg_verification_email1)
        ) {
          neutralButton {
            destIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(destIntent)
            finish()
          }
        }
      }
    }
  }

  public override fun onInitialized(): Unit {
    binding.forgotPaswordVM = viewModel
  }

  public companion object {
    public const val TAG: String = "FORGOT_PASWORD_ACTIVITY"

    public fun getIntent(context: Context, bundle: Bundle?): Intent {
      val destIntent = Intent(context, ForgotPaswordActivity::class.java)
      destIntent.putExtra("bundle", bundle)
      return destIntent
    }
  }

  private fun validateEmail(): Boolean {
    val emailInput: String = binding.editEmail.text.toString().trim()
    return if (emailInput.isEmpty()) {
      binding.editEmail.error = "Field can't be empty"
      false
    } else if (!emailInput.isEmail()) {
      binding.editEmail.error = "Please enter a valid email address"
      false
    } else {
      binding.editEmail.error = null
      true
    }
  }
}
