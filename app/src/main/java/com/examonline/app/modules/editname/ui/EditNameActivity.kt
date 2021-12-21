package com.examonline.app.modules.editname.ui

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
import com.examonline.app.MainActivity
import com.examonline.app.R
import com.examonline.app.appcomponents.base.BaseActivity
import com.examonline.app.appcomponents.di.MyApp
import com.examonline.app.appcomponents.utility.PreferenceHelper
import com.examonline.app.databinding.ActivityEditNameBinding
import com.examonline.app.extensions.*
import com.examonline.app.modules.editname.data.viewmodel.EditNameVM
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


public class EditNameActivity : BaseActivity<ActivityEditNameBinding>(R.layout.activity_edit_name), KoinComponent {
  private val viewModel: EditNameVM by viewModels<EditNameVM>()
  private val prefs: PreferenceHelper by inject()


  public override fun setUpClicks(): Unit {
    binding.image.setOnClickListener {
      finish()
      super.onBackPressed()
    }
    binding.editFirstName.afterTextChanged {
      if (binding.editFirstName.text.toString().trim() != intent.getStringExtra("Firstname").toString())
        binding.btnSave.visibility= View.VISIBLE
      else
        binding.btnSave.visibility=View.GONE
    }
    binding.editLastName.afterTextChanged {
      if (binding.editLastName.text.toString().trim() != intent.getStringExtra("Lastname").toString())
        binding.btnSave.visibility= View.VISIBLE
      else
        binding.btnSave.visibility=View.GONE
    }

    binding.btnSave.setOnClickListener {
      if (binding.editFirstName.text.isNotEmpty() && binding.editLastName.text.isNotEmpty()){
        val updateProfileRequest = UpdateProfileRequest(
          Firstname = binding.editFirstName.text.toString(),
          Lastname = binding.editLastName.text.toString(),
          Gender = intent.getStringExtra("Gender").toBoolean(),
          Email = intent.getStringExtra("Email").toString(),
          DateOfBirth = intent.getStringExtra("DateOfBirth").toString()
            .split("/").toTypedArray().reversedArray().joinToString("-"),
          Phone = intent.getStringExtra("Phone").toString(),
          Address = intent.getStringExtra("Address").toString(),
          Avatar = prefs.getAvatar().toString()
        )
        viewModel.onClickBtnSave(updateProfileRequest)
      }
      else if (binding.editFirstName.text.isEmpty())
        binding.editFirstName.error = "Field can't be empty"
      else
        binding.editLastName.error = "Field can't be empty"
    }
  }

  public override fun onInitialized(): Unit {
    binding.editNameVM = viewModel
    if(intent.getStringExtra("Firstname").toString()!="null")
      viewModel.editNameModel.value?.editFirstName = intent.getStringExtra("Firstname").toString()
    if(intent.getStringExtra("Lastname").toString()!="null")
      viewModel.editNameModel.value?.editLastName = intent.getStringExtra("Lastname").toString()
  }

  public override fun addObservers(): Unit {
    var progressDialog: AlertDialog? = null
    viewModel.progressLiveData.observe(this@EditNameActivity) {
      if (it) {
        progressDialog?.dismiss()
        progressDialog = null
        progressDialog = this@EditNameActivity.showProgressDialog()
      } else {
        progressDialog?.dismiss()
      }
    }
    viewModel.editNameLiveData.observe(this@EditNameActivity) {
      if (it is SuccessResponse) {
        val response = it.getContentIfNotHandled()
        onSuccessEditName(it)
      } else if (it is ErrorResponse) {
        onErrorEditName(it.data ?: Exception())
      }
    }
  }
  private fun onSuccessEditName(response: SuccessResponse<UpdateProfileResponse>): Unit {
    if (response.data.status?.Code=="200"){
      this@EditNameActivity.hideKeyboard()
      finish()
      super.onBackPressed()
    }
    else this@EditNameActivity.alert(
      MyApp.getInstance().getString(R.string.lbl_error),
      response.data.message.toString()
    ) {
      neutralButton {
      }
    }
  }
  private fun onErrorEditName(exception: Exception): Unit {
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
        this@EditNameActivity.alert(
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
    public const val TAG: String = "EDIT_NAME_ACTIVITY"

    public fun getIntent(context: Context, bundle: Bundle?): Intent {
      val destIntent = Intent(context, EditNameActivity::class.java)
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
