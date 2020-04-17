package com.lightrain.android.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lightrain.android.R
import com.lightrain.android.model.UVBean
import com.lightrain.android.model.VideoBean
import com.lightrain.android.util.ImageUtil
import com.lightrain.android.util.StartActivityUtil
import kotlinx.android.synthetic.main.view_learning_list.view.*

class LearningListAdapter(private var uvList: List<UVBean>,private var videoList: List<VideoBean>)
    :RecyclerView.Adapter<LearningListAdapter.LearningListHolder>(){
    private var context:Context?=null
    class LearningListHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LearningListHolder {
        context=parent.context
        val view=LayoutInflater.from(context).inflate(R.layout.view_learning_list,null)
        return LearningListHolder(view)
    }

    override fun getItemCount(): Int {
        return uvList.size
    }

    override fun onBindViewHolder(holder: LearningListHolder, position: Int) {
        holder.itemView.learningListItem.setOnClickListener {
            //跳转到播放界面
            context?.let { it1 -> StartActivityUtil.toVideoPlayActivity(it1,videoList[position]) }
        }
        context?.let { ImageUtil.loadImageRoundedCornersDIY(it,
            holder.itemView.learningListIcon,videoList[position].videoIcon,25,15) }
        holder.itemView.learningListTitle.text=videoList[position].videoTitle
        holder.itemView.learningListProgress.text="已学${uvList[position].videoProgress/videoList[position].videoTime}%"
        //holder.itemView.learningListPoints.setOnClickListener {  }//省略待处理
    }
}