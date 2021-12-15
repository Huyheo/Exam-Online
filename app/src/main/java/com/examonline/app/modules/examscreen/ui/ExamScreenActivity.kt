package com.examonline.app.modules.examscreen.ui
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.*
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.ViewPager
import com.examonline.app.R
import com.examonline.app.databinding.ActivityExamScreenBinding
import com.examonline.app.extensions.alert
import com.examonline.app.extensions.negativeButton
import com.examonline.app.extensions.positiveButton
import android.widget.Chronometer
import android.widget.Chronometer.OnChronometerTickListener
import java.util.concurrent.TimeUnit


/**
 * The number of pages (wizard steps) to show in this demo.
 */
private const val NUM_PAGES = 5
lateinit var binding: ActivityExamScreenBinding

class ExamScreenActivity : FragmentActivity() {

    /**
     * The pager widget, which handles animation and allows swiping horizontally to access previous
     * and next wizard steps.
     */
    private lateinit var mPager: ViewPager

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exam_screen)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_exam_screen) as ActivityExamScreenBinding
        binding.lifecycleOwner = this

        // Instantiate a ViewPager and a PagerAdapter.
        mPager = findViewById(R.id.viewpager)

        // The pager adapter, which provides the pages to the view pager widget.
        val pagerAdapter = ExamScreenAdapter(supportFragmentManager)
        mPager.adapter = pagerAdapter

        binding.count.text=(mPager.currentItem+1).toString()+"/"+NUM_PAGES.toString()

        binding.next.setOnClickListener {
            mPager.currentItem = mPager.currentItem + 1
        }
        binding.back.setOnClickListener {
            mPager.currentItem = mPager.currentItem - 1
        }
        binding.image.setOnClickListener {
            finish();
            super.onBackPressed();
        }
        binding.btnSubmitExam.setOnClickListener {
            this.alert("Submit?","Do you want to submit?") {
                positiveButton("Submit"){
                    //do positive actions
                    finish();
                    onBackPressed()
                    it.dismiss()
                }
                negativeButton(){
                    //do negative actions
                    it.dismiss()
                }
            }
        }

//        binding.viewTimer.isCountDown = true
//        binding.viewTimer.base = SystemClock.elapsedRealtime() + 20000
//        binding.viewTimer.start()
//        binding.viewTimer.onChronometerTickListener =
//            OnChronometerTickListener {
//                TimeUnit.SECONDS.sleep(20L)
//                finish()
//            }
        val timer = object: CountDownTimer(30000, 1000) {
            override fun onTick(duration: Long) {
                val Mmin: Long = duration / 1000 / 60
                val Ssec: Long = duration / 1000 % 60
                if (Ssec < 10) {
                    binding.viewTimer.text = "$Mmin:0$Ssec"
                } else binding.viewTimer.text = "$Mmin:$Ssec"
            }

            override fun onFinish() {
                binding.viewTimer.text = "done!"
                finish()
            }
        }
        timer.start()

        binding.viewpager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {

            override fun onPageScrollStateChanged(state: Int) {
                binding.count.text=(mPager.currentItem+1).toString()+"/"+NUM_PAGES.toString()
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }
            override fun onPageSelected(position: Int) {

            }

        })
    }

    override fun onBackPressed() {
        if (mPager.currentItem == 0) {
            // If the user is currently looking at the first step, allow the system to handle the
            // Back button. This calls finish() on this activity and pops the back stack.
            super.onBackPressed()
        } else {
            // Otherwise, select the previous step.
            mPager.currentItem = mPager.currentItem - 1
        }
    }

    /**
     * A simple pager adapter that represents 5 ScreenSlidePageFragment objects, in
     * sequence.
     */
    private inner class ExamScreenAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {
        override fun getCount(): Int = NUM_PAGES

        override fun getItem(position: Int): Fragment = ExamScreenFragment()
    }
}
