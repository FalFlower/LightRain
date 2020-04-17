package com.lightrain.android.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lightrain.android.model.VideoBean
import com.lightrain.android.widget.ShowListViewItem

class ListViewAdapter(private val status:Int,private val list:List<VideoBean>) :
    RecyclerView.Adapter<ListViewAdapter.ListViewHolder>() {
    var context: Context?=null
    var view:ShowListViewItem?=null

    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        context=parent.context
        view= ShowListViewItem(context)
        return ListViewHolder(view!!)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val v=holder.itemView as ShowListViewItem
        v.setData(status,list[position])
    }
}