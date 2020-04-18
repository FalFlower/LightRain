package com.lightrain.android.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lightrain.android.R
import com.lightrain.android.ui.activity.SearchActivity
import com.lightrain.android.util.StartActivityUtil
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.android.synthetic.main.view_search_history.view.*

class SearchHistoryAdapter(private var context: Context,private var list:List<String>) :RecyclerView.Adapter<SearchHistoryAdapter.SearchHistoryHolder>(){
    class SearchHistoryHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchHistoryHolder {
        val view=LayoutInflater.from(context).inflate(R.layout.view_search_history,null)
        return SearchHistoryHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: SearchHistoryHolder, position: Int) {
        holder.itemView.searchHistoryListTitle.text=list[position]
        holder.itemView.searchHistoryListTitle.setOnClickListener {
            //跳转到搜索详情界面
            StartActivityUtil.toSearchInfoActivity(context,list[position],list[position])
            SearchActivity.app?.finish()
        }
    }
}