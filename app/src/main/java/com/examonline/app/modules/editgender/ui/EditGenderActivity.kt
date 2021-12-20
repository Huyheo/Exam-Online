package com.examonline.app.modules.editgender.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.FragmentManager
import com.examonline.app.R
import com.examonline.app.appcomponents.base.BaseActivity
import com.examonline.app.appcomponents.di.MyApp
import com.examonline.app.appcomponents.utility.PreferenceHelper
import com.examonline.app.databinding.ActivityEditGenderBinding
import com.examonline.app.extensions.*
import com.examonline.app.modules.editgender.`data`.viewmodel.EditGenderVM
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

public class EditGenderActivity :
    BaseActivity<ActivityEditGenderBinding>(R.layout.activity_edit_gender), KoinComponent {
  private val viewModel: EditGenderVM by viewModels<EditGenderVM>()
  private val prefs: PreferenceHelper by inject()


  public override fun setUpClicks(): Unit {
    binding.image.setOnClickListener {
      finish();
      super.onBackPressed();
    }

    binding.Gender.setOnCheckedChangeListener { _, _ ->
      if (binding.Male.isChecked != intent.getStringExtra("Gender").toBoolean() && binding.Male.isChecked.toString()!=binding.Female.isChecked.toString())
        binding.btnSave.visibility= View.VISIBLE
      else if (binding.Male.isChecked.toString()==binding.Female.isChecked.toString())
        binding.btnSave.visibility= View.GONE
      else
        binding.btnSave.visibility= View.GONE
    }

    binding.btnSave.setOnClickListener {
      val updateProfileRequest = UpdateProfileRequest(
        Firstname = intent.getStringExtra("Firstname").toString(),
        Lastname = intent.getStringExtra("Lastname").toString(),
        Gender = binding.Male.isChecked,
        Email = intent.getStringExtra("Email").toString(),
        DateOfBirth = intent.getStringExtra("DateOfBirth").toString()
          .split("/").toTypedArray().reversedArray().joinToString("-"),
        Phone = intent.getStringExtra("Phone").toString(),
        Address = intent.getStringExtra("Address").toString(),
        Avatar = prefs.getAvatar().toString()
      )
      viewModel.onClickBtnSave(updateProfileRequest)
    }
  }

  public override fun onInitialized(): Unit {
    binding.editGenderVM = viewModel
    if (intent.getStringExtra("Gender").toString()=="true")
      binding.Gender.check(binding.Male.id)
    else if (intent.getStringExtra("Gender").toString()=="false")
      binding.Gender.check(binding.Female.id)
  }

  public override fun addObservers(): Unit {
    var progressDialog: AlertDialog? = null
    viewModel.progressLiveData.observe(this@EditGenderActivity) {
      if (it) {
        progressDialog?.dismiss()
        progressDialog = null
        progressDialog = this@EditGenderActivity.showProgressDialog()
      } else {
        progressDialog?.dismiss()
      }
    }
    viewModel.editGenderLiveData.observe(this@EditGenderActivity) {
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
      this@EditGenderActivity.hideKeyboard()
      finish()
      super.onBackPressed()
    }
    else this@EditGenderActivity.alert(
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
        this@EditGenderActivity.alert(
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
    public const val TAG: String = "EDIT_GENDER_ACTIVITY"

    public fun getIntent(context: Context, bundle: Bundle?): Intent {
      val destIntent = Intent(context, EditGenderActivity::class.java)
      destIntent.putExtra("bundle", bundle)
      return destIntent
    }
  }
}
