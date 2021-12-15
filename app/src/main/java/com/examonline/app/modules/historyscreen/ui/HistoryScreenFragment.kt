package com.examonline.app.modules.historyscreen.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.examonline.app.R
import com.examonline.app.appcomponents.base.BaseFragment
import com.examonline.app.appcomponents.utility.PreferenceHelper
import com.examonline.app.databinding.ActivityHistoryScreenBinding
import com.examonline.app.modules.detailhistoryscreen.ui.DetailHistoryScreenActivity
import com.examonline.app.modules.historyscreen.data.model.HistoryScreenRowModel
import com.examonline.app.modules.historyscreen.`data`.viewmodel.HistoryScreenVM
import org.koin.core.KoinComponent
import org.koin.core.inject
import kotlin.String
import kotlin.Unit

public class HistoryScreenFragment :
    BaseFragment<ActivityHistoryScreenBinding>(R.layout.activity_history_screen), KoinComponent {
  private val viewModel: HistoryScreenVM by viewModels<HistoryScreenVM>()
  private val prefs: PreferenceHelper by inject()

//  override fun onStart() {
//    super.onStart()
//    viewModel.onClickOnCreate()
//  }

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
        startActivity(destIntent)
      }
    }
  }
  public override fun onInitialized(): Unit {
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

  public companion object {
    public const val TAG: String = "HISTORY_SCREEN_ACTIVITY"

    public fun getIntent(context: Context, bundle: Bundle?): Intent {
      val destIntent = Intent(context, HistoryScreenFragment::class.java)
      destIntent.putExtra("bundle", bundle)
      return destIntent
    }
  }
}
