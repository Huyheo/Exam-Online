package com.examonline.app.modules.examscreen.ui

import android.view.View
import android.widget.RadioButton
import com.examonline.app.R
import com.examonline.app.appcomponents.base.BaseFragment
import com.examonline.app.databinding.FullpagedQuestionBinding

public class ExamScreenFragment : BaseFragment<FullpagedQuestionBinding>(R.layout.fullpaged_question){

    override fun onInitialized() {
        val MCQ = binding.mcq
        MCQ.visibility= View.VISIBLE

        val ra = ArrayList<RadioButton>()
        (MCQ.findViewById<View>(R.id.radioButton) as RadioButton?)?.let { ra.add(it) }
        (MCQ.findViewById<View>(R.id.radioButton2) as RadioButton?)?.let { ra.add(it) }
        (MCQ.findViewById<View>(R.id.radioButton3) as RadioButton?)?.let { ra.add(it) }
        (MCQ.findViewById<View>(R.id.radioButton4) as RadioButton?)?.let { ra.add(it) }
    }

    override fun setUpClicks() {

    }


}
