package com.examonline.app.modules.historyscreen.ui

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
import com.examonline.app.databinding.ActivityHistoryScreenBinding
import com.examonline.app.extensions.*
import com.examonline.app.modules.detailhistoryscreen.ui.DetailHistoryScreenActivity
import com.examonline.app.modules.historyscreen.data.model.HistoryScreenRowModel
import com.examonline.app.modules.historyscreen.`data`.viewmodel.HistoryScreenVM
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

public class HistoryScreenFragment :
    BaseFragment<ActivityHistoryScreenBinding>(R.layout.activity_history_screen), KoinComponent {
  private val viewModel: HistoryScreenVM by viewModels<HistoryScreenVM>()
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
    item: HistoryScreenRowModel
  ): Unit {
    when(view.id) {
      R.id.cardView ->  {
        val destIntent = Intent(activity, DetailHistoryScreenActivity::class.java)
          destIntent.putExtra("ExamName", item.txtNameOfExam)
          destIntent.putExtra("NumQuestion", item.txtNumQuestion)
          destIntent.putExtra("Duration", item.txtDuration)
          destIntent.putExtra("TimeBegin", item.txtDateTime)
          destIntent.putExtra("TimeEnd", item.txtTimeEnd)
          destIntent.putExtra("ExamID", item.txtExamID)
        startActivity(destIntent)
      }
    }
  }
  public override fun onInitialized(): Unit {
    binding.refreshLayout.setOnRefreshListener {
      viewModel.onCreateExams()
      binding.refreshLayout.isRefreshing=false
    }
    viewModel.historyScreenModel.value?.txtHelloTeacher="Hello, "+prefs.getUserName()
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
                                 HistoryScreenRowModel
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
    binding.historyScreenVM = viewModel
  }

    public override fun addObservers(): Unit {
        var progressDialog: AlertDialog? = null
        viewModel.progressLiveData.observe(this@HistoryScreenFragment) {
            if (it) {
                progressDialog?.dismiss()
                progressDialog = null
                progressDialog = this@HistoryScreenFragment.activity?.showProgressDialog()
            } else {
                progressDialog?.dismiss()
            }
        }
        viewModel.getExamsLiveData.observe(this@HistoryScreenFragment) {
            if (it is SuccessResponse) {
                val response = it.getContentIfNotHandled()
                onSuccessGetProfile(it)
            } else if (it is ErrorResponse) {
                onErrorGetExams(it.data ?: Exception())
            }
        }
    }

    private fun onSuccessGetProfile(response: SuccessResponse<GetAllOfExamsResponse>) {
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
                this@HistoryScreenFragment.activity?.alert(
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
    public const val TAG: String = "HISTORY_SCREEN_ACTIVITY"

    public fun getIntent(context: Context, bundle: Bundle?): Intent {
      val destIntent = Intent(context, HistoryScreenFragment::class.java)
      destIntent.putExtra("bundle", bundle)
      return destIntent
    }
  }
}
