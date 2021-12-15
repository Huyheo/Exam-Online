package com.examonline.app.modules.homescreen.ui

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
import com.examonline.app.databinding.ActivityHomeScreenBinding
import com.examonline.app.extensions.*
import com.examonline.app.modules.homescreen.`data`.viewmodel.HomeScreenVM
import com.examonline.app.network.models.getallofexams.GetAllOfExamsResponse
import com.examonline.app.network.models.resources.ErrorResponse
import com.examonline.app.network.models.resources.SuccessResponse
import kotlin.String
import kotlin.Unit

import com.github.tlaabs.timetableview.Schedule
import com.github.tlaabs.timetableview.Time
import com.google.android.material.snackbar.Snackbar
import org.json.JSONObject
import org.koin.core.KoinComponent
import org.koin.core.inject
import retrofit2.HttpException
import me.jlurena.revolvingweekview.WeekViewEvent

import me.jlurena.revolvingweekview.WeekView.WeekViewLoader


public class HomeScreenFragment :
    BaseFragment<ActivityHomeScreenBinding>(R.layout.activity_home_screen), KoinComponent {
  private val viewModel: HomeScreenVM by viewModels<HomeScreenVM>()
  private var response: GetAllOfExamsResponse?=null
  private val prefs: PreferenceHelper by inject()

  override fun onStart() {
    super.onStart()
    viewModel.onCreateExams()
  }

  public override fun setUpClicks(): Unit {
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
  }

//  override fun onCreateView(
//    inflater: LayoutInflater,
//    container: ViewGroup?,
//    savedInstanceState: Bundle?
//  ): View? {
//    val activity: MainActivity? = activity as MainActivity?
//    val schedules: ArrayList<Schedule> = activity!!.getSchedule()
//    binding.timetable.add(schedules)
//    return view
//  }

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
    val schedules = ArrayList<Schedule>()
    for (s in response.data.data!!){
      val schedule = Schedule()
      schedule.classTitle = s.ExamName // sets subject

      schedule.classPlace = s.ClassName // sets place

      schedule.professorName = s.TotalQuestions.toString() // sets professor

      schedule.startTime =
        s.TimeBegin?.let { Time(it.hours, it.minutes) } // sets the beginning of class time (hour,minute)

      schedule.endTime = s.TimeEnd?.let { Time(it.hours, it.minutes) } // sets the end of class time (hour,minute)
      schedule.day = s.TimeBegin?.day!!
      schedules.add(schedule)
    }
//    binding.timetable.add(schedules)
//    binding.timetable.setOnStickerSelectEventListener(OnStickerSelectedListener { idx, schedules ->
//      Log.d("index", idx.toString())
//      val destIntent = Intent(activity, DetailScreenActivity::class.java)
//      destIntent.putExtra("ExamName", schedules[idx].classTitle)
//      destIntent.putExtra("NumQuestion", schedules[idx].professorName)
//      destIntent.putExtra("Duration", schedules[idx].day.toString())
//      destIntent.putExtra("TimeBegin", schedules[idx].startTime.hour.toString())
//      destIntent.putExtra("TimeEnd", schedules[idx].endTime.hour.toString())
//      destIntent.putExtra("ClassName", schedules[idx].classPlace)
//      startActivity(destIntent)
//    })
//    binding.revolvingWeekview.weekViewLoader = WeekViewLoader { // Add some events
//      val events: List<WeekViewEvent> = ArrayList()
//      return@WeekViewLoader events
//    }
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

  @SuppressLint("SetTextI18n")
  public override fun onInitialized(): Unit {
    binding.homeScreenVM = viewModel
    viewModel.homeScreenModel.value?.txtHelloTeacher = "Hello, "+prefs.getUserName()

//    val mWeekView: WeekView = binding.weekView
//    mWeekView.addChildrenForAccessibility()
  }

  public companion object {
    public const val TAG: String = "HOME_SCREEN_ACTIVITY"

    public fun getIntent(context: Context, bundle: Bundle?): Intent {
      val destIntent = Intent(context, HomeScreenFragment::class.java)
      destIntent.putExtra("bundle", bundle)
      return destIntent
    }
  }
}
