package com.examonline.app.modules.profilescreen.ui

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.viewModels
import com.examonline.app.R
import com.examonline.app.appcomponents.base.BaseFragment
import com.examonline.app.appcomponents.di.MyApp
import com.examonline.app.appcomponents.utility.PreferenceHelper
import com.examonline.app.databinding.ActivityProfileScreenBinding
import com.examonline.app.extensions.*
import com.examonline.app.modules.editbirth.ui.EditBirthActivity
import com.examonline.app.modules.editemail.ui.EditEmailActivity
import com.examonline.app.modules.editgender.ui.EditGenderActivity
import com.examonline.app.modules.editaddress.ui.EditAddressActivity
import com.examonline.app.modules.editname.ui.EditNameActivity
import com.examonline.app.modules.editpassword.ui.EditPasswordActivity
import com.examonline.app.modules.editphone.ui.EditPhoneActivity
import com.examonline.app.modules.profilescreen.`data`.viewmodel.ProfileScreenVM
import com.examonline.app.network.models.getprofile.GetProfileResponse
import com.examonline.app.network.models.resources.ErrorResponse
import com.examonline.app.network.models.resources.SuccessResponse
import com.google.android.material.snackbar.Snackbar
import org.json.JSONObject
import org.koin.core.KoinComponent
import org.koin.core.inject
import retrofit2.HttpException
import kotlin.String
import kotlin.Unit

