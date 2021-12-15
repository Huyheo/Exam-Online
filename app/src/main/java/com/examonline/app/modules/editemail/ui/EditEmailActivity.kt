package com.examonline.app.modules.editemail.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import android.view.View
import android.widget.EditText
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.FragmentManager
import com.examonline.app.R
import com.examonline.app.appcomponents.base.BaseActivity
import com.examonline.app.appcomponents.di.MyApp
import com.examonline.app.appcomponents.utility.PreferenceHelper
import com.examonline.app.databinding.ActivityEditEmailBinding
import com.examonline.app.extensions.*
import com.examonline.app.modules.editemail.`data`.viewmodel.EditEmailVM
import com.examonline.app.modules.otp1.ui.Otp1Activity
import com.examonline.app.modules.profilescreen.ui.ProfileScreenFragment
import com.examonline.app.network.models.resources.ErrorResponse
import com.examonline.app.network.models.resources.SuccessResponse
import com.examonline.app.network.models.updateprofile.UpdateProfileRequest
import com.examonline.app.network.models.updateprofile.UpdateProfileResponse
import com.google.android.material.snackbar.Snackbar
import org.json.JSONObject
import org.koin.core.KoinComponent
import org.koin.core.inject
import retrofit2.HttpException
import kotlin.String
import kotlin.Unit

public class EditEmailActivity :
    BaseActivity<ActivityEditEmailBinding>(R.layout.activity_edit_email), KoinComponent {
  private val viewModel: EditEmailVM by viewModels<EditEmailVM>()
  private val prefs: PreferenceHelper by inject()


  public override fun setUpClicks(): Unit {
    binding.image.setOnClickListener {
      finish();
      super.onBackPressed();
    }
    binding.editEmail.afterTextChanged {
      if (binding.editEmail.text.toString() != intent.getStringExtra("Email").toString())
        binding.btnSave.visibility= View.VISIBLE
      else
        binding.btnSave.visibility= View.GONE
    }
    binding.btnSave.setOnClickListener {
      if (binding.editEmail.text.isNotEmpty() && validateEmail()){
        val updateProfileRequest = UpdateProfileRequest(
          Firstname = intent.getStringExtra("Firstname").toString(),
          Lastname = intent.getStringExtra("Lastname").toString(),
          Gender = intent.getStringExtra("Gender").toBoolean(),
          Email = binding.editEmail.text.toString(),
          DateOfBirth = intent.getStringExtra("DateOfBirth").toString()
            .split("/").toTypedArray().reversedArray().joinToString("-"),
          Phone = intent.getStringExtra("Phone").toString(),
          Address = intent.getStringExtra("Address").toString(),
          Avatar = prefs.getAvatar().toString()
        )
        viewModel.onClickBtnSave(updateProfileRequest)
      }
    }
  }

  public override fun onInitialized(): Unit {
    binding.editEmailVM = viewModel
    if(intent.getStringExtra("Email").toString()!="null")
      viewModel.editEmailModel.value?.editEmail = intent.getStringExtra("Email").toString()
  }

  public override fun addObservers(): Unit {
    var progressDialog: AlertDialog? = null
    viewModel.progressLiveData.observe(this@EditEmailActivity) {
      if (it) {
        progressDialog?.dismiss()
        progressDialog = null
        progressDialog = this@EditEmailActivity.showProgressDialog()
      } else {
        progressDialog?.dismiss()
      }
    }
    viewModel.editEmailLiveData.observe(this@EditEmailActivity) {
      if (it is SuccessResponse) {
        val response = it.getContentIfNotHandled()
        onSuccessCreateLogin(it)
      } else if (it is ErrorResponse) {
        onErrorCreateLogin(it.data ?: Exception())
      }
    }
  }
  private fun onSuccessCreateLogin(response: SuccessResponse<UpdateProfileResponse>): Unit {
    if (response.data.status?.Code=="200"){
      this@EditEmailActivity.hideKeyboard()
      finish()
      super.onBackPressed()
    }
    else this@EditEmailActivity.alert(
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
        this@EditEmailActivity.alert(
          MyApp.getInstance().getString(R.string.lbl_login_error),
          errMessage
        ) {
          neutralButton {
          }
        }
      }
    }
  }

  private fun validateEmail(): Boolean {
    val emailInput: String = binding.editEmail.text.toString().trim()
    return if (emailInput.isEmpty()) {
      binding.editEmail.error = "Field can't be empty"
      false
    } else if (!Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()) {
      binding.editEmail.error = "Please enter a valid email address"
      false
    } else {
      binding.editEmail.error = null
      true
    }
  }

  public companion object {
    public const val TAG: String = "EDIT_EMAIL_ACTIVITY"

    public fun getIntent(context: Context, bundle: Bundle?): Intent {
      val destIntent = Intent(context, EditEmailActivity::class.java)
      destIntent.putExtra("bundle", bundle)
      return destIntent
    }
  }

  fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
      override fun afterTextChanged(editable: Editable?) {
        afterTextChanged.invoke(editable.toString())
      }

      override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

      override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
    })
  }
}
