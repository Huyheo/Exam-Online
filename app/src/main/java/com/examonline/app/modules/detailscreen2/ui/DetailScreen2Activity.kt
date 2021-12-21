package com.examonline.app.modules.detailscreen2.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.SearchView
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import com.examonline.app.R
import com.examonline.app.appcomponents.base.BaseActivity
import com.examonline.app.appcomponents.di.MyApp
import com.examonline.app.databinding.ActivityDetailScreen2Binding
import com.examonline.app.extensions.*
import com.examonline.app.modules.detailscreen2.`data`.model.DetailScreen3RowModel
import com.examonline.app.modules.detailscreen2.`data`.viewmodel.DetailScreen2VM
import com.examonline.app.network.models.getclasses.GetClassesResponse
import com.examonline.app.network.models.getmemofclass.GetMemOfClassResponse
import com.examonline.app.network.models.resources.ErrorResponse
import com.examonline.app.network.models.resources.SuccessResponse
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.json.JSONObject
import retrofit2.HttpException
import kotlin.Int
import kotlin.String
import kotlin.Unit

public class DetailScreen2Activity :
    BaseActivity<ActivityDetailScreen2Binding>(R.layout.activity_detail_screen2) {
  private val viewModel: DetailScreen2VM by viewModels<DetailScreen2VM>()
    private var response:GetMemOfClassResponse?=null

  public override fun setUpClicks(): Unit {
      viewModel.detailScreen2Model.value?.txtTeacherName =" "+intent.getStringExtra("TeacherName").toString()
      viewModel.detailScreen2Model.value?.txtClassName =" "+intent.getStringExtra("ClassName").toString()
      viewModel.detailScreen2Model.value?.txtNumStudent =" "+intent.getStringExtra("NumStudent").toString()

      binding.image.setOnClickListener {
          finish();
          super.onBackPressed();
      }
//      binding.btnOut.setOnClickListener {
//          this.alert("Leave?","Do you want to leave this class?") {
//              positiveButton(){
//                  //do positive actions
//                  finish();
//                  onBackPressed()
//                  it.dismiss()
//              }
//              negativeButton(){
//                  //do negative actions
//                  it.dismiss()
//              }
//          }
//      }
      viewModel.onCreateMemOfClass(intent.getStringExtra("ClassID").toString())

  }

    public override fun addObservers(): Unit {
        var progressDialog: AlertDialog? = null
        viewModel.progressLiveData.observe(this@DetailScreen2Activity) {
            if (it) {
                progressDialog?.dismiss()
                progressDialog = null
                progressDialog = this@DetailScreen2Activity.showProgressDialog()
            } else {
                progressDialog?.dismiss()
            }
        }
        viewModel.getMemOfClassLiveData.observe(this@DetailScreen2Activity) {
            if (it is SuccessResponse) {
                response = it.getContentIfNotHandled()
                onSuccessGetMemOfClass(it)
            } else if (it is ErrorResponse) {
                onErrorGetMemOfClass(it.data ?: Exception())
            }
        }
    }

    private fun onSuccessGetMemOfClass(response: SuccessResponse<GetMemOfClassResponse>) {
        viewModel.bindGetMemOfClassResponse(response.data)
    }
    private fun onErrorGetMemOfClass(exception: Exception): Unit {
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
                this@DetailScreen2Activity.alert(
                    MyApp.getInstance().getString(R.string.lbl_error),
                    errMessage
                ) {
                    neutralButton {
                    }
                }
            }
        }
    }

  public fun onClickRecyclerContent(
    view: View,
    position: Int,
    item: DetailScreen3RowModel
  ): Unit {
    when(view.id) {


    }
  }

  public override fun onInitialized(): Unit {
      binding.btnOut.visibility=View.GONE
    val recyclerContentAdapter =
                        RecyclerContentAdapter(
                            viewModel.recyclerContentList.value?:mutableListOf()
                        )
    binding.recyclerContent.adapter = recyclerContentAdapter
    recyclerContentAdapter.setOnItemClickListener(
                object : RecyclerContentAdapter.OnItemClickListener {
                    override fun onItemClick(view:View, position:Int, item : DetailScreen3RowModel)
        {
                        onClickRecyclerContent(view, position, item)
                    }
                }
                )
    viewModel.recyclerContentList.observe(this) {
                    recyclerContentAdapter.updateData(it)
                }
    binding.detailScreen2VM = viewModel

      binding.etSearch.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
          override fun onQueryTextSubmit(query: String?): Boolean {
              return false
          }

          override fun onQueryTextChange(newText: String?): Boolean {
              recyclerContentAdapter.filter.filter(newText)
              return false
          }

      })

  }

  public companion object {
    public const val TAG: String = "DETAIL_SCREEN2ACTIVITY"

    public fun getIntent(context: Context, bundle: Bundle?): Intent {
      val destIntent = Intent(context, DetailScreen2Activity::class.java)
      destIntent.putExtra("bundle", bundle)
      return destIntent
    }
  }
}
