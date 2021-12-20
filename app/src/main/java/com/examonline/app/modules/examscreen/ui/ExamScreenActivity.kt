package com.examonline.app.modules.examscreen.ui
import android.annotation.SuppressLint
import android.os.*
import androidx.databinding.DataBindingUtil
import androidx.viewpager.widget.ViewPager
import com.examonline.app.R
import com.examonline.app.databinding.ActivityExamScreenBinding
import androidx.activity.viewModels
import androidx.fragment.app.*
import com.examonline.app.appcomponents.di.MyApp
import com.examonline.app.extensions.*
import com.examonline.app.modules.detailhistoryscreen.ui.DetailHistoryScreenActivity
import com.examonline.app.modules.examscreen.data.viewmodel.ExamScreenVM
import com.examonline.app.network.models.getexam.GetExamResponse
import com.examonline.app.network.models.resources.ErrorResponse
import com.examonline.app.network.models.resources.SuccessResponse
import com.google.android.material.snackbar.Snackbar
import org.json.JSONObject
import org.koin.core.KoinComponent
import retrofit2.HttpException

import android.os.Bundle
import android.util.Log
import android.view.View
import com.examonline.app.network.models.getexam.Question
import com.examonline.app.network.models.submitexam.SubmitExamRequest
import com.examonline.app.network.models.submitexam.SubmitExamResponse
import java.text.SimpleDateFormat
import java.util.*
import kotlin.properties.Delegates


/**
 * The number of pages (wizard steps) to show in this demo.
 */
lateinit var binding: ActivityExamScreenBinding

class ExamScreenActivity : FragmentActivity(), KoinComponent {

