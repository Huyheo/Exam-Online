package com.examonline.app.modules.doexamscreen.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.examonline.app.R
import com.examonline.app.databinding.RowExamScreenBinding
import com.examonline.app.modules.doexamscreen.data.model.DoExamScreenRowModel
import kotlin.Int
import kotlin.Unit
import kotlin.collections.List

public class RecyclerViewAdapter(
  public var list: List<DoExamScreenRowModel>
) : RecyclerView.Adapter<RecyclerViewAdapter.RowDoExamScreen1VH>() {
  private var clickListener: OnItemClickListener? = null

  public fun updateData(newData: List<DoExamScreenRowModel>): Unit {
    list = newData
    notifyDataSetChanged()
  }

  public fun setOnItemClickListener(clickListener: OnItemClickListener): Unit {
    this.clickListener = clickListener
  }

  public override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RowDoExamScreen1VH {
    val view=LayoutInflater.from(parent.context).inflate(R.layout.row_exam_screen,parent,false)
    return RowDoExamScreen1VH(view)
  }

  public override fun onBindViewHolder(holder: RowDoExamScreen1VH, position: Int): Unit {
     val doExamScreenRowModel = list[position]
    if (list[position].DoingFlag == "NotDone" && list[position].Expired == true){
      holder.binding.Done.visibility = View.GONE
      holder.binding.Expired.visibility = View.VISIBLE
    }
    else if (list[position].DoingFlag == "NotDone") {
      holder.binding.Done.visibility = View.GONE
      holder.binding.Expired.visibility = View.INVISIBLE
    }
    else {
      holder.binding.Done.visibility = View.VISIBLE
      holder.binding.Expired.visibility = View.GONE
    }
    holder.binding.doExamScreenRowModel = doExamScreenRowModel
  }

  public override fun getItemCount(): Int = list.size

  public interface OnItemClickListener {
    public fun onItemClick(
      view: View,
      position: Int,
      item: DoExamScreenRowModel
    ): Unit {
    }
  }

  public inner class RowDoExamScreen1VH(
    view: View
  ) : RecyclerView.ViewHolder(view) {
    public val binding: RowExamScreenBinding = RowExamScreenBinding.bind(itemView)
    init {
      binding.cardView.setOnClickListener {
        // TODO replace with value from datasource
        clickListener?.onItemClick(it, adapterPosition, list[adapterPosition])
      }
    }
  }
}
