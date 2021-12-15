package com.examonline.app.modules.joinclass.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import com.examonline.app.MainActivity
import com.examonline.app.R
import com.examonline.app.appcomponents.base.BaseActivity
import com.examonline.app.appcomponents.di.MyApp
import com.examonline.app.databinding.ActivityJoinClassBinding
import com.examonline.app.extensions.*
import com.examonline.app.modules.joinclass.`data`.viewmodel.JoinClassVM
import com.examonline.app.network.models.createlogin.CreateLoginResponse
import com.examonline.app.network.models.memtoclass.MemToClassResponse
import com.examonline.app.network.models.resources.ErrorResponse
import com.examonline.app.network.models.resources.SuccessResponse
import com.google.android.material.snackbar.Snackbar
import org.json.JSONObject
import retrofit2.HttpException
import kotlin.String
import kotlin.Unit

public class JoinClassActivity :
    BaseActivity<ActivityJoinClassBinding>(R.layout.activity_join_class) {
  private val viewModel: JoinClassVM by viewModels<JoinClassVM>()

  public override fun setUpClicks(): Unit {
    binding.image.setOnClickListener {
      finish();
      super.onBackPressed();
    }
//    binding.btnJoin.setOnClickListener {
//      viewModel.onCreateAddMem()
//    }
  }

  public override fun onInitialized(): Unit {
    binding.joinClassVM = viewModel
  }

  public override fun addObservers(): Unit {
    var progressDialog: AlertDialog? = null
    viewModel.progressLiveData.observe(this@JoinClassActivity) {
      if (it) {
        progressDialog?.dismiss()
        progressDialog = null
        progressDialog = this@JoinClassActivity.showProgressDialog()
      } else {
        progressDialog?.dismiss()
      }
    }
    viewModel.putMemToClassLiveData.observe(this@JoinClassActivity) {
      if (it is SuccessResponse) {
        val response = it.getContentIfNotHandled()
        onSuccessCreateLogin(it)
      } else if (it is ErrorResponse) {
        onErrorCreateLogin(it.data ?: Exception())
      }
    }
  }
  private fun onSuccessCreateLogin(response: SuccessResponse<MemToClassResponse>): Unit {
    if (response.data.status?.Code =="200"){
      viewModel.bindAddMemToClassResponse(response.data)
      finish()
    }
    else this@JoinClassActivity.alert(
      MyApp.getInstance().getString(R.string.lbl_error),
      response.data.status?.Status.toString()
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
        this@JoinClassActivity.alert(
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
    public const val TAG: String = "JOIN_CLASS_ACTIVITY"

    public fun getIntent(context: Context, bundle: Bundle?): Intent {
      val destIntent = Intent(context, JoinClassActivity::class.java)
      destIntent.putExtra("bundle", bundle)
      return destIntent
    }
  }
}
