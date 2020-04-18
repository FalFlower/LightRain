package com.lightrain.android.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lightrain.android.R
import com.lightrain.android.model.VideoBean

class SearchInfoAdapter(private var context: Context,private var list: List<VideoBean>) :RecyclerView.Adapter<SearchInfoAdapter.SearchInfoHolder>(){

    class SearchInfoHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchInfoHolder {
        val view=LayoutInflater.from(context).inflate(R.layout.view_search_info,null)
        return SearchInfoHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: SearchInfoHolder, position: Int) {
    }
}