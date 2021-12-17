package com.examonline.app.modules.examscreen.ui

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.*
import com.examonline.app.R
import com.examonline.app.appcomponents.base.BaseFragment
import com.examonline.app.databinding.FullpagedQuestionBinding
import com.examonline.app.network.models.getexam.Question

public class ExamScreenFragment(question: Question) : BaseFragment<FullpagedQuestionBinding>(R.layout.fullpaged_question){

    public val question: Question = question

    override fun onInitialized() {

        binding.questionDataPage.text = question.Question

        val Type = question.Type.toString()



        if (Type == "Single Choices" || Type == "True/False") {
            val MCQ = binding.mcq.findViewById<View>(R.id.radioGroup) as RadioGroup

            binding.mcq.visibility = View.VISIBLE
            binding.answerLayout.visibility = View.GONE
            MCQ.visibility=View.VISIBLE

            val ra = ArrayList<RadioButton>()
            ra.add(MCQ.findViewById<View>(R.id.radioButton) as RadioButton)
            ra.add(MCQ.findViewById<View>(R.id.radioButton2) as RadioButton)
            ra.add(MCQ.findViewById<View>(R.id.radioButton3) as RadioButton)
            ra.add(MCQ.findViewById<View>(R.id.radioButton4) as RadioButton)
            ra.add(MCQ.findViewById<View>(R.id.radioButton5) as RadioButton)
            ra.add(MCQ.findViewById<View>(R.id.radioButton6) as RadioButton)

            for (i in question.Solution!!.size until ra.size) {
                ra[i].visibility = View.GONE
            }

            for (i in question.Solution!!.indices) {
                ra[i].text = question.Solution!![i].Solution.toString()
            }

            MCQ.setOnCheckedChangeListener { _, i ->
                for (element in question.Solution!!)
                    element.Correct = null
                question.Solution!![i.toString().toCharArray().reversedArray()[0].toString().toInt()-2].Correct = 1
            }
        }
        else if(Type == "Multiple Choices" ){
            val MCQ = binding.mcq.findViewById<View>(R.id.checkbox) as LinearLayout
            binding.mcq.visibility = View.VISIBLE
            binding.answerLayout.visibility = View.GONE
            MCQ.visibility=View.VISIBLE

            val ra = ArrayList<CheckBox>()
            ra.add(MCQ.findViewById<View>(R.id.checkbox1) as CheckBox)
            ra.add(MCQ.findViewById<View>(R.id.checkbox2) as CheckBox)
            ra.add(MCQ.findViewById<View>(R.id.checkbox3) as CheckBox)
            ra.add(MCQ.findViewById<View>(R.id.checkbox4) as CheckBox)
            ra.add(MCQ.findViewById<View>(R.id.checkbox5) as CheckBox)
            ra.add(MCQ.findViewById<View>(R.id.checkbox6) as CheckBox)

            for (i in question.Solution!!.size until ra.size) {
                ra[i].visibility = View.GONE
            }

            for (i in question.Solution!!.indices) {
                ra[i].text = question.Solution!![i].Solution.toString()
                ra[i].setOnClickListener {
                    for (element in question.Solution!!)
                        element.Correct = null
                    for (i in question.Solution!!.indices)
                        if (ra[i].isChecked)
                            question.Solution!![i].Correct = 1
                }
            }
        }
        else {
            binding.mcq.visibility = View.GONE
            binding.answerLayout.visibility = View.VISIBLE
            binding.etAnswer.afterTextChanged {
                if (binding.etAnswer.text.isNotEmpty()) {
                    question.Solution!![0].Correct = 1
                    question.Solution!![0].Solution = binding.etAnswer.text.toString()
                }
            }
        }
    }

    override fun setUpClicks() {

    }

    fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
        this.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(editable: Editable?) {
                afterTextChanged.invoke(editable.toString())
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        })
    }
}
