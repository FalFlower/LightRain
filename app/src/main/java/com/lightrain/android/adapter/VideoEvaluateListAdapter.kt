package com.lightrain.android.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lightrain.android.model.VideoEvaluateBean
import com.lightrain.android.widget.VideoEvaluateListItem

class VideoEvaluateListAdapter(private val context: Context,private val list:List<VideoEvaluateBean>) :RecyclerView.Adapter<VideoEvaluateListAdapter.VideoEvaluateListHolder>(){

    class VideoEvaluateListHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoEvaluateListHolder {
        val view=VideoEvaluateListItem(context)
        return VideoEvaluateListHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: VideoEvaluateListHolder, position: Int) {
        val view =holder.itemView as VideoEvaluateListItem
        view.setData(list[position])
    }
}