    /**
     * The pager widget, which handles animation and allows swiping horizontally to access previous
     * and next wizard steps.
     */
    private lateinit var mPager: ViewPager
    private val viewModel: ExamScreenVM by viewModels<ExamScreenVM>()
    private lateinit var response : GetExamResponse
    private var timestart by Delegates.notNull<Long>()


    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exam_screen)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_exam_screen) as ActivityExamScreenBinding
        binding.lifecycleOwner = this

        binding.txtExamName.text = intent.getStringExtra("ExamName").toString()

        addObservers()
        onInitialized()
    }

    override fun onBackPressed() {
        if (mPager.currentItem == 0) {
//            this.alert(
//                MyApp.getInstance().getString(R.string.lbl_exit),
//                MyApp.getInstance().getString(R.string.msg_exit)) {
//                positiveButton(MyApp.getInstance().getString(R.string.lbl_exit)){
//                    finish()
//                    super.onBackPressed()
//                    it.dismiss()
//                }
//                negativeButton(){
//                    it.dismiss()
//                }
//            }
        } else {
            mPager.currentItem = mPager.currentItem - 1
        }
    }

    @SuppressLint("SetTextI18n")
    fun onInitialized(){
        viewModel.onCreateDoExam(intent.getStringExtra("ExamID").toString())
        binding.image.visibility= View.GONE
    }

    fun addObservers() {
        var progressDialog: androidx.appcompat.app.AlertDialog? = null
        viewModel.progressLiveData.observe(this@ExamScreenActivity) {
            if (it) {
                progressDialog?.dismiss()
                progressDialog = null
                progressDialog = this@ExamScreenActivity.showProgressDialog()
            } else {
                progressDialog?.dismiss()
            }
        }
        viewModel.getExamLiveData.observe(this@ExamScreenActivity) {
            if (it is SuccessResponse) {
                response = it.getContentIfNotHandled()!!
                onSuccessGetExam(it)
            } else if (it is ErrorResponse) {
                onErrorGetExam(it.data ?: Exception())
            }
        }
        viewModel.submitExamLiveData.observe(this@ExamScreenActivity) {
            if (it is SuccessResponse) {
                val response = it.getContentIfNotHandled()!!
                onSuccessSubmitExam(it)
            } else if (it is ErrorResponse) {
                onErrorGetExam(it.data ?: Exception())
            }
        }
    }

    @SuppressLint("SetTextI18n")
    fun setUp(){
        timestart = System.currentTimeMillis()
        val numPages = viewModel.QuestionList.value?.size

        val timer = object: CountDownTimer(viewModel.Duration.value!!, 1000) {
            override fun onTick(duration: Long) {
                val Mmin: Long = duration / 1000 / 60
                val Ssec: Long = duration / 1000 % 60
                if (Ssec < 10) {
                    binding.viewTimer.text = "$Mmin:0$Ssec"
                } else binding.viewTimer.text = "$Mmin:$Ssec"
            }

            override fun onFinish() {
                binding.viewTimer.text = MyApp.getInstance().getString(R.string.lbl_done)
                alert(
                    MyApp.getInstance().getString(R.string.lbl_timeover),
                    MyApp.getInstance().getString(R.string.msg_timeover)) {
                    neutralButton(){
                        it.dismiss()
                        val submitExamRequest = SubmitExamRequest(
                            ExamID = response.data!!.ExamID,
                            ExamName = response.data!!.ExamName,
                            ClassID = response.data!!.ClassID,
                            ClassName = response.data!!.ClassName,
                            TimeBegin = response.data!!.TimeBegin,
                            TimeEnd = response.data!!.TimeEnd,
                            Duration = response.data!!.Duration,
                            DoingTime = (System.currentTimeMillis() - timestart).div(60000)
                                .toInt(),
                            Questions = getDataSubmit()
                        )
                        viewModel.onSubmit(submitExamRequest)
                    }
                }
            }
        }
        timer.start()
        // Instantiate a ViewPager and a PagerAdapter.
        mPager = findViewById(R.id.viewpager)

        // The pager adapter, which provides the pages to the view pager widget.
        val pagerAdapter = ExamScreenAdapter(supportFragmentManager)
        mPager.adapter = pagerAdapter

        binding.count.text=(mPager.currentItem+1).toString()+"/"+numPages.toString()

        binding.next.setOnClickListener {
            mPager.currentItem = mPager.currentItem + 1
        }
        binding.back.setOnClickListener {
            mPager.currentItem = mPager.currentItem - 1
        }
        binding.image.setOnClickListener {
            this.alert(MyApp.getInstance().getString(R.string.lbl_exit),
                MyApp.getInstance().getString(R.string.msg_exit)) {
                positiveButton(MyApp.getInstance().getString(R.string.lbl_exit)) {
//                    if (checkAnswer()) {
//                        it.dismiss()
//                        val submitExamRequest = SubmitExamRequest(
//                            ExamID = response.data!!.ExamID,
//                            ExamName = response.data!!.ExamName,
//                            ClassID = response.data!!.ClassID,
//                            ClassName = response.data!!.ClassName,
//                            TimeBegin = response.data!!.TimeBegin,
//                            TimeEnd = response.data!!.TimeEnd,
//                            Duration = response.data!!.Duration,
//                            DoingTime = (System.currentTimeMillis() - timestart).div(60000)
//                                .toInt(),
//                            Questions = getDataSubmit()
//                        )
//                        viewModel.onSubmit(submitExamRequest)
//                        timer.cancel()
//                    }
                    it.dismiss()
                    timer.cancel()
                    super.onBackPressed()
                }
                negativeButton() {
                    it.dismiss()
                }
            }
        }
        binding.btnSubmitExam.setOnClickListener {
//            this.alert("Submit?","Do you want to submit?") {
//                positiveButton("Submit"){
//                    finish()
//                    onBackPressed()
//                    it.dismiss()
//                    timer.cancel()
//                }
//                negativeButton(){
//                    it.dismiss()
//                }
//            }

            this.alert(
                MyApp.getInstance().getString(R.string.lbl_submit),
                MyApp.getInstance().getString(R.string.msg_submit)
            ) {
                positiveButton(MyApp.getInstance().getString(R.string.lbl_submit)) {
                    if (checkAnswer()) {
                        it.dismiss()
                        val doingTime =
                            if ((System.currentTimeMillis() - timestart)<60000) 1
                            else (System.currentTimeMillis() - timestart).div(60000).toInt()
                        val submitExamRequest = SubmitExamRequest(
                            ExamID = response.data!!.ExamID,
                            ExamName = response.data!!.ExamName,
                            ClassID = response.data!!.ClassID,
                            ClassName = response.data!!.ClassName,
                            TimeBegin = response.data!!.TimeBegin,
                            TimeEnd = response.data!!.TimeEnd,
                            Duration = response.data!!.Duration,
                            DoingTime = doingTime,
                            Questions = getDataSubmit()
                        )
                        viewModel.onSubmit(submitExamRequest)
                        timer.cancel()
                    }
                }
                negativeButton() {
                    it.dismiss()
                }
            }
        }

        binding.viewpager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
                binding.count.text=(mPager.currentItem+1).toString()+"/"+numPages.toString()
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }
            override fun onPageSelected(position: Int) {

            }

        })
    }

    private fun checkAnswer() : Boolean{
        val pagerAdapter : ExamScreenAdapter = mPager.adapter as ExamScreenAdapter
        for( i in 0..mPager.childCount) {
            if (!pagerAdapter.checkAnswer(pagerAdapter.getItem(i) as ExamScreenFragment)) {
                this.alert(
                    MyApp.getInstance().getString(R.string.lbl_unanswered),
                    MyApp.getInstance().getString(R.string.msg_check_answer)
                ) {
                    neutralButton {
                        mPager.currentItem = i
                        Log.d("numpage",i.toString())
                    }
                }
                return false
            }
        }
        return true
    }

    private fun getDataSubmit() : List<Question>{
        val pagerAdapter : ExamScreenAdapter = mPager.adapter as ExamScreenAdapter
        val questions : MutableList<Question> = mutableListOf()
        for( i in 0..mPager.childCount){
            val q : Question = pagerAdapter.getData(pagerAdapter.getItem(i) as ExamScreenFragment)
            questions.add(q)
        }
        return questions
    }

    private fun onSuccessGetExam(response: SuccessResponse<GetExamResponse>) {
        if (response.data.status?.Code=="200") {
            viewModel.bindGetExamResponse(response.data)
            setUp()
        }else alert(
            MyApp.getInstance().getString(R.string.lbl_error),
            response.data.message.toString()
        ) {
            neutralButton {
                super.onBackPressed()
                finish()
            }
        }
    }
    private fun onErrorGetExam(exception: Exception): Unit {
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
                this@ExamScreenActivity.alert(
                    MyApp.getInstance().getString(R.string.lbl_error),
                    errMessage
                ) {
                    neutralButton {
                    }
                }
            }
        }
    }

    @SuppressLint("SimpleDateFormat")
    private fun onSuccessSubmitExam(response: SuccessResponse<SubmitExamResponse>) {
        if (response.data.status?.Code=="200"){
            val myFormat = "HH:mm dd/MM/yyyy"
            val sdf = SimpleDateFormat(myFormat)
            sdf.timeZone = TimeZone.getTimeZone("UTC")
            val time = response.data.data?.TimeSubmit!!
            val timeSubmit = SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z").parse(time)!!
            val doingTime = response.data.data.DoingTime.toString()
            this.finish()

            val destIntent = DetailHistoryScreenActivity.getIntent(this, null)
            destIntent.putExtra("ExamName", intent.getStringExtra("ExamName").toString())
            destIntent.putExtra("NumQuestion", intent.getStringExtra("NumQuestion").toString())
            destIntent.putExtra("Duration", intent.getStringExtra("Duration").toString())
            destIntent.putExtra("TimeBegin", intent.getStringExtra("TimeBegin").toString())
            destIntent.putExtra("TimeEnd", intent.getStringExtra("TimeEnd").toString())
            destIntent.putExtra("ExamID", response.data.data.ExamID.toString())
            destIntent.putExtra("DoingTime", response.data.data.DoingTime.toString())
            destIntent.putExtra("Mark", response.data.data.Mark.toString())
//            destIntent.putExtra("CorrectNumber", response.data.data.CorrectNumber.toString())
            destIntent.putExtra("TimeSubmit", sdf.format(timeSubmit))
            destIntent.putExtra("TimeStart", sdf.format(timeSubmit.time.minus(doingTime.toInt()*60000)))
            destIntent.putExtra("Title", MyApp.getInstance().getString(R.string.lbl_result_of_exam))
            startActivity(destIntent)
        }else alert(
            MyApp.getInstance().getString(R.string.lbl_error),
            response.data.message.toString()
        ) {
            neutralButton {
            }
        }
    }

    /**
     * A simple pager adapter that represents 5 ScreenSlidePageFragment objects, in
     * sequence.
     */
    private inner class ExamScreenAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {
        val numPages = viewModel.QuestionList.value?.size!!

        fun getData(f:ExamScreenFragment) : Question {
            return f.getData()
        }

        fun checkAnswer(f:ExamScreenFragment) : Boolean {
            return f.checkAnswer()
        }

        override fun getItemPosition(`object`: Any): Int {
            return super.getItemPosition(`object`)
        }

        override fun getCount(): Int = numPages

        override fun getItem(position: Int): Fragment = ExamScreenFragment(viewModel.QuestionList.value?.get(position)!!)

    }
}
