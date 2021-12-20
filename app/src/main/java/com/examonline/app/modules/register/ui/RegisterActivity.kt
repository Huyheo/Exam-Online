package com.examonline.app.modules.register.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import com.examonline.app.R
import com.examonline.app.appcomponents.base.BaseActivity
import com.examonline.app.appcomponents.di.MyApp
import com.examonline.app.databinding.ActivityRegisterBinding
import com.examonline.app.extensions.*
import com.examonline.app.modules.login.ui.LoginActivity
import com.examonline.app.modules.register.`data`.viewmodel.RegisterVM
import com.examonline.app.network.models.createsignup.CreateSignupResponse
import com.examonline.app.network.models.resources.ErrorResponse
import com.examonline.app.network.models.resources.SuccessResponse
import com.google.android.material.snackbar.Snackbar
import org.json.JSONObject
import retrofit2.HttpException
import java.util.regex.Pattern
import kotlin.String
import kotlin.Unit

public class RegisterActivity : BaseActivity<ActivityRegisterBinding>(R.layout.activity_register) {
  private val viewModel: RegisterVM by viewModels<RegisterVM>()

  public override fun setUpClicks(): Unit {
    binding.txtAlreadyMember.setOnClickListener {
      val destIntent = LoginActivity.getIntent(this, null)
      startActivity(destIntent)
//      this.finish()

    }
    binding.btnRegister.setOnClickListener {
      if (validateUsername() && validatePassword() && validateEmail() && validateConfPassword()) {
        viewModel.onClickBtnSignUp()
//        val destIntent = LoginActivity.getIntent(this, null)
//        startActivity(destIntent)
      }
    }
  }

  public override fun onInitialized(): Unit {
    binding.registerVM = viewModel
    binding.image1.visibility= View.GONE
  }

  public override fun addObservers(): Unit {
    var progressDialog: AlertDialog? = null
    viewModel.progressLiveData.observe(this@RegisterActivity) {
      if (it) {
        progressDialog?.dismiss()
        progressDialog = null
        progressDialog = this@RegisterActivity.showProgressDialog()
      } else {
        progressDialog?.dismiss()
      }
    }
    viewModel.createLoginLiveData.observe(this@RegisterActivity) {
      if (it is SuccessResponse) {
        val response = it.getContentIfNotHandled()
        onSuccessCreateLogin(it)
      } else if (it is ErrorResponse) {
        onErrorCreateLogin(it.data ?: Exception())
      }
    }
  }
  private fun onSuccessCreateLogin(response: SuccessResponse<CreateSignupResponse>): Unit {
    if (response.data.status?.Code=="200"){
      viewModel.bindCreateSignupResponse(response.data)
      val destIntent = LoginActivity.getIntent(this,null)
      this@RegisterActivity.alert(
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
    else {
      this@RegisterActivity.alert(
      MyApp.getInstance().getString(R.string.lbl_register_error),
      response.data.message.toString()
      ) {
        neutralButton {
        }
      }
      binding.editUsername.requestFocus()
    }
  }
  private fun onErrorCreateLogin(exception: Exception): Unit {
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
        this@RegisterActivity.alert(
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
    public const val TAG: String = "REGISTER_ACTIVITY"

    public fun getIntent(context: Context, bundle: Bundle?): Intent {
      val destIntent = Intent(context, RegisterActivity::class.java)
      destIntent.putExtra("bundle", bundle)
      return destIntent
    }
  }

  private fun validateEmail(): Boolean {
    val emailInput: String = binding.editEmailAddress.text.toString().trim()
    return if (emailInput.isEmpty()) {
      binding.editEmailAddress.error = "Field can't be empty"
      false
    } else if (!emailInput.isEmail()) {
      binding.editEmailAddress.error = "Please enter a valid email address"
      false
    } else {
      binding.editEmailAddress.error = null
      true
    }
  }

  private fun validatePassword(): Boolean {
    val passwordInput: String = binding.editPassword.text.toString().trim()
    return if (passwordInput.isEmpty()) {
      binding.editPassword.error = "Field can't be empty"
      false
    }
    else if (!passwordInput.isPassword()) {
      binding.editPassword.error = "Password at least 6 characters and no white spaces"
      false
    }
    else {
      binding.editPassword.error = null
      true
    }
  }

  private fun validateConfPassword(): Boolean {
    val passwordInput: String = binding.editConfirmPassword.text.toString().trim()
    return if (passwordInput.isEmpty()) {
      binding.editConfirmPassword.error = "Field can't be empty"
      false
    }
    else if (!passwordInput.isPassword()) {
      binding.editConfirmPassword.error = "Password at least 6 characters and no white spaces"
      false
    }
    else if (passwordInput != binding.editPassword.text.toString().trim()){
      binding.editConfirmPassword.error = "Confirmation password must be same as above password"
      false
    }
    else {
      binding.editConfirmPassword.error = null
      true
    }
  }

  private fun validateUsername(): Boolean {
    val usernameInput: String = binding.editUsername.text.toString().trim()
    return if (usernameInput.isEmpty()) {
      binding.editUsername.error = "Field can't be empty"
      false
    }
    else {
      binding.editUsername.error = null
      true
    }
  }
}
