package com.examonline.app.modules.detailscreen2.ui

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.examonline.app.R
import com.examonline.app.databinding.RowDetailScreen3Binding
import com.examonline.app.modules.detailscreen2.`data`.model.DetailScreen3RowModel
import com.squareup.picasso.Picasso
import java.util.*
import kotlin.Int
import kotlin.Unit
import kotlin.collections.ArrayList
import kotlin.collections.List

public class RecyclerContentAdapter(
  public var list: List<DetailScreen3RowModel>
) : RecyclerView.Adapter<RecyclerContentAdapter.RowDetailScreen3VH>(), Filterable {
  private var clickListener: OnItemClickListener? = null
  var FilterList = list as ArrayList<DetailScreen3RowModel>

  override fun getFilter(): Filter {
    return object : Filter() {
      override fun performFiltering(constraint: CharSequence?): FilterResults {
        val charSearch = constraint.toString()
        if (charSearch.isEmpty()) {
          FilterList = list as ArrayList<DetailScreen3RowModel>
        } else {
          val resultList = ArrayList<DetailScreen3RowModel>()
          for (row in list) {
            if (row.txtStudentName?.toLowerCase(Locale.ROOT)?.
              contains(charSearch.toLowerCase(Locale.ROOT)) == true) {
              resultList.add(row)
            }
          }
          FilterList = resultList
        }
        val filterResults = FilterResults()
        filterResults.values = FilterList
        return filterResults
      }

      @SuppressLint("NotifyDataSetChanged")
      @Suppress("UNCHECKED_CAST")
      override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
        FilterList = results?.values as ArrayList<DetailScreen3RowModel>
        notifyDataSetChanged()
      }
    }
  }

  @SuppressLint("NotifyDataSetChanged")
  public fun updateData(newData: List<DetailScreen3RowModel>): Unit {
    list = newData
    notifyDataSetChanged()
  }

  public fun setOnItemClickListener(clickListener: OnItemClickListener): Unit {
    this.clickListener = clickListener
  }

  public override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RowDetailScreen3VH {
    val view=LayoutInflater.from(parent.context).inflate(R.layout.row_detail_screen3,parent,false)
    return RowDetailScreen3VH(view)
  }

  public override fun onBindViewHolder(holder: RowDetailScreen3VH, position: Int): Unit {
//    val detailScreen3RowModel = DetailScreen3RowModel()
    // TODO uncomment following line after integration with data source
    val detailScreen3RowModel = FilterList[position]
    if (FilterList[position].avaurl?.isNotEmpty() == true){
      val imageView: ImageView = holder.binding.StudentAva
      Picasso.get().load(FilterList[position].avaurl).into(imageView);
    }
    holder.binding.detailScreen3RowModel = detailScreen3RowModel
  }

  public override fun getItemCount(): Int =
  // TODO uncomment following line after integration with data source
    FilterList.size

  public interface OnItemClickListener {
    public fun onItemClick(
      view: View,
      position: Int,
      item: DetailScreen3RowModel
    ): Unit {
    }
  }

  public inner class RowDetailScreen3VH(
    view: View
  ) : RecyclerView.ViewHolder(view) {
    public val binding: RowDetailScreen3Binding = RowDetailScreen3Binding.bind(itemView)
  }
}
