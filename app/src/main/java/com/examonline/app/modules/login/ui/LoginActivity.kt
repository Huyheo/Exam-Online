package com.examonline.app.modules.login.ui

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import com.examonline.app.R
import com.examonline.app.appcomponents.base.BaseActivity
import com.examonline.app.modules.forgotpasword.ui.ForgotPaswordActivity
import com.examonline.app.modules.login.`data`.viewmodel.LoginVM
import com.examonline.app.modules.register.ui.RegisterActivity
import kotlin.String
import kotlin.Unit

import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import com.examonline.app.MainActivity
import com.examonline.app.appcomponents.di.MyApp
import com.examonline.app.databinding.ActivityLoginBinding
import com.examonline.app.extensions.*
import com.examonline.app.network.models.createlogin.CreateLoginResponse
import com.examonline.app.network.models.resources.ErrorResponse
import com.examonline.app.network.models.resources.SuccessResponse
import com.google.android.material.snackbar.Snackbar
import org.json.JSONObject
import retrofit2.HttpException
import java.util.regex.Pattern
import android.util.Patterns
import android.view.View
import com.examonline.app.appcomponents.utility.PreferenceHelper
import org.koin.core.KoinComponent
import org.koin.core.inject

public class LoginActivity : BaseActivity<ActivityLoginBinding>(R.layout.activity_login), KoinComponent {
  private val viewModel: LoginVM by viewModels<LoginVM>()
  private val prefs: PreferenceHelper by inject()
  public override fun setUpClicks(): Unit {
    binding.btnLogin.setOnClickListener {
      if (validateUsername() && validatePassword()) {
        viewModel.onClickBtnSignIn()
      }
    }
    binding.txtNewHereRegis.setOnClickListener {
      val destIntent = Intent(this, RegisterActivity::class.java)
      startActivity(destIntent)
    }
    binding.txtForgotPassword.setOnClickListener {
      val destIntent = Intent(this, ForgotPaswordActivity::class.java)
      startActivity(destIntent)
    }
  }
  public override fun onInitialized(): Unit {
    binding.loginVM = viewModel
    binding.image1.visibility= View.GONE
  }
  public override fun addObservers(): Unit {
    var progressDialog: AlertDialog? = null
    viewModel.progressLiveData.observe(this@LoginActivity) {
      if (it) {
        progressDialog?.dismiss()
        progressDialog = null
        progressDialog = this@LoginActivity.showProgressDialog()
      } else {
        progressDialog?.dismiss()
      }
    }
    viewModel.createLoginLiveData.observe(this@LoginActivity) {
      if (it is SuccessResponse) {
        val response = it.getContentIfNotHandled()
        onSuccessCreateLogin(it)
      } else if (it is ErrorResponse) {
        onErrorCreateLogin(it.data ?: Exception())
      }
    }
  }
  private fun onSuccessCreateLogin(response: SuccessResponse<CreateLoginResponse>): Unit {
    if (response.data.accessToken!=null){
      if (response.data.data?.RoleID=="3") {
        if (response.data.data.Authentication == "true") {
          viewModel.bindCreateLoginResponse(response.data)
          val destIntent = Intent(this, MainActivity::class.java)
          destIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
          startActivity(destIntent)
          val welcome = getString(R.string.welcome)
          val displayName = response.data.data.Username
          Toast.makeText(
            applicationContext,
            "$welcome $displayName",
            Toast.LENGTH_LONG
          ).show()
          finish()
        } else this@LoginActivity.alert(
          MyApp.getInstance().getString(R.string.lbl_unverified_email),
          MyApp.getInstance().getString(R.string.mgm_pls_verify_email)
        ) {
          neutralButton {
          }
        }
      }
      else this@LoginActivity.alert(
        MyApp.getInstance().getString(R.string.lbl_login_error),
        MyApp.getInstance().getString(R.string.mgm_you_are_not_student)
      ) {
        neutralButton {
        }
      }
    }
    else this@LoginActivity.alert(
      MyApp.getInstance().getString(R.string.lbl_login_error),
      response.data.message.toString()
      ) {
        neutralButton {
        }
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
        this@LoginActivity.alert(
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
    public const val TAG: String = "LOGIN_ACTIVITY"
    public fun getIntent(context: Context, bundle: Bundle?): Intent {
      val destIntent = Intent(context, LoginActivity::class.java)
      destIntent.putExtra("bundle", bundle)
      return destIntent
    }
  }

  private fun isValidPasswordFormat(password: String): Boolean {
    val passwordREGEX = Pattern.compile("^" +
//            "(?=.*[0-9])" +         //at least 1 digit
//            "(?=.*[a-z])" +         //at least 1 lower case letter
//            "(?=.*[A-Z])" +         //at least 1 upper case letter
//            "(?=.*[a-zA-Z])" +      //any letter
//            "(?=.*[@#$%^&+=])" +    //at least 1 special character
            "(?=\\S+$)" +           //no white spaces
            ".{6,}" +               //at least 6 characters
            "$");
    return passwordREGEX.matcher(password).matches()
  }

  private fun validatePassword(): Boolean {
    val passwordInput: String = binding.etPassword.text.toString().trim()
    return if (passwordInput.isEmpty()) {
      binding.etPassword.error = "Field can't be empty"
      false
    }
    else if (!isValidPasswordFormat(passwordInput)) {
      binding.etPassword.error = "Password at least 6 characters and no white spaces"
      false
    }
    else {
      binding.etPassword.error = null
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
