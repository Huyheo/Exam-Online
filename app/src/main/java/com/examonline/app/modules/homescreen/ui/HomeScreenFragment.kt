package com.examonline.app.modules.homescreen.ui

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.RectF
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.viewModels
import com.examonline.app.R
import com.examonline.app.appcomponents.base.BaseFragment
import com.examonline.app.appcomponents.di.MyApp
import com.examonline.app.appcomponents.utility.PreferenceHelper
import com.examonline.app.databinding.ActivityHomeScreenBinding
import com.examonline.app.extensions.*
import com.examonline.app.modules.homescreen.`data`.viewmodel.HomeScreenVM
import com.examonline.app.network.models.getallofexams.GetAllOfExamsResponse
import com.examonline.app.network.models.resources.ErrorResponse
import com.examonline.app.network.models.resources.SuccessResponse
import kotlin.String
import kotlin.Unit
import com.google.android.material.snackbar.Snackbar
import me.jlurena.revolvingweekview.WeekView
import me.jlurena.revolvingweekview.WeekViewEvent
import org.json.JSONObject
import org.koin.core.KoinComponent
import org.koin.core.inject
import retrofit2.HttpException
import java.util.*
import kotlin.collections.ArrayList
import androidx.annotation.ColorInt
import com.examonline.app.modules.detailscreen.ui.DetailScreenActivity
import org.threeten.bp.LocalTime
import java.text.SimpleDateFormat


public class HomeScreenFragment :
    BaseFragment<ActivityHomeScreenBinding>(R.layout.activity_home_screen), KoinComponent, WeekView.EventClickListener, WeekView.WeekViewLoader{
  private val viewModel: HomeScreenVM by viewModels<HomeScreenVM>()
  private var response: GetAllOfExamsResponse?=null
  private val prefs: PreferenceHelper by inject()
  private val random = Random()
  private val events: MutableList<WeekViewEvent> = ArrayList()

  @ColorInt
  private fun randomColor(): Int {
    return Color.argb(255, random.nextInt(256), random.nextInt(256), random.nextInt(256))
  }

  override fun onStart() {
    super.onStart()
    if (events.isEmpty())
      viewModel.onCreateExams()
  }

  public override fun setUpClicks(): Unit {
  }

  public override fun addObservers(): Unit {
    var progressDialog: AlertDialog? = null
    viewModel.progressLiveData.observe(this@HomeScreenFragment) {
      if (it) {
        progressDialog?.dismiss()
        progressDialog = null
        progressDialog = this@HomeScreenFragment.activity?.showProgressDialog()
      } else {
        progressDialog?.dismiss()
      }
    }
    viewModel.getExamsLiveData.observe(this@HomeScreenFragment) {
      if (it is SuccessResponse) {
        response = it.getContentIfNotHandled()
        onSuccessGetProfile(it)
      } else if (it is ErrorResponse) {
        onErrorGetExams(it.data ?: Exception())
      }
    }
  }

  private fun onSuccessGetProfile(response: SuccessResponse<GetAllOfExamsResponse>) {
    if (events.isEmpty()) {
      for (s in response.data.data!!) {
        if(s.TimeEnd?.time!! > System.currentTimeMillis()
//          && s.DoingFlag.equals("NotDone")
        ) {
          val duration = if (s.Duration!! > 60) {
            s.Duration?.div(60).toString() + " Hour " +
                    s.Duration?.rem(60).toString() + " Minute"
          } else {
            s.Duration.toString() + " Minute"
          }

          val info: MutableList<String> = ArrayList()
          info.add(s.ExamID.toString())
          info.add(s.TimeBegin!!.time.toString())
          info.add(s.TimeEnd!!.time.toString())
          info.add(duration)
          info.add(s.TotalQuestions.toString() + " Questions")
          info.add(s.DoingFlag.toString())

          if (s.TimeBegin!!.hours < s.TimeEnd!!.hours) {
            val event = WeekViewEvent(
              info.joinToString(","), s.ExamName,
              s.TimeBegin!!.day, s.TimeBegin!!.hours, s.TimeBegin!!.minutes,
              s.TimeBegin!!.day, s.TimeEnd!!.hours, s.TimeEnd!!.minutes
            )
            event.color = randomColor()
            event.location = "- " + s.ClassName.toString()
            events.add(event)
          } else {
            val event = WeekViewEvent(
              info.joinToString(","), s.ExamName,
              s.TimeBegin!!.day, s.TimeEnd!!.hours, s.TimeBegin!!.minutes,
              s.TimeBegin!!.day, s.TimeBegin!!.hours, s.TimeEnd!!.minutes
            )
            event.color = randomColor()
            event.location = "- " + s.ClassName.toString()
            events.add(event)
          }
        }
        binding.revolvingWeekview.notifyDatasetChanged()
      }
    }
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
        this@HomeScreenFragment.activity?.alert(
          MyApp.getInstance().getString(R.string.lbl_error),
          errMessage
        ) {
          neutralButton {
          }
        }
      }
    }
  }

  @SuppressLint("SetTextI18n", "ClickableViewAccessibility")
  public override fun onInitialized(): Unit {
    binding.revolvingWeekview.weekViewLoader = WeekView.WeekViewLoader { // Add some events
      events
    }
    binding.revolvingWeekview.setOnEventClickListener(this)

    binding.homeScreenVM = viewModel
    viewModel.homeScreenModel.value?.txtHelloTeacher = "Hello, "+prefs.getUserName()


  }

  public companion object {
    public const val TAG: String = "HOME_SCREEN_ACTIVITY"

    public fun getIntent(context: Context, bundle: Bundle?): Intent {
      val destIntent = Intent(context, HomeScreenFragment::class.java)
      destIntent.putExtra("bundle", bundle)
      return destIntent
    }
  }

  override fun onEventClick(event: WeekViewEvent?, eventRect: RectF?) {
    Log.d("alllllll",event?.identifier.toString())
    val info = event?.identifier.toString().split(",").toTypedArray()
    val destIntent = Intent(activity, DetailScreenActivity::class.java)
    destIntent.putExtra("ExamName", event?.name)
    destIntent.putExtra("NumQuestion", info[4])
    destIntent.putExtra("Duration", info[3])
    destIntent.putExtra("TimeBegin", convertTime(info[1].toLong()))
    destIntent.putExtra("TimeEnd", convertTime(info[2].toLong()))
    destIntent.putExtra("ExamID", info[0])
    destIntent.putExtra("DoingFlag", info[5])
    startActivity(destIntent)
  }

  private fun convertTime (time: Long): String? {
    val myFormat = "hh:mm dd/MM/yyyy"
    val sdf = SimpleDateFormat(myFormat, Locale.getDefault())
    return sdf.format(time)
  }

  override fun onWeekViewLoad(): MutableList<out WeekViewEvent> {
    return events
  }
}
