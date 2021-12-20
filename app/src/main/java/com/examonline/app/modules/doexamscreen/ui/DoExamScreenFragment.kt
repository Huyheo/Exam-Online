package com.examonline.app.modules.doexamscreen.ui

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.viewModels
import com.examonline.app.R
import com.examonline.app.appcomponents.base.BaseFragment
import com.examonline.app.appcomponents.di.MyApp
import com.examonline.app.appcomponents.utility.PreferenceHelper
import com.examonline.app.databinding.ActivityDoExamScreenBinding
import com.examonline.app.extensions.*
import com.examonline.app.modules.detailscreen.ui.DetailScreenActivity
import com.examonline.app.modules.doexamscreen.data.model.DoExamScreenRowModel
import com.examonline.app.modules.doexamscreen.`data`.viewmodel.DoExamScreenVM
import com.examonline.app.network.models.getallofexams.GetAllOfExamsResponse
import com.examonline.app.network.models.resources.ErrorResponse
import com.examonline.app.network.models.resources.SuccessResponse
import com.google.android.material.snackbar.Snackbar
import org.json.JSONObject
import org.koin.core.KoinComponent
import org.koin.core.inject
import retrofit2.HttpException
import kotlin.String
import kotlin.Unit

public class DoExamScreenFragment :
    BaseFragment<ActivityDoExamScreenBinding>(R.layout.activity_do_exam_screen), KoinComponent {
  private val viewModel: DoExamScreenVM by viewModels<DoExamScreenVM>()
  private val prefs: PreferenceHelper by inject()

  override fun onStart() {
    super.onStart()
    viewModel.onCreateExams()
  }

  public override fun setUpClicks(): Unit {
  }

  public fun onClickRecyclerView(
    view: View,
    position: Int,
    item: DoExamScreenRowModel
  ): Unit {
    when(view.id) {
      R.id.cardView ->  {
        val destIntent = Intent(activity, DetailScreenActivity::class.java)
        destIntent.putExtra("ExamName", item.txtNameOfExam)
        destIntent.putExtra("NumQuestion", item.txtNumQuestion)
        destIntent.putExtra("Duration", item.Duration)
        destIntent.putExtra("TimeBegin", item.txtDateTime)
        destIntent.putExtra("TimeEnd", item.txtTimeEnd)
        destIntent.putExtra("ExamID", item.txtExamID)
        destIntent.putExtra("DoingFlag", item.DoingFlag)
        destIntent.putExtra("Expired", item.Expired)
        destIntent.putExtra("txtDuration", item.txtDuration)
        startActivity(destIntent)
      }
    }
  }


  @SuppressLint("SetTextI18n")
  public override fun onInitialized(): Unit {
    binding.refreshLayout.setOnRefreshListener {
      viewModel.onCreateExams()
      binding.refreshLayout.isRefreshing=false
    }
    viewModel.doExamScreenModel.value?.txtHelloTeacher="Hello, "+prefs.getUserName()
    val recyclerViewAdapter =
      RecyclerViewAdapter(viewModel.recyclerViewList.value?:mutableListOf())
    binding.recyclerView.adapter = recyclerViewAdapter
    recyclerViewAdapter.setOnItemClickListener(
      object :
        RecyclerViewAdapter.OnItemClickListener
      {
        override fun onItemClick(view: View,
                                 position:Int,
                                 item :
                                 DoExamScreenRowModel
        )
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
    binding.doExamScreenVM = viewModel
  }

  public override fun addObservers(): Unit {
    var progressDialog: AlertDialog? = null
    viewModel.progressLiveData.observe(this@DoExamScreenFragment) {
      if (it) {
        progressDialog?.dismiss()
        progressDialog = null
        progressDialog = this@DoExamScreenFragment.activity?.showProgressDialog()
      } else {
        progressDialog?.dismiss()
      }
    }
    viewModel.getExamsLiveData.observe(this@DoExamScreenFragment) {
      if (it is SuccessResponse) {
        val response = it.getContentIfNotHandled()
        onSuccessGetExams(it)
      } else if (it is ErrorResponse) {
        onErrorGetExams(it.data ?: Exception())
      }
    }
  }

  private fun onSuccessGetExams(response: SuccessResponse<GetAllOfExamsResponse>) {
    viewModel.bindGetExamsResponse(response.data)
  }
  private fun onErrorGetExams(exception: Exception): Unit {
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
        this@DoExamScreenFragment.activity?.alert(
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
    public const val TAG: String = "DO_EXAM_SCREEN_ACTIVITY"

    public fun getIntent(context: Context, bundle: Bundle?): Intent {
      val destIntent = Intent(context, DoExamScreenFragment::class.java)
      destIntent.putExtra("bundle", bundle)
      return destIntent
    }
  }
}
