package com.examonline.app.modules.historyscreen.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.examonline.app.R
import com.examonline.app.databinding.RowExamScreen1Binding
import com.examonline.app.databinding.RowExamScreenBinding
import com.examonline.app.modules.historyscreen.data.model.HistoryScreenRowModel
import kotlin.Int
import kotlin.Unit
import kotlin.collections.List

public class RecyclerViewAdapter(
  public var list: List<HistoryScreenRowModel>
) : RecyclerView.Adapter<RecyclerViewAdapter.RowHistoryScreen1VH>() {
  private var clickListener: OnItemClickListener? = null

  public fun updateData(newData: List<HistoryScreenRowModel>): Unit {
    list = newData
    notifyDataSetChanged()
  }

  public fun setOnItemClickListener(clickListener: OnItemClickListener): Unit {
    this.clickListener = clickListener
  }

  public override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RowHistoryScreen1VH {
    val view=LayoutInflater.from(parent.context).inflate(R.layout.row_exam_screen1,parent,false)
    return RowHistoryScreen1VH(view)
  }

  public override fun onBindViewHolder(holder: RowHistoryScreen1VH, position: Int): Unit {
    val historyScreenRowModel = HistoryScreenRowModel()
    // TODO uncomment following line after integration with data source
    // val doExamScreen1RowModel = list[position]
    holder.binding.historyScreenRowModel = historyScreenRowModel
  }

  public override fun getItemCount(): Int = 3
  // TODO uncomment following line after integration with data source
  // list.size

  public interface OnItemClickListener {
    public fun onItemClick(
      view: View,
      position: Int,
      item: HistoryScreenRowModel
    ): Unit {
    }
  }

  public inner class RowHistoryScreen1VH(
    view: View
  ) : RecyclerView.ViewHolder(view) {
    public val binding: RowExamScreen1Binding = RowExamScreen1Binding.bind(itemView)
    init {
      binding.cardView.setOnClickListener {
        // TODO replace with value from datasource
        clickListener?.onItemClick(it, adapterPosition, HistoryScreenRowModel())
      }
    }
  }
}