public class ProfileScreenFragment :
    BaseFragment<ActivityProfileScreenBinding>(R.layout.activity_profile_screen), KoinComponent {
  val viewModel: ProfileScreenVM by viewModels<ProfileScreenVM>()
  private var response:GetProfileResponse?=null
  private val prefs: PreferenceHelper by inject()

  override fun onStart() {
    super.onStart()
    viewModel.onClickOnCreate()
  }

  public override fun setUpClicks(): Unit {
    binding.linear1.setOnClickListener {
      val destIntent = Intent(activity, EditNameActivity::class.java)
      if (response?.status?.Code.toString()=="200") {
        destIntent.putExtra("Firstname", response?.data?.get(0)?.Firstname)
        destIntent.putExtra("Lastname", response?.data?.get(0)?.Lastname)
        destIntent.putExtra("Gender", response?.data?.get(0)?.Gender.toString())
        destIntent.putExtra("DateOfBirth", binding.txtDateOfBirth.text.toString())
        destIntent.putExtra("Phone", response?.data?.get(0)?.Phone)
        destIntent.putExtra("Email", response?.data?.get(0)?.Email)
        destIntent.putExtra("Address", response?.data?.get(0)?.Address)
        startActivity(destIntent)
      }
    }
    binding.linear11.setOnClickListener {
      val destIntent = Intent(activity, EditGenderActivity::class.java)
      if (response?.status?.Code.toString()=="200") {
        destIntent.putExtra("Firstname", response?.data?.get(0)?.Firstname)
        destIntent.putExtra("Lastname", response?.data?.get(0)?.Lastname)
        destIntent.putExtra("Gender", response?.data?.get(0)?.Gender.toString())
        destIntent.putExtra("DateOfBirth", binding.txtDateOfBirth.text.toString())
        destIntent.putExtra("Phone", response?.data?.get(0)?.Phone)
        destIntent.putExtra("Email", response?.data?.get(0)?.Email)
        destIntent.putExtra("Address", response?.data?.get(0)?.Address)
        startActivity(destIntent)
      }
    }
    binding.linear12.setOnClickListener {
      val destIntent = Intent(activity, EditBirthActivity::class.java)
      if (response?.status?.Code.toString()=="200") {
        destIntent.putExtra("Firstname", response?.data?.get(0)?.Firstname)
        destIntent.putExtra("Lastname", response?.data?.get(0)?.Lastname)
        destIntent.putExtra("Gender", response?.data?.get(0)?.Gender.toString())
        destIntent.putExtra("DateOfBirth", binding.txtDateOfBirth.text.toString())
        destIntent.putExtra("Phone", response?.data?.get(0)?.Phone)
        destIntent.putExtra("Email", response?.data?.get(0)?.Email)
        destIntent.putExtra("Address", response?.data?.get(0)?.Address)
        startActivity(destIntent)
      }
    }
    binding.linear13.setOnClickListener {
      val destIntent = Intent(activity, EditPhoneActivity::class.java)
      if (response?.status?.Code.toString()=="200") {
        destIntent.putExtra("Firstname", response?.data?.get(0)?.Firstname)
        destIntent.putExtra("Lastname", response?.data?.get(0)?.Lastname)
        destIntent.putExtra("Gender", response?.data?.get(0)?.Gender.toString())
        destIntent.putExtra("DateOfBirth", binding.txtDateOfBirth.text.toString())
        destIntent.putExtra("Phone", response?.data?.get(0)?.Phone)
        destIntent.putExtra("Email", response?.data?.get(0)?.Email)
        destIntent.putExtra("Address", response?.data?.get(0)?.Address)
        startActivity(destIntent)
      }
    }
    binding.linear14.setOnClickListener {
      val destIntent = Intent(activity, EditEmailActivity::class.java)
      if (response?.status?.Code.toString()=="200") {
        destIntent.putExtra("Firstname", response?.data?.get(0)?.Firstname)
        destIntent.putExtra("Lastname", response?.data?.get(0)?.Lastname)
        destIntent.putExtra("Gender", response?.data?.get(0)?.Gender.toString())
        destIntent.putExtra("DateOfBirth", binding.txtDateOfBirth.text.toString())
        destIntent.putExtra("Phone", response?.data?.get(0)?.Phone)
        destIntent.putExtra("Email", response?.data?.get(0)?.Email)
        destIntent.putExtra("Address", response?.data?.get(0)?.Address)
        startActivity(destIntent)
      }
    }
    binding.linear15.setOnClickListener {
      val destIntent = Intent(activity, EditPasswordActivity::class.java)
      if (response?.status?.Code.toString()=="200") {
        destIntent.putExtra("Password", response?.data?.get(0)?.Password)
        startActivity(destIntent)
      }
    }
    binding.linear16.setOnClickListener {
      val destIntent = Intent(activity, EditAddressActivity::class.java)
      if (response?.status?.Code.toString()=="200") {
        destIntent.putExtra("Firstname", response?.data?.get(0)?.Firstname)
        destIntent.putExtra("Lastname", response?.data?.get(0)?.Lastname)
        destIntent.putExtra("Gender", response?.data?.get(0)?.Gender.toString())
        destIntent.putExtra("DateOfBirth", binding.txtDateOfBirth.text.toString())
        destIntent.putExtra("Phone", response?.data?.get(0)?.Phone)
        destIntent.putExtra("Email", response?.data?.get(0)?.Email)
        destIntent.putExtra("Address", response?.data?.get(0)?.Address)
        startActivity(destIntent)
      }
    }
  }


  @SuppressLint("SetTextI18n")
  public override fun onInitialized(): Unit {
    binding.refreshLayout.setOnRefreshListener {
      viewModel.onClickOnCreate()
      binding.refreshLayout.isRefreshing=false
    }
    binding.profileScreenVM=viewModel
    viewModel.profileModel.value?.txtHello="Hello, "+prefs.getUserName()
  }

  public override fun addObservers(): Unit {
    var progressDialog: AlertDialog? = null
    viewModel.progressLiveData.observe(this@ProfileScreenFragment) {
      if (it) {
        progressDialog?.dismiss()
        progressDialog = null
        progressDialog = this@ProfileScreenFragment.activity?.showProgressDialog()
      } else {
        progressDialog?.dismiss()
      }
    }
    viewModel.getProfileLiveData.observe(this@ProfileScreenFragment) {
      if (it is SuccessResponse) {
        response = it.getContentIfNotHandled()
        onSuccessGetProfile(it)
      } else if (it is ErrorResponse) {
        onErrorCreateUser(it.data ?: Exception())
      }
    }
  }

  private fun onSuccessGetProfile(response: SuccessResponse<GetProfileResponse>): Unit {
    viewModel.bindCreateUserResponse(response.data)
  }
  private fun onErrorCreateUser(exception: Exception): Unit {
    when (exception) {
      is NoInternetConnection -> {
        Snackbar.make(binding.root, exception.message ?: "", Snackbar.LENGTH_LONG).show()
      }
      is HttpException -> {
        val errorBody = exception.response()?.errorBody()?.string()
        val errorObject =
          if (errorBody != null && errorBody.isJSONObject()) JSONObject(errorBody)
          else JSONObject()
        val errMessage = if (!errorObject.optString("message").isNullOrEmpty()) {
          errorObject.optString("message").toString()
        } else {
          exception.response()?.message() ?: ""
        }
        this@ProfileScreenFragment.activity?.alert(
          MyApp.getInstance().getString(R.string.lbl_error),
          errMessage
        ) {
          neutralButton {
          }
        }
      }
    }
  }

  public companion object {
    public const val TAG: String = "PROFILE_SCREEN_FRAGMENT_ACTIVITY"

    public fun getIntent(context: Context, bundle: Bundle?): Intent {
      val destIntent = Intent(context, ProfileScreenFragment::class.java)
      destIntent.putExtra("bundle", bundle)
      return destIntent
    }
  }
}
