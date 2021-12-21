package com.examonline.app.modules.editpassword.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import com.examonline.app.appcomponents.base.BaseActivity
import com.examonline.app.appcomponents.di.MyApp
import com.examonline.app.databinding.ActivityEditPasswordBinding
import com.examonline.app.extensions.*
import com.examonline.app.modules.editpassword.`data`.viewmodel.EditPasswordVM
import com.examonline.app.network.models.resources.ErrorResponse
import com.examonline.app.network.models.resources.SuccessResponse
import com.examonline.app.network.models.updatepassword.UpdatePasswordResponse
import com.google.android.material.snackbar.Snackbar
import org.json.JSONObject
import retrofit2.HttpException
import java.util.regex.Pattern
import kotlin.String
import kotlin.Unit
import androidx.fragment.app.FragmentManager
import com.examonline.app.R
import com.examonline.app.modules.profilescreen.ui.ProfileScreenFragment


public class EditPasswordActivity :
    BaseActivity<ActivityEditPasswordBinding>(R.layout.activity_edit_password) {
  private val viewModel: EditPasswordVM by viewModels<EditPasswordVM>()

  public override fun setUpClicks(): Unit {
    binding.image.setOnClickListener {
      finish();
      super.onBackPressed();
    }
    binding.btnSave.setOnClickListener {
      if (validatePassword())
        viewModel.onClickBtnSave(binding.editCurrentPass.text.toString(),
          binding.editNewPass.text.toString())
    }
  }

  public override fun onInitialized(): Unit {
    binding.editPasswordVM = viewModel
  }

  public override fun addObservers(): Unit {
    var progressDialog: AlertDialog? = null
    viewModel.progressLiveData.observe(this@EditPasswordActivity) {
      if (it) {
        progressDialog?.dismiss()
        progressDialog = null
        progressDialog = this@EditPasswordActivity.showProgressDialog()
      } else {
        progressDialog?.dismiss()
      }
    }
    viewModel.editPasswordLiveData.observe(this@EditPasswordActivity) {
      if (it is SuccessResponse) {
        val response = it.getContentIfNotHandled()
        onSuccessEditPassword(it)
      } else if (it is ErrorResponse) {
        onErrorEditPassword(it.data ?: Exception())
      }
    }
  }
  private fun onSuccessEditPassword(response: SuccessResponse<UpdatePasswordResponse>): Unit {
    if (response.data.status?.Code=="200"){
      this@EditPasswordActivity.hideKeyboard()
      super.onBackPressed()
    }
    else this@EditPasswordActivity.alert(
      MyApp.getInstance().getString(R.string.lbl_error),
      response.data.message.toString()
    ) {
      neutralButton {
      }
    }
  }
  private fun onErrorEditPassword(exception: Exception): Unit {
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
        this@EditPasswordActivity.alert(
          MyApp.getInstance().getString(R.string.lbl_login_error),
          errMessage
        ) {
          neutralButton {
          }
        }
      }
    }
  }

  private fun validatePassword(): Boolean {
    val curPass: String = binding.editCurrentPass.text.toString().trim()
    val newPass: String = binding.editNewPass.text.toString().trim()
    val conPass: String = binding.editConfirmPass.text.toString().trim()
    return if (curPass.isEmpty()) {
      binding.editCurrentPass.error = "Field can't be empty"
      false
    }
    else if (newPass.isEmpty()) {
      binding.editNewPass.error = "Field can't be empty"
      false
    }
    else if (conPass.isEmpty()) {
      binding.editConfirmPass.error = "Field can't be empty"
      false
    }
    else if (!curPass.isPassword()) {
      binding.editCurrentPass.error = "Password at least 6 characters and no white spaces"
      false
    }
    else if (!newPass.isPassword()) {
      binding.editNewPass.error = "Password at least 6 characters and no white spaces"
      false
    }
    else if (!conPass.isPassword()) {
      binding.editConfirmPass.error = "Password at least 6 characters and no white spaces"
      false
    }
    else if (newPass==curPass) {
      binding.editNewPass.error = "The new password must be different from the old password"
      false
    }
    else if (conPass==curPass) {
      binding.editConfirmPass.error = "The new password must be different from the old password"
      false
    }
    else if (newPass!=conPass) {
      binding.editConfirmPass.error = "The confirm password must be the same as the new password"
      false
    }
    else {
      binding.editCurrentPass.error = null
      binding.editNewPass.error = null
      binding.editConfirmPass.error = null
      true
    }
  }

  public companion object {
    public const val TAG: String = "EDIT_PASSWORD_ACTIVITY"

    public fun getIntent(context: Context, bundle: Bundle?): Intent {
      val destIntent = Intent(context, EditPasswordActivity::class.java)
      destIntent.putExtra("bundle", bundle)
      return destIntent
    }
  }
}
