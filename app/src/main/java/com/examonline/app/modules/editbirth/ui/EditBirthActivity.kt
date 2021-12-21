package com.examonline.app.modules.editbirth.ui

import android.annotation.SuppressLint
import android.app.DatePickerDialog
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
import com.examonline.app.databinding.ActivityEditBirthBinding
import com.examonline.app.extensions.*
import com.examonline.app.modules.editbirth.`data`.viewmodel.EditBirthVM
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
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Pattern
import kotlin.String
import kotlin.Unit

public class EditBirthActivity :
    BaseActivity<ActivityEditBirthBinding>(R.layout.activity_edit_birth), KoinComponent {
  private val viewModel: EditBirthVM by viewModels<EditBirthVM>()
  private val prefs: PreferenceHelper by inject()


  @SuppressLint("SimpleDateFormat")
  public override fun setUpClicks(): Unit {
    binding.image.setOnClickListener {
      finish();
      super.onBackPressed();
    }

    binding.btnSave.setOnClickListener {
      if (binding.editDateOfBirth.text.isNotEmpty() && validateBirth()){
        val updateProfileRequest = UpdateProfileRequest(
          Firstname = intent.getStringExtra("Firstname").toString(),
          Lastname = intent.getStringExtra("Lastname").toString(),
          Gender = intent.getStringExtra("Gender").toBoolean(),
          Email = intent.getStringExtra("Email").toString(),
          DateOfBirth = binding.editDateOfBirth.text.toString().split("/").toTypedArray().reversedArray().joinToString("-"),
          Phone = intent.getStringExtra("Phone").toString(),
          Address = intent.getStringExtra("Address").toString(),
          Avatar = prefs.getAvatar().toString()
        )
        viewModel.onClickBtnSave(updateProfileRequest)
      }
    }

    binding.BirthLayout.setStartIconOnClickListener {
      val calendar: Calendar = Calendar.getInstance()
      val dateofbirth:EditText = binding.editDateOfBirth
      val dialog = DatePickerDialog(this, R.style.DialogTheme,{ _, year, month, day_of_month ->
        calendar[Calendar.YEAR] = year
        calendar[Calendar.MONTH] = month
        calendar[Calendar.DAY_OF_MONTH] = day_of_month
        val myFormat = "dd/MM/yyyy"
        val sdf = SimpleDateFormat(myFormat, Locale.getDefault())
        dateofbirth.setText(sdf.format(calendar.time))
      }, calendar[Calendar.YEAR], calendar[Calendar.MONTH], calendar[Calendar.DAY_OF_MONTH])
      calendar.add(Calendar.YEAR, 0)
      dialog.datePicker.maxDate = calendar.timeInMillis
      calendar.add(Calendar.YEAR, -80)
      dialog.datePicker.minDate = calendar.timeInMillis
      if (binding.editDateOfBirth.text.isNotEmpty()){
        val date = binding.editDateOfBirth.text.toString().split("/").toTypedArray()
        dialog.datePicker.updateDate(date[2].toInt(),date[1].toInt()-1,date[0].toInt())
      }
      else
        dialog.datePicker.updateDate(2000,0,1)
      dialog.show()
    }
    binding.editDateOfBirth.afterTextChanged {
      if (binding.editDateOfBirth.text.toString() != intent.getStringExtra("DateOfBirth").toString())
        binding.btnSave.visibility=View.VISIBLE
      else
        binding.btnSave.visibility=View.GONE
    }
  }

  public override fun onInitialized(): Unit {
    binding.editBirthVM = viewModel
    if(intent.getStringExtra("DateOfBirth").toString()!="null")
      viewModel.editBirthModel.value?.editDateOfBirth = intent.getStringExtra("DateOfBirth").toString()
  }

  private fun isValidDateFormat(date: String): Boolean {
    val d = date.split("/").toTypedArray()
    return date == d.joinToString("/")
  }

  private fun validateBirth(): Boolean {
    val dateInput: String = binding.editDateOfBirth.text.toString().trim()
    return if (dateInput.isEmpty()) {
      binding.editDateOfBirth.error = "Field can't be empty"
      false
    } else if (!isValidDateFormat(dateInput)) {
      binding.editDateOfBirth.error = "Please enter a valid date dd/MM/yyyy"
      false
    } else {
      binding.editDateOfBirth.error = null
      true
    }
  }

  public override fun addObservers(): Unit {
    var progressDialog: AlertDialog? = null
    viewModel.progressLiveData.observe(this@EditBirthActivity) {
      if (it) {
        progressDialog?.dismiss()
        progressDialog = null
        progressDialog = this@EditBirthActivity.showProgressDialog()
      } else {
        progressDialog?.dismiss()
      }
    }
    viewModel.editBirthLiveData.observe(this@EditBirthActivity) {
      if (it is SuccessResponse) {
        val response = it.getContentIfNotHandled()
        onSuccessEditBirth(it)
      } else if (it is ErrorResponse) {
        onErrorEditBirth(it.data ?: Exception())
      }
    }
  }
  private fun onSuccessEditBirth(response: SuccessResponse<UpdateProfileResponse>): Unit {
    if (response.data.status?.Code=="200"){
      this@EditBirthActivity.hideKeyboard()
      finish()
      super.onBackPressed()
    }
    else this@EditBirthActivity.alert(
      MyApp.getInstance().getString(R.string.lbl_error),
      response.data.message.toString()
    ) {
      neutralButton {
      }
    }
  }
  private fun onErrorEditBirth(exception: Exception): Unit {
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
        this@EditBirthActivity.alert(
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
    public const val TAG: String = "EDIT_BIRTH_ACTIVITY"

    public fun getIntent(context: Context, bundle: Bundle?): Intent {
      val destIntent = Intent(context, EditBirthActivity::class.java)
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
