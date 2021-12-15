package com.examonline.app.modules.classesscreen.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.examonline.app.R
import com.examonline.app.databinding.RowClassesScreenBinding
import com.examonline.app.modules.classesscreen.data.model.ClassesScreenRowModel
import kotlin.Int
import kotlin.Unit
import kotlin.collections.List

public class RecyclerViewAdapter(
  var list: List<ClassesScreenRowModel>
) : RecyclerView.Adapter<RecyclerViewAdapter.RowClassesScreen1VH>() {
  private var clickListener: OnItemClickListener? = null
  public fun updateData(newData: List<ClassesScreenRowModel>): Unit {
    list = newData
    notifyDataSetChanged()
  }

  public fun setOnItemClickListener(clickListener: OnItemClickListener): Unit {
    this.clickListener = clickListener
  }

  public override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RowClassesScreen1VH {
    val view=LayoutInflater.from(parent.context).inflate(R.layout.row_classes_screen,parent,false)
    return RowClassesScreen1VH(view)
  }

  public override fun onBindViewHolder(holder: RowClassesScreen1VH, position: Int): Unit {
    val classesScreenRowModel = list[position]
    holder.binding.classesScreenRowModel = classesScreenRowModel
  }

  public override fun getItemCount(): Int =
  // TODO uncomment following line after integration with data source
   list.size

  public interface OnItemClickListener {
    public fun onItemClick(
      view: View,
      position: Int,
      item: ClassesScreenRowModel
    ): Unit {
    }
  }

  public inner class RowClassesScreen1VH(
    view: View
  ) : RecyclerView.ViewHolder(view) {
    public val binding: RowClassesScreenBinding = RowClassesScreenBinding.bind(itemView)
    init {
      binding.cardView.setOnClickListener {
        // TODO replace with value from datasource
        clickListener?.onItemClick(it, adapterPosition, list[adapterPosition])
      }
    }
  }
}
