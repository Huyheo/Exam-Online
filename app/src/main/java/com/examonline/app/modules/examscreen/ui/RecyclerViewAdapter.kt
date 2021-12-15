//package com.examonline.app.modules.examscreen.ui
//
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import androidx.recyclerview.widget.RecyclerView
//import com.examonline.app.R
//import com.examonline.app.databinding.RowExamScreen1Binding
//import com.examonline.app.databinding.RowExamScreenBinding
//import com.examonline.app.modules.examscreen.`data`.model.ExamScreen1RowModel
//import kotlin.Int
//import kotlin.Unit
//import kotlin.collections.List
//
//public class RecyclerViewAdapter(
//  public var list: List<ExamScreen1RowModel>
//) : RecyclerView.Adapter<RecyclerViewAdapter.RowExamScreen1VH>() {
//  private var clickListener: OnItemClickListener? = null
//
//  public fun updateData(newData: List<ExamScreen1RowModel>): Unit {
//    list = newData
//    notifyDataSetChanged()
//  }
//
//  public fun setOnItemClickListener(clickListener: OnItemClickListener): Unit {
//    this.clickListener = clickListener
//  }
//
//  public override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RowExamScreen1VH {
//    val view=LayoutInflater.from(parent.context).inflate(R.layout.row_exam_screen1,parent,false)
//    return RowExamScreen1VH(view)
//  }
//
//  public override fun onBindViewHolder(holder: RowExamScreen1VH, position: Int): Unit {
//    val examScreen1RowModel = ExamScreen1RowModel()
//    // TODO uncomment following line after integration with data source
//    // val examScreen1RowModel = list[position]
//    holder.binding.doExamScreenRowModel = examScreen1RowModel
//  }
//
//  public override fun getItemCount(): Int = 2
//  // TODO uncomment following line after integration with data source
//  // list.size
//
//  public interface OnItemClickListener {
//    public fun onItemClick(
//      view: View,
//      position: Int,
//      item: ExamScreen1RowModel
//    ): Unit {
//    }
//  }
//
//  public inner class RowExamScreen1VH(
//    view: View
//  ) : RecyclerView.ViewHolder(view) {
//    public val binding: RowExamScreenBinding = RowExamScreenBinding.bind(itemView)
//  }
//}
