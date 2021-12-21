package com.examonline.app.modules.classesscreen.ui

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.viewModels
import com.examonline.app.R
import com.examonline.app.appcomponents.base.BaseFragment
import com.examonline.app.appcomponents.di.MyApp
import com.examonline.app.appcomponents.utility.PreferenceHelper
import com.examonline.app.databinding.ActivityClassesScreenBinding
import com.examonline.app.extensions.*
import com.examonline.app.modules.classesscreen.data.model.ClassesScreenRowModel
import com.examonline.app.modules.classesscreen.`data`.viewmodel.ClassesScreenVM
import com.examonline.app.modules.detailscreen2.ui.DetailScreen2Activity
import com.examonline.app.modules.joinclass.ui.JoinClassActivity
import com.examonline.app.network.models.getclasses.GetClassesResponse
import com.examonline.app.network.models.getprofile.GetProfileResponse
import com.examonline.app.network.models.resources.ErrorResponse
import com.examonline.app.network.models.resources.SuccessResponse
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.json.JSONObject
import org.koin.core.KoinComponent
import org.koin.core.inject
import retrofit2.HttpException
import kotlin.String
import kotlin.Unit

public class ClassesScreenFragment :
    BaseFragment<ActivityClassesScreenBinding>(R.layout.activity_classes_screen), KoinComponent {
  private val viewModel: ClassesScreenVM by viewModels<ClassesScreenVM>()
    private var response:GetClassesResponse?=null
    private val prefs: PreferenceHelper by inject()

  public override fun setUpClicks(): Unit {
  }


    public override fun addObservers(): Unit {
        var progressDialog: AlertDialog? = null
        viewModel.progressLiveData.observe(this@ClassesScreenFragment) {
            if (it) {
                progressDialog?.dismiss()
                progressDialog = null
                progressDialog = this@ClassesScreenFragment.activity?.showProgressDialog()
            } else {
                progressDialog?.dismiss()
            }
        }
        viewModel.getClassesLiveData.observe(this@ClassesScreenFragment) {
            if (it is SuccessResponse) {
                response = it.getContentIfNotHandled()
                onSuccessGetClasses(it)
            } else if (it is ErrorResponse) {
                onErrorCreateClasses(it.data ?: Exception())
            }
        }
    }

    private fun onSuccessGetClasses(response: SuccessResponse<GetClassesResponse>) {
        viewModel.bindGetClassesResponse(response.data)
    }
    private fun onErrorCreateClasses(exception: Exception): Unit {
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
                this@ClassesScreenFragment.activity?.alert(
                    MyApp.getInstance().getString(R.string.lbl_error),
                    errMessage
                ) {
                    neutralButton {
                    }
                }
            }
        }
    }

  public fun onClickRecyclerView(
    view: View,
    position: Int,
    item: ClassesScreenRowModel
  ): Unit {
    when(view.id) {
      R.id.cardView ->  {
        val destIntent = Intent(activity, DetailScreen2Activity::class.java)
          destIntent.putExtra("ClassName", item.txtClassName)
          destIntent.putExtra("TeacherName", item.txtTeacherName)
          destIntent.putExtra("NumStudent", item.txtNumStudent)
          destIntent.putExtra("ClassID", item.classID)
        startActivity(destIntent)
      }
    }
  }

  @SuppressLint("SetTextI18n")
  public override fun onInitialized(): Unit {
      viewModel.onCreateClasses()
      binding.refreshLayout.setOnRefreshListener {
          viewModel.onCreateClasses()
          binding.refreshLayout.isRefreshing=false
      }
      binding.floatingBtn.visibility=View.GONE
      viewModel.classesScreenModel.value?.txtHelloTeacher="Hello, "+prefs.getUserName()
    val recyclerViewAdapter =
      RecyclerViewAdapter(
        viewModel.recyclerViewList.value?:mutableListOf()
      )
    binding.recyclerView.adapter = recyclerViewAdapter
    recyclerViewAdapter.setOnItemClickListener(
      object :
        RecyclerViewAdapter.OnItemClickListener
      {
        override
        fun
                onItemClick(view:View,
                            position:Int,
                            item :
                            ClassesScreenRowModel)
        {
          onClickRecyclerView(view,
            position,
            item)
        }
      }
    )
    viewModel.recyclerViewList.observe(this) {

      recyclerViewAdapter.updateData(it)
    }
    binding.classesScreenVM = viewModel
  }

  public companion object {
    public const val TAG: String = "CLASSES_SCREEN_ACTIVITY"

    public fun getIntent(context: Context, bundle: Bundle?): Intent {
      val destIntent = Intent(context, ClassesScreenFragment::class.java)
      destIntent.putExtra("bundle", bundle)
      return destIntent
    }
  }
}
