package com.examonline.app.modules.examscreen.ui
import android.annotation.SuppressLint
import android.database.DataSetObserver
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
import java.util.*


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
    private var Type : String = ""


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
            this.alert("Submit?","Do you want to submit?") {
                positiveButton("Submit"){
                    finish()
                    super.onBackPressed()
                    it.dismiss()
                }
                negativeButton(){
                    it.dismiss()
                }
            }
        } else {
            mPager.currentItem = mPager.currentItem - 1
        }
    }

    @SuppressLint("SetTextI18n")
    fun onInitialized(){
        viewModel.onCreateDoExam(intent.getStringExtra("ExamID").toString())
    }

    public fun addObservers(): Unit {
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
                val response = it.getContentIfNotHandled()
                onSuccessGetExam(it)
            } else if (it is ErrorResponse) {
                onErrorGetExam(it.data ?: Exception())
            }
        }
    }

    @SuppressLint("SetTextI18n")
    fun setUp(){
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
                binding.viewTimer.text = "Done!"
                alert("Time over","Thank you for taking the test!") {
                    neutralButton(){
                        it.dismiss()
                        val destIntent = DetailHistoryScreenActivity.getIntent(this.context, null)
                        startActivity(destIntent)
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
            this.alert("Submit?","Do you want to submit?") {
                positiveButton("Submit"){
                    finish();
                    super.onBackPressed()
                    it.dismiss()
                    timer.cancel()
                }
                negativeButton(){
                    it.dismiss()
                }
            }
        }
        binding.btnSubmitExam.setOnClickListener {
            this.alert("Submit?","Do you want to submit?") {
                positiveButton("Submit"){
                    finish();
                    onBackPressed()
                    it.dismiss()
                    timer.cancel()
                }
                negativeButton(){
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

    private fun onSuccessGetExam(response: SuccessResponse<GetExamResponse>) {
        viewModel.bindGetExamResponse(response.data)
        setUp()
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

    /**
     * A simple pager adapter that represents 5 ScreenSlidePageFragment objects, in
     * sequence.
     */
    private inner class ExamScreenAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {
        val numPages = viewModel.QuestionList.value?.size!!

        override fun registerDataSetObserver(observer: DataSetObserver) {
            super.registerDataSetObserver(observer)
        }

        override fun getCount(): Int = numPages

        override fun getItem(position: Int): Fragment = ExamScreenFragment(viewModel.QuestionList.value?.get(position)!!)

    }
}
