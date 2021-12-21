package com.examonline.app.modules.forgotpasword.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import com.examonline.app.MainActivity
import com.examonline.app.R
import com.examonline.app.appcomponents.base.BaseActivity
import com.examonline.app.appcomponents.di.MyApp
import com.examonline.app.databinding.ActivityForgotPaswordBinding
import com.examonline.app.extensions.*
import com.examonline.app.modules.forgotpasword.`data`.viewmodel.ForgotPaswordVM
import com.examonline.app.modules.login.ui.LoginActivity
import com.examonline.app.modules.otp.ui.OtpActivity
import com.examonline.app.network.models.createlogin.CreateLoginResponse
import com.examonline.app.network.models.resetpassword.ResetPasswordResponse
import com.examonline.app.network.models.resources.ErrorResponse
import com.examonline.app.network.models.resources.SuccessResponse
import com.google.android.material.snackbar.Snackbar
import org.json.JSONObject
import retrofit2.HttpException
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
      if (validateEmail() && validateUsername()) {
        this@ForgotPaswordActivity.alert(
          MyApp.getInstance().getString(R.string.lbl_reset_password),
          MyApp.getInstance().getString(R.string.msg_new_password)
        ) {
          neutralButton {
            viewModel.onClickBtnSubmit()
          }
        }
      }
    }
  }

  public override fun onInitialized(): Unit {
    binding.forgotPaswordVM = viewModel
  }

  public override fun addObservers(): Unit {
    var progressDialog: AlertDialog? = null
    viewModel.progressLiveData.observe(this@ForgotPaswordActivity) {
      if (it) {
        progressDialog?.dismiss()
        progressDialog = null
        progressDialog = this@ForgotPaswordActivity.showProgressDialog()
      } else {
        progressDialog?.dismiss()
      }
    }
    viewModel.resetPasswordLiveData.observe(this@ForgotPaswordActivity) {
      if (it is SuccessResponse) {
        val response = it.getContentIfNotHandled()
        onSuccessResetPassword(it)
      } else if (it is ErrorResponse) {
        onErrorResetPassword(it.data ?: Exception())
      }
    }
  }
  private fun onSuccessResetPassword(response: SuccessResponse<ResetPasswordResponse>): Unit {
    if (response.data.status!!.Code=="200") {
      val destIntent = LoginActivity.getIntent(this, null)
      startActivity(destIntent)
      finish()
    }
    else this@ForgotPaswordActivity.alert(
      MyApp.getInstance().getString(R.string.lbl_reset_error),
      response.data.message.toString()
      ) {
        neutralButton {
        }
    }
  }
  private fun onErrorResetPassword(exception: Exception): Unit {
    when (exception) {
      is NoInternetConnection -> {
        Snackbar.make(binding.root, exception.message ?: "", Snackbar.LENGTH_LONG).show()
      }
      is HttpException -> {
        val errorBody = exception.response()?.errorBody()?.string()
        val errorObject = if (errorBody != null && errorBody.isJSONObject())
          JSONObject(errorBody)
        else JSONObject()
        val errMessage = if (!errorObject.optString("message").isNullOrEmpty()) {
          errorObject.optString("message").toString()
        } else {
          exception.response()?.message() ?: ""
        }
        this@ForgotPaswordActivity.alert(
          MyApp.getInstance().getString(R.string.lbl_login_error),
          errMessage
        ) {
          neutralButton {
          }
        }
      }
    }
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

  private fun validateUsername(): Boolean {
    val usernameInput: String = binding.etUsername.text.toString().trim()
    return if (usernameInput.isEmpty()) {
      binding.etUsername.error = "Field can't be empty"
      false
    }
    else {
      binding.etUsername.error = null
      true
    }
  }
}
