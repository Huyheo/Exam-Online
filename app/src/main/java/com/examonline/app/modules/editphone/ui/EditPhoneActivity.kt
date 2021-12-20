package com.examonline.app.modules.editphone.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.FragmentManager
import com.examonline.app.R
import com.examonline.app.appcomponents.base.BaseActivity
import com.examonline.app.appcomponents.di.MyApp
import com.examonline.app.appcomponents.utility.PreferenceHelper
import com.examonline.app.databinding.ActivityEditPhoneBinding
import com.examonline.app.extensions.*
import com.examonline.app.modules.editphone.`data`.viewmodel.EditPhoneVM
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

public class EditPhoneActivity :
    BaseActivity<ActivityEditPhoneBinding>(R.layout.activity_edit_phone), KoinComponent {
  private val viewModel: EditPhoneVM by viewModels<EditPhoneVM>()
  private val prefs: PreferenceHelper by inject()


  public override fun setUpClicks(): Unit {
    binding.image.setOnClickListener {
      finish()
      super.onBackPressed()
    }
    binding.editPhoneNumber.afterTextChanged {
      if (binding.editPhoneNumber.text.toString() != intent.getStringExtra("Phone").toString())
        binding.btnSave.visibility=View.VISIBLE
      else
        binding.btnSave.visibility= View.GONE
    }

    binding.btnSave.setOnClickListener {
      if (binding.editPhoneNumber.text.isNotEmpty()){
        val updateProfileRequest = UpdateProfileRequest(
          Firstname = intent.getStringExtra("Firstname").toString(),
          Lastname = intent.getStringExtra("Lastname").toString(),
          Gender = intent.getStringExtra("Gender").toBoolean(),
          Email = intent.getStringExtra("Email").toString(),
          DateOfBirth = intent.getStringExtra("DateOfBirth").toString()
            .split("/").toTypedArray().reversedArray().joinToString("-"),
          Phone = binding.editPhoneNumber.text.toString(),
          Address = intent.getStringExtra("Address").toString(),
          Avatar = prefs.getAvatar().toString()
        )
        viewModel.onClickBtnSave(updateProfileRequest)
      }
      else
        binding.editPhoneNumber.error = "Field can't be empty"
    }
  }

  public override fun onInitialized(): Unit {
    binding.editPhoneVM = viewModel
    if(intent.getStringExtra("Phone").toString()!="null")
      viewModel.editPhoneModel.value?.editPhoneNumber = intent.getStringExtra("Phone").toString()
  }

  public override fun addObservers(): Unit {
    var progressDialog: AlertDialog? = null
    viewModel.progressLiveData.observe(this@EditPhoneActivity) {
      if (it) {
        progressDialog?.dismiss()
        progressDialog = null
        progressDialog = this@EditPhoneActivity.showProgressDialog()
      } else {
        progressDialog?.dismiss()
      }
    }
    viewModel.editPhoneLiveData.observe(this@EditPhoneActivity) {
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
      this@EditPhoneActivity.hideKeyboard()
      finish()
      super.onBackPressed()
    }
    else this@EditPhoneActivity.alert(
      MyApp.getInstance().getString(R.string.lbl_error),
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
        this@EditPhoneActivity.alert(
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
    public const val TAG: String = "EDIT_PHONE_ACTIVITY"

    public fun getIntent(context: Context, bundle: Bundle?): Intent {
      val destIntent = Intent(context, EditPhoneActivity::class.java)
      destIntent.putExtra("bundle", bundle)
      return destIntent
    }
  }

  private fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
      override fun afterTextChanged(editable: Editable?) {
        afterTextChanged.invoke(editable.toString())
      }

      override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

      override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
    })
  }
}
